package com.trinitycore.sniffexplorer.utils;

import com.trinitycore.sniffexplorer.game.data.Position;
import com.trinitycore.sniffexplorer.game.entities.Creature;
import com.trinitycore.sniffexplorer.game.entities.Unit;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by chaouki on 09-07-16.
 */
public class SniffExplorerUtilsTest {

//    @Test
//    public void testGetLatestKnownValue() throws Exception {
//        Map<Unit, TreeMap<Integer, Integer>> unitHistogramMap = null;
//        TreeMap<Integer, Integer> histogramMap = null;
//        Integer value = null;
//        Unit unit = new Creature(1, "A");
//
//        // case 1
//        histogramMap= new TreeMap<>();
//        unitHistogramMap = new HashMap<>();
//        unitHistogramMap.put(unit, histogramMap);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 1), 1);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 2), 2);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 3), 3);
//        value = SniffExplorerUtils.getLatestKnownValue(LocalDateTime.of(2000, 1, 1, 0, 0, 2, 1*1000_000), unit, unitHistogramMap);
//        Assert.assertThat(value, is(2));
//
//        // case 2
//        histogramMap= new HashMap<>();
//        unitHistogramMap = new HashMap<>();
//        unitHistogramMap.put(unit, histogramMap);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 1), 1);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 2), 2);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 3), 3);
//        value = SniffExplorerUtils.getLatestKnownValue(LocalDateTime.of(2000, 1, 1, 0, 0, 2), unit, unitHistogramMap);
//        Assert.assertThat(value, is(2));
//    }
//
//    @Test
//    public void getLatestKownValueIfSameAsNext_A(){
//        Map<Unit, Map<LocalDateTime, Integer>> unitHistogramMap;
//        Map<LocalDateTime, Integer> histogramMap;
//        Integer value;
//        Unit unit = new Creature(1, "A");
//        LocalDateTime spellTime;
//
//        // case 1a aka ideal situation
//        spellTime = LocalDateTime.of(2000, 1, 1, 0, 0, 4);
//        histogramMap= new HashMap<>();
//        unitHistogramMap = new HashMap<>();
//        unitHistogramMap.put(unit, histogramMap);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 1), 1);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 3), 2);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 5), 2);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 7), 3);
//        value = SniffExplorerUtils.getLatestKnownValueIfSameAsNext(spellTime, unit, unitHistogramMap);
//        Assert.assertThat(value, is(2));
//
//        // case 1b aka ideal situation
//        spellTime = LocalDateTime.of(2000, 1, 1, 0, 0, 2);
//        histogramMap= new HashMap<>();
//        unitHistogramMap = new HashMap<>();
//        unitHistogramMap.put(unit, histogramMap);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 1), 1);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 3), 1);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 5), 2);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 7), 3);
//        value = SniffExplorerUtils.getLatestKnownValueIfSameAsNext(spellTime, unit, unitHistogramMap);
//        Assert.assertThat(value, is(1));
//
//        // case 1c aka ideal situation
//        spellTime = LocalDateTime.of(2000, 1, 1, 0, 0, 6);
//        histogramMap= new HashMap<>();
//        unitHistogramMap = new HashMap<>();
//        unitHistogramMap.put(unit, histogramMap);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 1), 1);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 3), 2);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 5), 3);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 7), 3);
//        value = SniffExplorerUtils.getLatestKnownValueIfSameAsNext(spellTime, unit, unitHistogramMap);
//        Assert.assertThat(value, is(3));
//
//        // case 2a - borders
//        spellTime = LocalDateTime.of(2000, 1, 1, 0, 0, 1);
//        histogramMap= new HashMap<>();
//        unitHistogramMap = new HashMap<>();
//        unitHistogramMap.put(unit, histogramMap);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 1), 1);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 3), 1);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 5), 2);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 7), 3);
//        value = SniffExplorerUtils.getLatestKnownValueIfSameAsNext(spellTime, unit, unitHistogramMap);
//        Assert.assertThat(value, is(1));
//
//        // case 2b - borders
//        spellTime = LocalDateTime.of(2000, 1, 1, 0, 0, 7);
//        histogramMap= new HashMap<>();
//        unitHistogramMap = new HashMap<>();
//        unitHistogramMap.put(unit, histogramMap);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 1), 1);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 3), 2);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 5), 3);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 7), 3);
//        value = SniffExplorerUtils.getLatestKnownValueIfSameAsNext(spellTime, unit, unitHistogramMap);
//        Assert.assertThat(value, is(3));
//
//        // case 3a - exclusive/inclusive search
//        spellTime = LocalDateTime.of(2000, 1, 1, 0, 0, 3);
//        histogramMap= new HashMap<>();
//        unitHistogramMap = new HashMap<>();
//        unitHistogramMap.put(unit, histogramMap);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 1), 1);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 3), 2);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 5), 2);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 7), 3);
//        value = SniffExplorerUtils.getLatestKnownValueIfSameAsNext(spellTime, unit, unitHistogramMap);
//        Assert.assertThat(value, is(2));
//
//        // case 3b - exclusive/inclusive search
//        spellTime = LocalDateTime.of(2000, 1, 1, 0, 0, 5);
//        histogramMap= new HashMap<>();
//        unitHistogramMap = new HashMap<>();
//        unitHistogramMap.put(unit, histogramMap);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 1), 1);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 3), 2);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 5), 2);
//        histogramMap.put(LocalDateTime.of(2000, 1, 1, 0, 0, 7), 3);
//        value = SniffExplorerUtils.getLatestKnownValueIfSameAsNext(spellTime, unit, unitHistogramMap);
//        Assert.assertThat(value, is(2));
//
//        // case
//        spellTime = LocalDateTime.parse("2010-09-14T09:11:02");
//        histogramMap= new HashMap<>();
//        unitHistogramMap = new HashMap<>();
//        unitHistogramMap.put(unit, histogramMap);
//        histogramMap.put(LocalDateTime.parse("2010-09-14T09:10:54"), 1);
//        histogramMap.put(LocalDateTime.parse("2010-09-14T09:10:56"), 2);
//        histogramMap.put(LocalDateTime.parse("2010-09-14T09:10:57"), 3);
//        histogramMap.put(LocalDateTime.parse("2010-09-14T09:11:02"), 3);
//        value = SniffExplorerUtils.getLatestKnownValueIfSameAsNext(spellTime, unit, unitHistogramMap);
//        Assert.assertThat(value, is(3));
//    }
}