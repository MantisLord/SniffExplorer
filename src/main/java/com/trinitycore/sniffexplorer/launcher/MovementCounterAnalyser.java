package com.trinitycore.sniffexplorer.launcher;

import com.trinitycore.sniffexplorer.core.Parser;
import com.trinitycore.sniffexplorer.core.Viewer;
import com.trinitycore.sniffexplorer.core.ViewerFullMessageFile;
import com.trinitycore.sniffexplorer.criteria.CriteriaSet;
import com.trinitycore.sniffexplorer.exceptions.ParseException;
import com.trinitycore.sniffexplorer.game.entities.Unit;
import com.trinitycore.sniffexplorer.message.Direction;
import com.trinitycore.sniffexplorer.message.Message;
import com.trinitycore.sniffexplorer.message.OpCode;
import com.trinitycore.sniffexplorer.message.ParseUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.SetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static com.trinitycore.sniffexplorer.message.OpCode.*;

/**
 * Created by chaouki on 23-12-16.
 */
public class MovementCounterAnalyser {

    protected static final Logger logger = LoggerFactory.getLogger(MovementCounterAnalyser.class);

    //    private static final String INPUT_SNIFF = "C:\\Sniffs\\335\\bin";
//    private static final String INPUT_SNIFF ="C:\\Sniffs\\335\\bin\\anfiteatro_argent_oculus_parsed.txt";
//    private static final String INPUT_SNIFF ="C:\\Sniffs\\335\\bin\\Dump_05_24_10_11_57_42_parsed.txt";
//    private static final String INPUT_SNIFF ="C:\\Sniffs\\335\\bin\\toc-and-more_parsed.txt";
//    private static final String INPUT_SNIFF ="C:\\Sniffs\\335\\bin\\BINBoreanTundraLeveling13_parsed.txt";
    private static final String INPUT_SNIFF ="C:\\Sniffs\\335\\bin\\ICC50%_parsed.txt";
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
        CriteriaSet criteriaSetFirstPass = new CriteriaSet(message -> {
            return message.getDirection().equals(Direction.ServerToClient)
                    && message.getMessageText().stream().anyMatch(line -> line.startsWith("Movement Counter"));
        });

        CriteriaSet criteriaSetSecondPass = new CriteriaSet(message -> {
            return message.getDirection().equals(Direction.ClientToServer)
                    && !message.getOpCode().equals(OpCode.CMSG_MOVE_SPLINE_DONE)
                    && message.getMessageText().stream().anyMatch(line -> line.startsWith("Movement Counter"));
        });

        OpCode[] knownOpcodes={SMSG_MOVE_SET_CAN_FLY, SMSG_MOVE_UNSET_CAN_FLY, SMSG_FORCE_RUN_BACK_SPEED_CHANGE, SMSG_MOVE_SET_NORMAL_FALL, SMSG_MOVE_KNOCK_BACK, SMSG_FORCE_FLIGHT_SPEED_CHANGE, SMSG_FORCE_MOVE_ROOT, SMSG_FORCE_RUN_SPEED_CHANGE, SMSG_FORCE_MOVE_UNROOT, SMSG_MOVE_SET_WATER_WALK, SMSG_FORCE_WALK_SPEED_CHANGE, SMSG_MOVE_ENABLE_TRANSITION_BETWEEN_SWIM_AND_FLY, SMSG_MOVE_DISABLE_TRANSITION_BETWEEN_SWIM_AND_FLY, SMSG_MOVE_SET_LAND_WALK, SMSG_MOVE_SET_HOVERING, SMSG_FORCE_SWIM_SPEED_CHANGE, SMSG_MOVE_UNSET_HOVERING, SMSG_MOVE_SET_FEATHER_FALL, SMSG_MOVE_SET_COLLISION_HGT, MSG_MOVE_TELEPORT_ACK};
        Set<OpCode> opcodesWithAck = new HashSet<>(Arrays.asList(knownOpcodes));
        Map<Unit, LinkedList<Integer>> counters = new HashMap<>();
        Viewer viewer=new ViewerFullMessageFile(OUTPUT_SNIFF_FILE_NAME);
        Parser parser=new Parser(file);
        parser.parseFile(criteriaSetFirstPass, message -> {
            Unit unit = getUnit(message);
            Integer movementCounter = getMovementCounter(message);

            LinkedList<Integer> unitCounters;
            if(counters.containsKey(unit))
                unitCounters = counters.get(unit);
            else{
                unitCounters = new LinkedList<>();
                counters.put(unit, unitCounters);
            }

            if(!unitCounters.isEmpty() && movementCounter!=0 && unitCounters.peekLast()+1!=movementCounter) {
                viewer.show(message);
                viewer.close();
                throw new RuntimeException();
            }

            unitCounters.addLast(movementCounter);
            opcodesWithAck.add(message.getOpCode());
        });

        MapUtils.debugPrint(System.out, "", counters);
        System.out.println(opcodesWithAck);

        if(true)
        return;

        parser.parseFile(criteriaSetSecondPass, message -> {
            Unit unit = getUnit(message);
            Integer movementCounter = getMovementCounter(message);

            LinkedList<Integer> unitCounters;
            if(counters.containsKey(unit))
                unitCounters = counters.get(unit);
            else{
                unitCounters = new LinkedList<>();
                counters.put(unit, unitCounters);
            }

            Integer poll = unitCounters.pollFirst();
            if(!poll.equals(movementCounter)){
                viewer.show(message);
                viewer.close();
                throw new RuntimeException();
            }
        });
        viewer.close();
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

    private static Integer getMovementCounter(Message message) {
        List<String> list = message.getMessageText().stream()
                .filter(line -> line.contains("Movement Counter"))
                .collect(Collectors.toList());
        if(list.size() != 1)
            throw new RuntimeException();

        String line = list.get(0);
        line = ParseUtils.removePrefix(line, "Movement Counter");
        return Integer.valueOf(line);
    }
}
