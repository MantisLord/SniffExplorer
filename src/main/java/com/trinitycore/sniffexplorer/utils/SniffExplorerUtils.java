package com.trinitycore.sniffexplorer.utils;

import com.trinitycore.sniffexplorer.game.data.Position;
import com.trinitycore.sniffexplorer.game.entities.Unit;

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
        TreeMap<Long, T> packetNumberTMap = histogramMap.get(unit);

        if(packetNumberTMap == null)
            return null;

        if(packetNumberTMap.isEmpty())
            return null;

        Map.Entry<Long, T> entry = packetNumberTMap.floorEntry(packetNumber);

        if(entry != null)
            return entry.getValue();
        else
            return null;
    }

    public static Position  getLatestKnownValueIfSameAsNext(Long packetNumber, Unit unit, Map<Unit, TreeMap<Long, Position>> histogramMap, Double errorTolerance) {
        Position before = getLatestKnownValue(packetNumber, unit, histogramMap);
        Position after = getNextKnownValue(packetNumber, unit, histogramMap);
        if(before != null && after != null && GeometryUtils.getDistance3D(before, after) < errorTolerance)
            return new Position((before.getX()+after.getX())/2, (before.getY()+after.getY())/2, (before.getZ()+after.getZ())/2);
        else
            return null;
    }

    public static  <T> T  getNextKnownValue(Long packetNumber, Unit unit, Map<Unit, TreeMap<Long, T>> histogramMap) {
        TreeMap<Long, T> packetNumberTMap = histogramMap.get(unit);

        if(packetNumberTMap == null)
            return null;

        if(packetNumberTMap.isEmpty())
            return null;

        Map.Entry<Long, T> entry = packetNumberTMap.ceilingEntry(packetNumber);

        if(entry != null)
            return entry.getValue();
        else
            return null;
    }
}
