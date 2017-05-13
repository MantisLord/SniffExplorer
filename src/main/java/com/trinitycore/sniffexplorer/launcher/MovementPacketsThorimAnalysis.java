package com.trinitycore.sniffexplorer.launcher;

import com.trinitycore.sniffexplorer.core.Parser;
import com.trinitycore.sniffexplorer.core.Viewer;
import com.trinitycore.sniffexplorer.core.ViewerFullMessageFile;
import com.trinitycore.sniffexplorer.criteria.CriteriaSet;
import com.trinitycore.sniffexplorer.game.data.Position;
import com.trinitycore.sniffexplorer.message.OpCode;
import com.trinitycore.sniffexplorer.message.smsg.OnMonsterMoveMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

/**
 * Created by chaouki on 23-12-16.
 */
@SuppressWarnings("Duplicates")
public class MovementPacketsThorimAnalysis {

    protected static final Logger logger = LoggerFactory.getLogger(MovementPacketsThorimAnalysis.class);

    private static final String INPUT_SNIFF = "C:\\Users\\chaouki\\Desktop\\TC\\sniff\\15595_2012-07-22_23-42-41_ulduar_10_with_hard_modes_parsed.txt";
//    private static final String INPUT_SNIFF = "C:\\Users\\chaouki\\Desktop\\TC\\sniff\\Ulduar_full_with_hard_modes_parsed.txt";
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
            if(unitLine.contains("0xF130807C000057CC"))
                return true;
            else
                return false;
        });

        Set<Position> waypoints = new LinkedHashSet<>();
        List<Position> startPoints = new ArrayList<>();
        Viewer viewer=new ViewerFullMessageFile(OUTPUT_SNIFF_FILE_NAME);
        Parser parser=new Parser(file, true);
        parser.parseFile(criteriaSet, message -> {
            OnMonsterMoveMessage moveMessage = (OnMonsterMoveMessage) message;
            List<Position> positions = moveMessage.getPositions();
            Position lastPosition = positions.get(positions.size() - 1);

            waypoints.add(lastPosition);
            startPoints.add(moveMessage.getPositions().get(0));
        });
        parser.parseFile(criteriaSet, viewer::show);
        System.out.println(waypoints.size());
        for (Position waypoint : waypoints) {
            System.out.println(waypoint);
        }

        for (Position startPoint : startPoints) {
            if(!isBetweenTwoWaypoints(startPoint, waypoints)){
                System.out.println("exception: " + startPoint);
                throw new RuntimeException();
            }
        }

        viewer.close();
    }

    private static boolean isBetweenTwoWaypoints(Position startPoint, Set<Position> waypoints) {
        ArrayList<Position> positions = new ArrayList<>(waypoints);
        positions.add(positions.get(0)); // add first point in the end in order to close the loop

        for (int i = 0; i < positions.size() -1; i++) {
            if(startPoint.isBetween2D(positions.get(i), positions.get(i + 1)))
                return true;
        }

        return false;
    }
}
