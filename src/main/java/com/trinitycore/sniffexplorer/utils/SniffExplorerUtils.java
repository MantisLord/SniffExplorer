package com.trinitycore.sniffexplorer.utils;

import com.trinitycore.sniffexplorer.game.data.Position;
import com.trinitycore.sniffexplorer.game.entities.Unit;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by chaouki on 09-07-16.
 */
public class SniffExplorerUtils {

    public static  <T> T  getClosestKnownValue(LocalDateTime spellTime, Unit unit, Map<Unit, Map<LocalDateTime, T>> histogramMap, final Long requiredPrecision) {
        Map<LocalDateTime, T> localDateTimeTMap = histogramMap.get(unit);

        if(localDateTimeTMap == null)
            return null;

        LocalDateTime closestUpdateTime = null;
        for(LocalDateTime time:localDateTimeTMap.keySet()){
            if(Math.abs(time.until(spellTime, ChronoUnit.MILLIS)) <= requiredPrecision)
                if (closestUpdateTime == null || Math.abs(time.until(spellTime, ChronoUnit.MILLIS)) < Math.abs(closestUpdateTime.until(spellTime, ChronoUnit.MILLIS)))
                    closestUpdateTime = time;
        }

        return localDateTimeTMap.get(closestUpdateTime);
    }

    public static <T> T getClosestKnownValue(LocalDateTime spellTime, Unit unit, Map<Unit, Map<LocalDateTime, T>> histogramMap) {
        Map<LocalDateTime, T> localDateTimeTMap = histogramMap.get(unit);

        if(localDateTimeTMap == null)
            return null;

        LocalDateTime closestUpdateTime = null;
        for(LocalDateTime time:localDateTimeTMap.keySet()){
            if (closestUpdateTime == null || Math.abs(time.until(spellTime, ChronoUnit.MILLIS)) < Math.abs(closestUpdateTime.until(spellTime, ChronoUnit.MILLIS)))
                closestUpdateTime = time;
        }

        return localDateTimeTMap.get(closestUpdateTime);
    }

    public static  <T> T  getLatestKnownValue(LocalDateTime spellTime, Unit unit, Map<Unit, Map<LocalDateTime, T>> histogramMap) {
        Map<LocalDateTime, T> localDateTimeTMap = histogramMap.get(unit);

        if(localDateTimeTMap == null)
            return null;

        List<LocalDateTime> localDateTimes = localDateTimeTMap.keySet().stream().sorted().collect(Collectors.toList());
        if(localDateTimes.isEmpty())
            return null;

        for(int i=0; i<localDateTimes.size()-2 ; i++){
            if(!spellTime.isBefore(localDateTimes.get(i)) && !spellTime.isAfter(localDateTimes.get(i+1)))
                return localDateTimeTMap.get(localDateTimes.get(i));
        }

        return null;
    }

    public static  <T> T  getLatestKnownValueIfSameAsNext(LocalDateTime spellTime, Unit unit, Map<Unit, Map<LocalDateTime, T>> histogramMap) {
        Map<LocalDateTime, T> localDateTimeTMap = histogramMap.get(unit);

        if(localDateTimeTMap == null)
            return null;

        List<LocalDateTime> localDateTimes = localDateTimeTMap.keySet().stream().sorted().collect(Collectors.toList());
        if(localDateTimes.isEmpty())
            return null;

//        // border excluded. they lead to undetermined situations if spellTime is equal to the end or the start because of lack of precision.
//        // if both spellTime and first timestamp of the list are equal, we cant know exactly which one happened the first and which one happened second.
//        if(!spellTime.isAfter(localDateTimes.get(0)) || !spellTime.isBefore(localDateTimes.get(localDateTimes.size()-1)))
//            return null;

        for(int i=0; i<localDateTimes.size()-1 ; i++){
            if (!spellTime.isBefore(localDateTimes.get(i)) && !spellTime.isAfter(localDateTimes.get(i + 1))) {
                T a = localDateTimeTMap.get(localDateTimes.get(i));
                T b = localDateTimeTMap.get(localDateTimes.get(i + 1));
                if(a == null || b == null)
                    throw new IllegalStateException("a and be should not be null");

                if (localDateTimes.get(i).until(localDateTimes.get(i + 1), ChronoUnit.SECONDS) <= 60 && a.equals(b))
                    return localDateTimeTMap.get(localDateTimes.get(i));
            }
        }

        return null;
    }

    public static  <T> T  getNextKnownValue(LocalDateTime spellTime, Unit unit, Map<Unit, Map<LocalDateTime, T>> histogramMap) {
        Map<LocalDateTime, T> localDateTimeTMap = histogramMap.get(unit);

        if(localDateTimeTMap == null)
            return null;

        List<LocalDateTime> localDateTimes = localDateTimeTMap.keySet().stream().sorted().collect(Collectors.toList());
        Collections.reverse(localDateTimes);

        if(localDateTimes.isEmpty())
            return null;

        for(int i=0; i<localDateTimes.size()-2 ; i++){
            if(spellTime.isEqual(localDateTimes.get(i)))

            if(!spellTime.isBefore(localDateTimes.get(i)) && !spellTime.isAfter(localDateTimes.get(i+1)))
                return localDateTimeTMap.get(localDateTimes.get(i));
        }

        return null;
    }
}
