package com.trinitycore.sniffexplorer.launcher;

import com.trinitycore.sniffexplorer.core.Parser;
import com.trinitycore.sniffexplorer.criteria.Criteria;
import com.trinitycore.sniffexplorer.criteria.CriteriaSet;
import com.trinitycore.sniffexplorer.criteria.smsg.UpdateObjectCriteria;
import com.trinitycore.sniffexplorer.game.entities.Creature;
import com.trinitycore.sniffexplorer.game.entities.Player;
import com.trinitycore.sniffexplorer.game.entities.Unit;
import com.trinitycore.sniffexplorer.message.smsg.UpdateObjectMessage;
import com.trinitycore.sniffexplorer.utils.SniffExplorerUtils;
import org.junit.Assert;
import org.junit.Test;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.core.Is.is;

/**
 * Created by chaouki on 09-07-16.
 */
public class SniffExplorerTest {

//    private static final String INPUT_SNIFF_FILE_1_NAME = "/launcher/sample1.txt";
//    private static final String INPUT_SNIFF_FILE_2_NAME = "/launcher/sample2.txt";
//
//    @Test
//    public void testFillGlobalUnitBRadiusCReachMap_1() throws Exception {
//        Parser parser = new Parser(getClass().getResource(INPUT_SNIFF_FILE_1_NAME));
//        HashMap<Unit, TreeMap<Integer, Double>> globalCombatReachMap = new HashMap<>();
//        HashMap<Unit, TreeMap<Integer, Double>> globalBoundingRadiusMap = new HashMap<>();
//        SniffExplorer.fillGlobalUnitBRadiusCReachMap(parser, globalCombatReachMap, globalBoundingRadiusMap);
//
//        /*
//         Asserts on combat reach values
//          */
//        Assert.assertThat(globalCombatReachMap.size(), is(3));
//        TreeMap<Integer, Double> changesMap1 = globalCombatReachMap.get(new Creature(49743, "0xF130C24F0009C932"));
//        TreeMap<Integer, Double> changesMap2 = globalCombatReachMap.get(new Creature(44158, "0xF130AC7E0000208B"));
//        TreeMap<Integer, Double> changesMap3 = globalCombatReachMap.get(new Creature(20491, "0xF130500B0000667E"));
//        Assert.assertNotNull(changesMap1);
//        Assert.assertNotNull(changesMap2);
//        Assert.assertNotNull(changesMap3);
//
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss.SSS");
//        final String DATE_TIME_FIRST_PACKET = "06/17/2012 02:39:43.669";
//        Double combatReach1 = changesMap1.get(LocalDateTime.parse(DATE_TIME_FIRST_PACKET, dateTimeFormatter));
//        Double combatReach2 = changesMap2.get(LocalDateTime.parse(DATE_TIME_FIRST_PACKET, dateTimeFormatter));
//        Double combatReach3 = changesMap3.get(LocalDateTime.parse(DATE_TIME_FIRST_PACKET, dateTimeFormatter));
//        Assert.assertThat(combatReach1, is(0.15));
//        Assert.assertThat(combatReach2, is(1.2));
//        Assert.assertThat(combatReach3, is(1.0));
//
//        /*
//         Asserts on bounding radius values
//          */
//        Assert.assertThat(globalBoundingRadiusMap.size(), is(4));
//        changesMap1 = globalBoundingRadiusMap.get(new Creature(49743, "0xF130C24F0009C932"));
//        changesMap2 = globalBoundingRadiusMap.get(new Creature(44158, "0xF130AC7E0000208B"));
//        changesMap3 = globalBoundingRadiusMap.get(new Creature(20491, "0xF130500B0000667E"));
//        TreeMap<Integer, Double> changesMap4 = globalBoundingRadiusMap.get(new Player("Gansinolo", "0x60000000320D4F0"));
//        Assert.assertNotNull(changesMap1);
//        Assert.assertNotNull(changesMap2);
//        Assert.assertNotNull(changesMap3);
//        Assert.assertNotNull(changesMap4);
//
//        final String DATE_TIME_SECOND_PACKET = "06/17/2012 01:24:25.833";
//        Double boundingRadius1 = changesMap1.get(LocalDateTime.parse(DATE_TIME_FIRST_PACKET, dateTimeFormatter));
//        Double boundingRadius2 = changesMap2.get(LocalDateTime.parse(DATE_TIME_FIRST_PACKET, dateTimeFormatter));
//        Double boundingRadius3 = changesMap3.get(LocalDateTime.parse(DATE_TIME_FIRST_PACKET, dateTimeFormatter));
//        Double boundingRadius4 = changesMap4.get(LocalDateTime.parse(DATE_TIME_SECOND_PACKET, dateTimeFormatter));
//        Assert.assertThat(boundingRadius1, is(0.05));
//        Assert.assertThat(boundingRadius2, is(0.2976));
//        Assert.assertThat(boundingRadius3, is(0.31));
//        Assert.assertThat(boundingRadius4, is(0.306));
//    }
//
//    @Test
//    public void testFillGlobalUnitBRadiusCReachMap_2() throws Exception {
//        Parser parser = new Parser(getClass().getResource(INPUT_SNIFF_FILE_2_NAME));
//
//        HashMap<Unit, TreeMap<Integer, Double>> globalCombatReachMap = new HashMap<>();
//        HashMap<Unit, TreeMap<Integer, Double>> globalBoundingRadiusMap = new HashMap<>();
//        SniffExplorer.fillGlobalUnitBRadiusCReachMap(parser, globalCombatReachMap, globalBoundingRadiusMap);
//
//        /*
//         Asserts on combat reach value
//          */
//        Creature unit = new Creature(26622, "0xF1300067FE14ABC1");
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss.SSS");
//        Double combatReach = SniffExplorerUtils.getLatestKnownValue(LocalDateTime.parse("07/15/2010 21:00:24.000", dateTimeFormatter), unit, globalCombatReachMap);
//        Assert.assertThat(combatReach, is(0.945));
//    }
//
//    @Test
//    public void testFillGlobalUnitPositionsMap() throws Exception {
//
//    }
}