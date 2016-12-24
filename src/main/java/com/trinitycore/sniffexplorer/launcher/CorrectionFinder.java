package com.trinitycore.sniffexplorer.launcher;

import com.trinitycore.sniffexplorer.core.Parser;
import com.trinitycore.sniffexplorer.core.Viewer;
import com.trinitycore.sniffexplorer.core.ViewerFullMessageFile;
import com.trinitycore.sniffexplorer.criteria.Criteria;
import com.trinitycore.sniffexplorer.criteria.CriteriaSet;
import com.trinitycore.sniffexplorer.criteria.smsg.PlayerMovementCriteria;
import com.trinitycore.sniffexplorer.message.Direction;
import com.trinitycore.sniffexplorer.message.Message;
import com.trinitycore.sniffexplorer.message.OpCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.trinitycore.sniffexplorer.message.OpCode.SMSG_SPELL_GO;

/**
 * Created by chaouki on 23-12-16.
 */
public class CorrectionFinder {

    protected static final Logger logger = LoggerFactory.getLogger(CorrectionFinder.class);

    //    private static final String INPUT_SNIFF = "C:\\Sniffs\\335\\bin";
    private static final String INPUT_SNIFF ="C:\\Sniffs\\335\\bin\\anfiteatro_argent_oculus_parsed.txt";
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
        CriteriaSet criteriaSet=new CriteriaSet();

        criteriaSet.addCriteria(
                new Criteria() {
                    @Override
                    public boolean isSatisfiedBy(Message message) {
                        List<OpCode> ignoredOpcodes = new ArrayList<OpCode>();
                        ignoredOpcodes.add(OpCode.SMSG_SPELL_GO);
                        ignoredOpcodes.add(OpCode.SMSG_ON_MONSTER_MOVE);

                        return message.getDirection().equals(Direction.ServerToClient)
                                && (message.getOpCode() == null || !ignoredOpcodes.contains(message.getOpCode()))
//                                && message.getMessageText().stream().anyMatch(line -> line.startsWith("Position"))
                                && message.getMessageText().stream().anyMatch(line -> line.contains("X: ") && line.contains("Y: ") && line.contains("Z: "))
                                && message.getMessageText().stream().noneMatch(line -> line.contains("SMSG_COMPRESSED_UPDATE_OBJECT"))
                                && message.getMessageText().stream().noneMatch(line -> line.contains("MSG_MOVE_SET_FLIGHT_SPEED"))
                                && message.getMessageText().stream().noneMatch(line -> line.contains("MSG_MOVE_SET_RUN_SPEED"))
                                ;
//                                && message.getMessageText().stream().anyMatch(line -> line.contains("0x28000000322F2D4"));

                    }
                }
        );

        // select the way the output will be rendered.
        Viewer viewer=new ViewerFullMessageFile(OUTPUT_SNIFF_FILE_NAME);

        Parser parser=new Parser(file);
        // output the filtered messages to the viewer
        parser.parseFile(criteriaSet, viewer::show);
        // this methods needs to be called once message has been read in order to clear the resources.
        viewer.cleanup();
    }
}
