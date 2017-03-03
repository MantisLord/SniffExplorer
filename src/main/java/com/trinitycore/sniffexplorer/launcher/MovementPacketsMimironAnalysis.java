package com.trinitycore.sniffexplorer.launcher;

import com.trinitycore.sniffexplorer.core.Parser;
import com.trinitycore.sniffexplorer.core.Viewer;
import com.trinitycore.sniffexplorer.core.ViewerFullMessageFile;
import com.trinitycore.sniffexplorer.criteria.CriteriaSet;
import com.trinitycore.sniffexplorer.criteria.smsg.UpdateObjectCriteria;
import com.trinitycore.sniffexplorer.exceptions.ParseException;
import com.trinitycore.sniffexplorer.game.entities.DynamicObject;
import com.trinitycore.sniffexplorer.game.entities.GameObject;
import com.trinitycore.sniffexplorer.game.entities.Unit;
import com.trinitycore.sniffexplorer.message.Message;
import com.trinitycore.sniffexplorer.message.OpCode;
import com.trinitycore.sniffexplorer.message.ParseUtils;
import com.trinitycore.sniffexplorer.message.smsg.UpdateObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by chaouki on 23-12-16.
 */
@SuppressWarnings("Duplicates")
public class MovementPacketsMimironAnalysis {

    protected static final Logger logger = LoggerFactory.getLogger(MovementPacketsMimironAnalysis.class);

//    private static final String INPUT_SNIFF = "C:\\Users\\chaouki\\Desktop\\TC\\sniff\\15595_2012-07-22_23-42-41_ulduar_10_with_hard_modes_parsed.txt";
    private static final String INPUT_SNIFF = "C:\\Users\\chaouki\\Desktop\\TC\\sniff\\Ulduar_full_with_hard_modes_parsed.txt";
//    private static final String INPUT_SNIFF ="C:\\Sniffs\\335\\bin\\anfiteatro_argent_oculus_parsed.txt";
//    private static final String INPUT_SNIFF ="C:\\Sniffs\\335\\bin\\Dump_05_24_10_11_57_42_parsed.txt";
//    private static final String INPUT_SNIFF ="C:\\Sniffs\\335\\bin\\Dump_07_02_10_16_26_19_parsed.txt";
//    private static final String INPUT_SNIFF ="C:\\Sniffs\\335\\bin\\toc-and-more_parsed.txt";
//    private static final String INPUT_SNIFF ="C:\\Sniffs\\335\\bin\\BINBoreanTundraLeveling13_parsed.txt";
//    private static final String INPUT_SNIFF ="C:\\Sniffs\\335\\bin\\ICC50%_parsed.txt";
//    private static final String INPUT_SNIFF ="C:\\Sniffs\\335\\bin\\BINSlavePens_parsed.txt";
    private static final String OUTPUT_SNIFF_FILE_NAME = "sniffexplorer.txt";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File dir = new File(INPUT_SNIFF);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            // case where INPUT_SNIFF is a directory. then analyse every files in it.
            for (File file : directoryListing) {
                if (file.getName().endsWith("_parsed.txt")) {
                    logger.warn("parsing now: " + file.toString());
                    processFile(file);
                }
            }
        } else {
            // INPUT_SNIFF is a file. analyse it.
            logger.warn("parsing now: " + dir.toString());
            processFile(dir);
        }
    }

    private static void processFile(File file) {
        CriteriaSet criteriaSet = new CriteriaSet(message -> {
            if(!message.getOpCode().equals(OpCode.SMSG_ON_MONSTER_MOVE))
                return false;

            String unitLine = message.getMessageText().get(1);
//            Unit unit;
//            try {
//                unit = ParseUtils.parseGuidRemovePrefix(unitLine, "GUID");
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }
//            if(unit.getGUID().equals("0xF15083860000A73C"))
//                return true;
//            else
//                return false;
            if(unitLine.contains("0x2031044B6020E1800062D600002DAA62"))
                return true;
            else
                return false;
        });

        Viewer viewer=new ViewerFullMessageFile(OUTPUT_SNIFF_FILE_NAME);
        Parser parser=new Parser(file, false);
        parser.parseFile(criteriaSet, viewer::show);
        viewer.close();
    }

    private static void printMessage(Message message){
        message.getMessageText().forEach(System.out::println);
        System.out.println();
    }

    private static Unit getUnit(Message message) {
        String line = message.getMessageText().get(1);
        Unit unit;
        try {
            if (line.startsWith("Guid"))
                unit = ParseUtils.parseGuidRemovePrefix(line, "Guid");
            else if (line.startsWith("GUID"))
                unit = ParseUtils.parseGuidRemovePrefix(line, "GUID");
            else
                throw new RuntimeException();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return unit;
    }
}
