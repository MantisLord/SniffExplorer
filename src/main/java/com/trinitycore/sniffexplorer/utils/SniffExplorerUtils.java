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

    public static  <T> T  getLatestKnownValue(Integer packetNumber, Unit unit, Map<Unit, TreeMap<Integer, T>> histogramMap) {
        TreeMap<Integer, T> packetNumberTMap = histogramMap.get(unit);

        if(packetNumberTMap == null)
            return null;

        if(packetNumberTMap.isEmpty())
            return null;

        return packetNumberTMap.floorEntry(packetNumber).getValue();
    }

    public static Position  getLatestKnownValueIfSameAsNext(Integer packetNumber, Unit unit, Map<Unit, TreeMap<Integer, Position>> histogramMap) {
        Position before = getLatestKnownValue(packetNumber, unit, histogramMap);
        Position after = getNextKnownValue(packetNumber, unit, histogramMap);
        if(before != null && after != null && GeometryUtils.getDistance3D(before, after) < 0.5)
            return before;
        else
            return null;
    }

    public static  <T> T  getNextKnownValue(Integer packetNumber, Unit unit, Map<Unit, TreeMap<Integer, T>> histogramMap) {
        TreeMap<Integer, T> packetNumberTMap = histogramMap.get(unit);

        if(packetNumberTMap == null)
            return null;

        if(packetNumberTMap.isEmpty())
            return null;

        return packetNumberTMap.ceilingEntry(packetNumber).getValue();
    }
}
