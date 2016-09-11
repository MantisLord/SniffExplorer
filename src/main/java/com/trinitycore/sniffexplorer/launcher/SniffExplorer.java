/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinitycore.sniffexplorer.launcher;

import com.antistupid.wardbc.lazy.WrapperDBC;
import com.trinitycore.sniffexplorer.core.Parser;
import com.trinitycore.sniffexplorer.core.Viewer;
import com.trinitycore.sniffexplorer.core.ViewerFile;
import com.trinitycore.sniffexplorer.criteria.CriteriaSet;
import com.trinitycore.sniffexplorer.criteria.smsg.*;
import com.trinitycore.sniffexplorer.game.data.Position;
import com.trinitycore.sniffexplorer.game.entities.Creature;
import com.trinitycore.sniffexplorer.game.entities.Unit;
import com.trinitycore.sniffexplorer.message.smsg.*;
import com.trinitycore.sniffexplorer.utils.GeometryUtils;
import com.trinitycore.sniffexplorer.utils.SniffExplorerUtils;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 *
 * @author chaouki
 */
public class SniffExplorer {


    protected static final Logger log = LoggerFactory.getLogger(SniffExplorer.class);

    private static final String INPUT_SNIFF_DIRECTORY_PATH ="C:\\Sniffs\\335\\bin";
//    private static final String INPUT_SNIFF_FILE_NAME ="sniff.txt";
//    private static final String INPUT_SNIFF_FILE_NAME ="ArathiHighland_parsed.txt";
//    private static final String INPUT_SNIFF_FILE_NAME ="sample";
    private static final String OUTPUT_SNIFF_FILE_NAME = "sniffexplorer.txt";

    private static final Integer SAPPHIRON_ENTRY =15989; // Sapphiron's entry in Naxx 10.
    private static final int GLUTH_ENTRY = 15932;
    public static final long POSITION_PRECISION_BY_TIME = 0; // in ms
    private static final double RADIUS_UNHIT_UNIT_SEARCH = 50; // in yards
    private static final Boolean ONLY_SHOW_SPELLS_WITH_AT_LEAST_ONE_HIT = true;

    private static WrapperDBC wrapperDBC = new WrapperDBC("F:\\TrinityCore\\source\\Build\\bin\\Release\\dbc");
    private static int globalValuesCounter = 0;
    private static int predicateDistanceUnsatisfiedCounter = 0;
    private static int predicateDistanceSatisfiedCounter = 0;
    private static int counter;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File dir = new File(INPUT_SNIFF_DIRECTORY_PATH);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File file : directoryListing) {
                if(file.getName().endsWith("_parsed.txt")){
                    try {
                        log.warn("parsing now: " + file.toString());
                        processFile(file);
                    } catch (Exception e){
                        log.warn("exception thrown for "+file, e);
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Is not directory");
        }
    }

    public static void processFile(File file) {

//        // Here you should construct the CriteriaSet. It represent every criteria that will be used to determine which
//        // messages should be filtered. If you are an user of the application, you don't need to modify any other part of
//        // the application than here.
//        CriteriaSet criteriaSet=new CriteriaSet();
////        criteriaSet.setGlobalMinTime("06/16/2012 23:19:09.791");
////        criteriaSet.setGlobalMaxTime("06/16/2012 23:21:32.703");
//
////        String npcGUID = "0xF140B5130B003DEF";
//        String npcGUID = "0xF13000681114AB72";
////        String playerGUID = "0x60000000456E3D8";
//        String playerGUID = "0x28000000322F2D4";
//
//        criteriaSet.addCriteria(
////                new NewHighestThreatUpdateCriteria(),
////                new OnMonsterMoveCriteria(),
////                new OnMonsterMoveCriteria(npcGUID_2)
////                new OnMonsterMoveCriteria(npcGUID_3),
////                new MoveUpdateCriteria(),
//////                new MoveUpdateCriteria(player2GUID),
////                new PlayerMovementCriteria()
//////                new PlayerMovementCriteria(player2GUID),
////
//////                new AttackerStateUpdateCriteria(null, playerGUID),
////                new AttackerStateUpdateCriteria(playerGUID, null),
//////                new AttackerStateUpdateCriteria(player2GUID, null),
//////                new AttackerStateUpdateCriteria(null, npcGUID),
////                new AttackerStateUpdateCriteria(npcGUID, null),
////                new AttackerStateUpdateCriteria(npcGUID_2, null),
////                new AttackerStateUpdateCriteria(npcGUID_3, null),
////
////                new AttackStartStopCriteria(npcGUID, null),
////
////                new UpdateObjectCriteria(npcGUID, "UNIT_FIELD_TARGET"),
////                new UpdateObjectCriteria(npcGUID, "UNIT_FIELD_BOUNDINGRADIUS"),
////                new UpdateObjectCriteria(npcGUID, "UNIT_FIELD_COMBATREACH"),
////                new UpdateObjectCriteria(npcGUID_2, "UNIT_FIELD_COMBATREACH"),
////                new UpdateObjectCriteria(npcGUID_3, "UNIT_FIELD_COMBATREACH"),
////                new UpdateObjectCriteria(playerGUID, "UNIT_FIELD_TARGET"),
////                new UpdateObjectCriteria(playerGUID, "UNIT_FIELD_BOUNDINGRADIUS"),
////                new UpdateObjectCriteria(playerGUID, "UNIT_FIELD_COMBATREACH"),
////
////                new SpellCriteria(null, null, npcGUID),
////                new SpellCriteria(null, null, playerGUID)
//
//
////                new SpellGoCriteria()
//
////                new UpdateObjectCriteria(null, "UNIT_FIELD_BOUNDINGRADIUS"),
////                new UpdateObjectCriteria(null, "UNIT_FIELD_COMBATREACH")
//
////                new UpdateObjectCriteria("0x280000002FE8BBA", "UNIT_FIELD_BOUNDINGRADIUS"),
////                new UpdateObjectCriteria("0x280000002FE8BBA", "UNIT_FIELD_COMBATREACH"),
////
////                new UpdateObjectCriteria("0xF13000718636A68C", "UNIT_FIELD_BOUNDINGRADIUS"),
////                new UpdateObjectCriteria("0xF13000718636A68C", "UNIT_FIELD_COMBATREACH")
//        );
//
//        // select the way the output will be rendered.
//        Viewer viewer=new ViewerFile(OUTPUT_SNIFF_FILE_NAME, false);

        final Set<Integer> spellList=new HashSet<>();
        final Map<Unit, TreeMap<Integer, Position>> globalPositionMap=new HashMap<>();
        final Map<Unit, TreeMap<Integer, Double>> globalCombatReachMap=new HashMap<>();
        final Map<Unit, TreeMap<Integer, Double>> globalBoundingRadiusMap=new HashMap<>();

        Parser parser=new Parser(file);
        // output the filtered messages to the viewer
//        parser.parseFile(criteriaSet, viewer::show);
        // gather the last known positions of every units during the entirety of the sniff's duration
        fillGlobalUnitPositionsMap(parser, globalPositionMap);
        // gather the last known bounding radius and combat reach of every units during the entirety of the sniff's duration
        fillGlobalUnitBRadiusCReachMap(parser, globalCombatReachMap, globalBoundingRadiusMap);
        processAoeSpells(parser, globalPositionMap, globalCombatReachMap, globalBoundingRadiusMap);
        log.warn("counter: "+ counter);
        log.warn("Count of verified launches: "+(counter-globalValuesCounter));
//        log.warn("predicateDistanceUnsatisfiedCounter: "+ predicateDistanceUnsatisfiedCounter);
//        log.warn("predicateDistanceSatisfiedCounter: "+ predicateDistanceSatisfiedCounter);
        // this methods needs to be called once message has been read in order to clear the resources.
//        viewer.cleanup();

//        log.warn(spellList.toString());
    }

    public static void fillGlobalUnitBRadiusCReachMap(Parser parser,
                                                       Map<Unit, TreeMap<Integer, Double>> globalCombatReachMap,
                                                       Map<Unit, TreeMap<Integer, Double>> globalBoundingRadiusMap) {
        CriteriaSet criteriaSet = new CriteriaSet(
                new UpdateObjectCriteria(null, "UNIT_FIELD_BOUNDINGRADIUS"),
                new UpdateObjectCriteria(null, "UNIT_FIELD_COMBATREACH")
        );
        parser.parseFile(criteriaSet, message -> {
            if(!(message instanceof UpdateObjectMessage))
                throw new IllegalArgumentException("The messages here should be of type UpdateObjectMessage");

            UpdateObjectMessage updateObjectMessage = (UpdateObjectMessage) message;
            for(UpdateObjectMessage.UpdateObject updateObject : updateObjectMessage.getUpdates()){
                Unit unit = updateObject.getUnit();
                if(updateObject.getBoundingRadius() != null)
                    addElementToGlobalMap(globalBoundingRadiusMap, message.getId(), unit, updateObject.getBoundingRadius());

                if(updateObject.getCombatReach() != null)
                    addElementToGlobalMap(globalCombatReachMap, message.getId(), unit, updateObject.getCombatReach());
            }
        });
    }

    public static void fillGlobalUnitPositionsMap(Parser parser, Map<Unit, TreeMap<Integer, Position>> globalPositionMap) {
        CriteriaSet criteriaSet = new CriteriaSet(
                new OnMonsterMoveCriteria(),
                new MoveUpdateCriteria(),
                new PlayerMovementCriteria()
        );
        parser.parseFile(criteriaSet, message -> {
            Unit unit = null;
            Integer packetNumber = null;
            Position position = null;
            if(message instanceof OnMonsterMoveMessage){
                OnMonsterMoveMessage onMonsterMoveMessage=(OnMonsterMoveMessage) message;
                unit = onMonsterMoveMessage.getUnit();
                packetNumber = onMonsterMoveMessage.getId();
                position = onMonsterMoveMessage.getPositions().get(0);
                if(position == null)
                    throw new RuntimeException("fuckkk");
            } else if(message instanceof MoveUpdateMessage) {
                MoveUpdateMessage moveUpdateMessage=(MoveUpdateMessage) message;
                unit = moveUpdateMessage.getMovingUnit();
                packetNumber = moveUpdateMessage.getId();
                position = moveUpdateMessage.getCurrentPos();
                if(position == null)
                    throw new RuntimeException("fuckkk");
            } else{
                PlayerMoveMessage playerMoveMessage=(PlayerMoveMessage) message;
                unit = playerMoveMessage.getUnit();
                packetNumber = playerMoveMessage.getId();
                position = playerMoveMessage.getPosition();
                if(position == null && playerMoveMessage.getOpCodeFull().startsWith("MSG_MOVE_TELEPORT"))
                    return; // skip this one. this kind of packets (MSG_MOVE_TELEPORT and MSG_MOVE_TELEPORT_ACK) may or may not contain the unit position
                if(position == null)
                    throw new RuntimeException("fuckkk");
            }

            addElementToGlobalMap(globalPositionMap, packetNumber, unit, position);
        });
    }

    private static <T> void addElementToGlobalMap(Map<Unit, TreeMap<Integer, T>> globalMap, Integer packetNumber, Unit unit, T newElement) {
        if(newElement == null)
            throw new IllegalArgumentException("newElement should not be null");

        TreeMap<Integer, T> changesMap;
        if(globalMap.containsKey(unit))
            changesMap = globalMap.get(unit);
        else{
            changesMap = new TreeMap<>();
            globalMap.put(unit, changesMap);
        }

        changesMap.put(packetNumber, newElement);
    }

    public static void processAoeSpells(Parser parser, Map<Unit,
            TreeMap<Integer, Position>> globalPositionMap, Map<Unit,
            TreeMap<Integer, Double>> globalCombatReachMap, Map<Unit,
            TreeMap<Integer, Double>> globalBoundingRadiusMap) {
        CriteriaSet criteriaSet = new CriteriaSet(
                new SpellGoCriteria()
        );

        List<Integer> spellIds = Arrays.asList(13704, 29328, 3600, 57491, 33061, 29484, 15532, 23600, 19134, 30657, 33237, 53850, 22884, 5484, 27758, 16508);
        List<Integer> exclutionSpellList = Arrays.asList(81280, 57578, 28542, 28560, 30541, 30657, 34441, 48721);
        List<Integer> exclutionEntryList = Arrays.asList(32325, 32322, 32340);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss.SSS");
        parser.parseFile(criteriaSet, message -> {
            if(message instanceof SpellGoMessage){
                SpellGoMessage spellGoMessage=(SpellGoMessage)message;
                Position location = spellGoMessage.getSourceLocation();
                if(location==null)
                    location=spellGoMessage.getDestinationLocation();

                // exclusion by creature entry
                if(spellGoMessage.getCasterUnit() instanceof Creature){
                    Creature caster=(Creature) spellGoMessage.getCasterUnit();
                    if(exclutionEntryList.contains(caster.getEntry()))
                        return;
                }

                // exclusion by spellId
                if(exclutionSpellList.contains(spellGoMessage.getSpellId()))
                    return;

                if(spellGoMessage.getSourceLocation()==null && spellGoMessage.getDestinationLocation()==null)
                    return;

                // exclude spell_go messages with empty hit & miss lists <======================================================== remove this later?
                if(spellGoMessage.getHitUnits().isEmpty() && spellGoMessage.getMissedUnits().keySet().isEmpty())
                    return;

                // initial conditions on the spell message met. let's get to work now
                List<RecordToDisplay> recordToDisplayList =new ArrayList<>();
                for(Unit unit:spellGoMessage.getHitUnits())
                    recordToDisplayList.add(computeAndShowPosition(spellGoMessage, unit, globalPositionMap, globalCombatReachMap, globalBoundingRadiusMap));
                for(Unit unit:spellGoMessage.getMissedUnits().keySet())
                    recordToDisplayList.add(computeAndShowPosition(spellGoMessage, unit, globalPositionMap, globalCombatReachMap, globalBoundingRadiusMap));

                // remove nulls from the list (computeAndShowPosition() can return null)
                recordToDisplayList.removeIf(p -> p == null);

                /*
                // we are done analyzing the people who got hit by the spell (and by hit I mean "spell hit" and also "spell miss").
                // let's look also at the units nearby that weren't hit.
                for(Unit unit:globalPositionMap.keySet()){
                    // ignore units "touched by the spell". they were already processed earlier.
                    if(spellGoMessage.getHitUnits().contains(unit) || spellGoMessage.getMissedUnits().keySet().contains(unit))
                        continue;

                    // let's see if we can find the position of the unit around the time of the SPELL_GO
                    Map<LocalDateTime, Position> localDateTimePositionMap = globalPositionMap.get(unit);
                    LocalDateTime closestPositionUpdateTime = null;
                    for(LocalDateTime time:localDateTimePositionMap.keySet()){
                        if(Math.abs(time.until(spellGoMessage.getTime(), ChronoUnit.MILLIS))< POSITION_PRECISION_BY_TIME)
                            if (closestPositionUpdateTime == null || Math.abs(time.until(spellGoMessage.getTime(), ChronoUnit.MILLIS)) < Math.abs(closestPositionUpdateTime.until(spellGoMessage.getTime(), ChronoUnit.MILLIS)))
                                closestPositionUpdateTime = time;
                    }

                    // if no match, skip this unit
                    if(closestPositionUpdateTime == null)
                        continue;

                    // we have the position of the unit around the time of the spell. let's now see if the unit was close to the spell area
                    Position unitPosition = localDateTimePositionMap.get(closestPositionUpdateTime);
                    double distance3D = getDistance3D(sourceLocation, unitPosition);

                    if(distance3D < RADIUS_UNHIT_UNIT_SEARCH)
                        recordToDisplayList.add(new RecordToDisplay(unit, unitPosition.toFormatedStringWoOrientation(), distance3D, false));
                }
                */

                // All the processing of units, their position and the distances is done now. Just display what we found.
                boolean shouldDisplaySpell;
                if(ONLY_SHOW_SPELLS_WITH_AT_LEAST_ONE_HIT) {
                    shouldDisplaySpell = recordToDisplayList.stream()
                            .anyMatch(RecordToDisplay::isHit);
                }
                else
                    shouldDisplaySpell= recordToDisplayList.size()>0;

                if(shouldDisplaySpell){
                    log.warn("------------------------");
                    log.warn("Spell at "+dateTimeFormatter.format(spellGoMessage.getTime()));
                    log.warn("Spell location: " + location.toFormatedStringWoOrientation());
                    log.warn("Spell id: " + spellGoMessage.getSpellId());
                    log.warn("Spell range: " + wrapperDBC.highestSpellRadius(spellGoMessage.getSpellId()));
                    log.warn("Spell effect implicit targets: " + Arrays.toString(wrapperDBC.getSpellImplicitTargets(spellGoMessage.getSpellId())));
                    log.warn("Caster: " + spellGoMessage.getCasterUnit().toString());
                    log.warn("Caster combat reach: " + SniffExplorerUtils.getLatestKnownValue(spellGoMessage.getId(), spellGoMessage.getCasterUnit(), globalCombatReachMap));
                    for(RecordToDisplay recordToDisplay : recordToDisplayList)
                        log.warn(recordToDisplay.toString());
                    log.warn("------------------------");
                }
            }
        });
    }

    private static RecordToDisplay computeAndShowPosition(SpellGoMessage spellGoMessage, Unit target, Map<Unit, TreeMap<Integer, Position>> globalPositionMap, Map<Unit, TreeMap<Integer, Double>> globalCombatReachMap, Map<Unit, TreeMap<Integer, Double>> globalBoundingRadiusMap) {
        counter++;
        Position location;
//        if(spellGoMessage.getSourceLocation()!=null && spellGoMessage.getDestinationLocation()!=null)
//            return null;
//        else
        if(spellGoMessage.getDestinationLocation()!=null)
            location=spellGoMessage.getDestinationLocation();
        else
            location=spellGoMessage.getSourceLocation();

        Integer packetNumber = spellGoMessage.getId();
        Integer spellId = spellGoMessage.getSpellId();

//        if(spellTime.equals(LocalDateTime.of(2010, 9, 14, 9, 11, 2)))
//            System.out.println("derp");

        // get the latest known position of the unit
//        Position targetPosition = SniffExplorerUtils.getClosestKnownValue(spellTime, target, globalPositionMap, POSITION_PRECISION_BY_TIME);
        Position targetPosition = SniffExplorerUtils.getLatestKnownValueIfSameAsNext(packetNumber, target, globalPositionMap);
        Double targetCombatReach = SniffExplorerUtils.getLatestKnownValue(packetNumber, target, globalCombatReachMap);
        Double targetBoundingRadius = SniffExplorerUtils.getLatestKnownValue(packetNumber, target, globalBoundingRadiusMap);
        Double casterCombatReach = SniffExplorerUtils.getLatestKnownValue(packetNumber, spellGoMessage.getCaster(), globalCombatReachMap);
        Double casterBoundingRadius = SniffExplorerUtils.getLatestKnownValue(packetNumber, spellGoMessage.getCaster(), globalBoundingRadiusMap);
//        if(targetPosition == null || targetCombatReach == null || targetBoundingRadius == null || casterCombatReach == null || casterBoundingRadius == null) {
        if(targetPosition == null || targetCombatReach == null || casterCombatReach == null) {
            globalValuesCounter ++;
            return null;
        }
        double distance3D = GeometryUtils.getDistance3D(location, targetPosition);
//        double distance2D = GeometryUtils.getDistance2D(location, targetPosition);
//        log.warn("distance 3D: "+distance3D+" distance 2D: "+distance2D+" Position: "+position.toFormatedString());
//        log.warn(unit.getGUID() + " " +position.toFormatedStringWoOrientation() + " " + distance3D);

        float spellRadius = wrapperDBC.highestSpellRadius(spellId);
//        if(spellRadius > 0 && !spellGoMessage.getCasterUnit().equals(target) && distance3D >= spellRadius + casterCombatReach && distance3D <= spellRadius + targetCombatReach) {
//        if(spellRadius > 0 && !spellGoMessage.getCasterUnit().equals(target) && distance3D >= spellRadius + casterCombatReach + targetCombatReach) {
        if(spellRadius > 0 && !spellGoMessage.getCasterUnit().equals(target) && distance3D > spellRadius) {
            predicateDistanceSatisfiedCounter++;
            return new RecordToDisplay(target, targetPosition.toFormatedStringWoOrientation(), distance3D, targetCombatReach);
        }
        else{
            predicateDistanceUnsatisfiedCounter++;
            return null;
        }
    }

    private static class RecordToDisplay {

        private Unit unit;
        private String formattedPosition;
        private double distance;
        private boolean isHit;
        private double combatReach;

        public RecordToDisplay(Unit unit, String formattedPosition, double distance) {
            this.unit = unit;
            this.formattedPosition = formattedPosition;
            this.distance = distance;
            this.isHit = true;
        }

        public RecordToDisplay(Unit unit, String formattedPosition, double distance, double combatReach) {
            this.unit = unit;
            this.formattedPosition = formattedPosition;
            this.distance = distance;
            this.isHit = true;
            this.combatReach = combatReach;
        }

        public RecordToDisplay(Unit unit, String formattedPosition, double distance, boolean isHit) {
            this.unit = unit;
            this.formattedPosition = formattedPosition;
            this.distance = distance;
            this.isHit = isHit;
        }

        public boolean isHit() {
            return isHit;
        }

        @Override
        public String toString(){
            String distanceString = String.format("%04.3f", distance);
            if(isHit)
                return unit.toString() + " " + formattedPosition + " " + distanceString + " " + this.combatReach;
            else
                return unit.toString() + " " + formattedPosition + " " + distanceString + " " + this.combatReach +" <<<<<";
        }
    }
}
