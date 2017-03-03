package com.trinitycore.sniffexplorer.launcher;

import com.trinitycore.sniffexplorer.core.Parser;
import com.trinitycore.sniffexplorer.core.Viewer;
import com.trinitycore.sniffexplorer.core.ViewerFullMessageFile;
import com.trinitycore.sniffexplorer.criteria.CriteriaSet;
import com.trinitycore.sniffexplorer.exceptions.ParseException;
import com.trinitycore.sniffexplorer.game.entities.Player;
import com.trinitycore.sniffexplorer.game.entities.Unit;
import com.trinitycore.sniffexplorer.message.Direction;
import com.trinitycore.sniffexplorer.message.Message;
import com.trinitycore.sniffexplorer.message.OpCode;
import com.trinitycore.sniffexplorer.message.ParseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by chaouki on 23-12-16.
 */
public class MovementPacketsAssertions2 {

    protected static final Logger logger = LoggerFactory.getLogger(MovementPacketsAssertions2.class);

    private static final String INPUT_SNIFF = "C:\\Sniffs\\335\\bin";
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
//        Viewer viewer=new ViewerFullMessageFile(OUTPUT_SNIFF_FILE_NAME);

        Set<OpCode> opCodesToAnalyse = new HashSet<>();
        opCodesToAnalyse.add(OpCode.MSG_MOVE_ROOT);
        opCodesToAnalyse.add(OpCode.MSG_MOVE_WATER_WALK);
        opCodesToAnalyse.add(OpCode.MSG_MOVE_HOVER);
        opCodesToAnalyse.add(OpCode.MSG_MOVE_UPDATE_CAN_FLY);
        opCodesToAnalyse.add(OpCode.MSG_MOVE_UPDATE_CAN_TRANSITION_BETWEEN_SWIM_AND_FLY);
        opCodesToAnalyse.add(OpCode.MSG_MOVE_FEATHER_FALL);
        opCodesToAnalyse.add(OpCode.MSG_MOVE_GRAVITY_CHNG);

        opCodesToAnalyse.add(OpCode.MSG_MOVE_UNROOT);
        opCodesToAnalyse.add(OpCode.MSG_MOVE_WATER_WALK);
        opCodesToAnalyse.add(OpCode.MSG_MOVE_HOVER);
        opCodesToAnalyse.add(OpCode.MSG_MOVE_UPDATE_CAN_FLY);
        opCodesToAnalyse.add(OpCode.MSG_MOVE_UPDATE_CAN_TRANSITION_BETWEEN_SWIM_AND_FLY);
        opCodesToAnalyse.add(OpCode.MSG_MOVE_FEATHER_FALL);
        opCodesToAnalyse.add(OpCode.MSG_MOVE_GRAVITY_CHNG);

        opCodesToAnalyse.add(OpCode.MSG_MOVE_SET_WALK_SPEED);
        opCodesToAnalyse.add(OpCode.MSG_MOVE_SET_RUN_SPEED);
        opCodesToAnalyse.add(OpCode.MSG_MOVE_SET_RUN_BACK_SPEED);
        opCodesToAnalyse.add(OpCode.MSG_MOVE_SET_SWIM_SPEED);
        opCodesToAnalyse.add(OpCode.MSG_MOVE_SET_SWIM_BACK_SPEED);
        opCodesToAnalyse.add(OpCode.MSG_MOVE_SET_TURN_RATE);
        opCodesToAnalyse.add(OpCode.MSG_MOVE_SET_FLIGHT_SPEED);
        opCodesToAnalyse.add(OpCode.MSG_MOVE_SET_FLIGHT_BACK_SPEED);
        opCodesToAnalyse.add(OpCode.MSG_MOVE_SET_PITCH_RATE);

        opCodesToAnalyse.add(OpCode.MSG_MOVE_SET_COLLISION_HGT);
        opCodesToAnalyse.add(OpCode.MSG_MOVE_TELEPORT);
        opCodesToAnalyse.add(OpCode.MSG_MOVE_KNOCK_BACK);

        CriteriaSet criteriaSet = new CriteriaSet(message -> opCodesToAnalyse.contains(message.getOpCode()));
        Parser parser=new Parser(file, false);
        parser.parseFile(criteriaSet, message -> {
            boolean conditionToAssert = message.getDirection().equals(Direction.ServerToClient);
            if(!conditionToAssert){
//                viewer.show(message);
                System.out.println("condition not verified in " + file + " for opcode: " + message.getOpCode() + " and direction " + message.getDirection());
                printMessage(message);

//                viewer.close();
//                throw new RuntimeException();
            }
        });

//        viewer.close();
    }

    private static void printMessage(Message message){
        System.out.println();
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
