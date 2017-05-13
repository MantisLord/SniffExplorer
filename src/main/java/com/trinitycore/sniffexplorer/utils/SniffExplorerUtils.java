package com.trinitycore.sniffexplorer.utils;

import com.trinitycore.sniffexplorer.game.data.Position;
import com.trinitycore.sniffexplorer.game.entities.Unit;
import org.assertj.core.api.Assertions;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Created by chaouki on 09-07-16.
 */
public class SniffExplorerUtils {

//    public static  <T> T  getClosestKnownValue(LocalDateTime spellTime, Unit unit, Map<Unit, Map<LocalDateTime, T>> histogramMap, final Long requiredPrecision) {
//        Map<LocalDateTime, T> localDateTimeTMap = histogramMap.get(unit);
//
//        if(localDateTimeTMap == null)
//            return null;
//
//        LocalDateTime closestUpdateTime = null;
//        for(LocalDateTime time:localDateTimeTMap.keySet()){
//            if(Math.abs(time.until(spellTime, ChronoUnit.MILLIS)) <= requiredPrecision)
//                if (closestUpdateTime == null || Math.abs(time.until(spellTime, ChronoUnit.MILLIS)) < Math.abs(closestUpdateTime.until(spellTime, ChronoUnit.MILLIS)))
//                    closestUpdateTime = time;
//        }
//
//        return localDateTimeTMap.get(closestUpdateTime);
//    }
//
//    public static <T> T getClosestKnownValue(LocalDateTime spellTime, Unit unit, Map<Unit, Map<LocalDateTime, T>> histogramMap) {
//        Map<LocalDateTime, T> localDateTimeTMap = histogramMap.get(unit);
//
//        if(localDateTimeTMap == null)
//            return null;
//
//        LocalDateTime closestUpdateTime = null;
//        for(LocalDateTime time:localDateTimeTMap.keySet()){
//            if (closestUpdateTime == null || Math.abs(time.until(spellTime, ChronoUnit.MILLIS)) < Math.abs(closestUpdateTime.until(spellTime, ChronoUnit.MILLIS)))
//                closestUpdateTime = time;
//        }
//
//        return localDateTimeTMap.get(closestUpdateTime);
//    }

    public static  <T> T  getLatestKnownValue(Long packetNumber, Unit unit, Map<Unit, TreeMap<Long, T>> histogramMap) {
        Map.Entry<Long, T> entry = getLatestKnownEntry(packetNumber, unit, histogramMap);

        if(entry != null)
            return entry.getValue();
        else
            return null;
    }

    public static  <T> Map.Entry<Long, T>  getLatestKnownEntry(Long packetNumber, Unit unit, Map<Unit, TreeMap<Long, T>> histogramMap) {
        TreeMap<Long, T> packetNumberTMap = histogramMap.get(unit);

        if(packetNumberTMap == null)
            return null;

        if(packetNumberTMap.isEmpty())
            return null;

        return packetNumberTMap.floorEntry(packetNumber);
    }

    public static  Position  getLatestKnownValuePosition(Long packetNumber, Unit unit, Map<Unit, TreeMap<Long, List<Position>>> histogramMap) {
        Map.Entry<Long, List<Position>> entry = getLatestKnownEntryPosition(packetNumber, unit, histogramMap);

        if(entry != null){
            List<Position> values = entry.getValue();
            return values.get(values.size()-1);
        }
        else
            return null;
    }

    public static Map.Entry<Long, List<Position>>  getLatestKnownEntryPosition(Long packetNumber, Unit unit, Map<Unit, TreeMap<Long, List<Position>>> histogramMap) {
        TreeMap<Long, List<Position>> packetNumberTMap = histogramMap.get(unit);

        if(packetNumberTMap == null)
            return null;

        if(packetNumberTMap.isEmpty())
            return null;

        return packetNumberTMap.floorEntry(packetNumber);
    }

    public static Position  getLatestKnownValueIfSameAsNext(Long packetNumber, Unit unit, Map<Unit, TreeMap<Long, List<Position>>> histogramMap, Double errorTolerance) {
        Position before = getLatestKnownValuePosition(packetNumber, unit, histogramMap);
        Position after = getNextKnownValue(packetNumber, unit, histogramMap);
        if(before != null && after != null && GeometryUtils.getDistance3D(before, after) < errorTolerance)
//            return new Position((before.getX()+after.getX())/2, (before.getY()+after.getY())/2, (before.getZ()+after.getZ())/2);if(before != null && after != null && GeometryUtils.getDistance3D(before, after) < 0.5)
            return before;
        else
            return null;
    }

    public static Position getLastPositionIfTheUnitIsStopped(Long packetNumber, Unit unit, Map<Unit, TreeMap<Long, List<Position>>> histogramMap, Map<Unit, TreeMap<Long, List<Position>>> globalStopPositionMap) {
        Map.Entry<Long, List<Position>> latestKnownEntry = getLatestKnownEntry(packetNumber, unit, histogramMap);
        Map.Entry<Long, List<Position>> latestKnownStopEntry = getLatestKnownEntry(packetNumber, unit, globalStopPositionMap);
//        Position nextKnownValue = getNextKnownValue(packetNumber, unit, histogramMap);

        if(latestKnownEntry == null || latestKnownStopEntry == null)
            return null;

        // we make sure that the unit was not moving
        if(!latestKnownEntry.getKey().equals(latestKnownStopEntry.getKey())) {
            return null;
        }

        Position lastKnownPosition = getLatestKnownValuePosition(packetNumber, unit, histogramMap);
        Position lastKnownStopPosition = getLatestKnownValuePosition(packetNumber, unit, globalStopPositionMap);

        if(latestKnownEntry.getValue().size() > 1 && !lastKnownPosition.equals(lastKnownStopPosition))
            return null;

        Assertions.assertThat(lastKnownPosition).isEqualTo(lastKnownStopPosition);
//        Assertions.assertThat(lastKnownPosition).isEqualTo(nextKnownValue);

        return lastKnownPosition;
    }

    public static  <T> T  getNextKnownValue(Long packetNumber, Unit unit, Map<Unit, TreeMap<Long, List<T>>> histogramMap) {

        Map.Entry<Long, List<T>> entry = getNextKnownEntry(packetNumber, unit, histogramMap);

        if(entry != null){
            List<T> values = entry.getValue();
            return values.get(0);
        }
        else
            return null;
    }

    public static  <T> Map.Entry<Long, List<T>>  getNextKnownEntry(Long packetNumber, Unit unit, Map<Unit, TreeMap<Long, List<T>>> histogramMap) {
        TreeMap<Long, List<T>> packetNumberTMap = histogramMap.get(unit);

        if(packetNumberTMap == null)
            return null;

        if(packetNumberTMap.isEmpty())
            return null;

        return packetNumberTMap.ceilingEntry(packetNumber);
    }
}
