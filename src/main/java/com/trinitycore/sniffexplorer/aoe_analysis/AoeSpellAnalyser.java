package com.trinitycore.sniffexplorer.aoe_analysis;

import com.antistupid.wardbc.lazy.WrapperDBC;
import com.trinitycore.sniffexplorer.core.Parser;
import com.trinitycore.sniffexplorer.criteria.CriteriaSet;
import com.trinitycore.sniffexplorer.criteria.smsg.*;
import com.trinitycore.sniffexplorer.game.data.Position;
import com.trinitycore.sniffexplorer.game.entities.Creature;
import com.trinitycore.sniffexplorer.game.entities.Player;
import com.trinitycore.sniffexplorer.game.entities.Unit;
import com.trinitycore.sniffexplorer.message.Message;
import com.trinitycore.sniffexplorer.message.smsg.*;
import com.trinitycore.sniffexplorer.utils.GeometryUtils;
import com.trinitycore.sniffexplorer.utils.SniffExplorerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigInteger;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.*;

/**
 * Created by chaouki on 12-09-16.
 */
public class AoeSpellAnalyser {

    protected static final Logger logger = LoggerFactory.getLogger(AoeSpellAnalyser.class);

    private static final Boolean ONLY_SHOW_SPELLS_WITH_AT_LEAST_ONE_HIT = true;
    private static final Double ERROR_TOLERANCE = 1.5;

    private static WrapperDBC wrapperDBC = new WrapperDBC("F:\\TrinityCore\\source\\Build\\bin\\Release\\dbc");
    private static int globalValuesCounter = 0;
    private static int counter;
    private static int predicateDistanceUnsatisfiedCounter = 0;
    private static int predicateDistanceSatisfiedCounter = 0;

    private Long timeSyncDelta;

    public void processFile(File file) {

        final Set<Integer> spellList=new HashSet<>();
        final Map<Unit, TreeMap<Long, Position>> globalPositionMap=new HashMap<>();
        final Map<Unit, TreeMap<Long, Double>> globalCombatReachMap=new HashMap<>();
        final Map<Unit, TreeMap<Long, Double>> globalBoundingRadiusMap=new HashMap<>();

        Parser parser=new Parser(file);
        findTimeSyncDelta(parser);
        fillHistograms(parser, globalPositionMap, globalCombatReachMap, globalBoundingRadiusMap);
        processAoeSpells(parser, globalPositionMap, globalCombatReachMap, globalBoundingRadiusMap);
        logger.warn("counter: "+ counter);
        logger.warn("Count of verified launches: "+(counter-globalValuesCounter));
//        logger.warn("predicateDistanceUnsatisfiedCounter: "+ predicateDistanceUnsatisfiedCounter);
//        logger.warn("predicateDistanceSatisfiedCounter: "+ predicateDistanceSatisfiedCounter);
//        logger.warn(spellList.toString());
    }

    /**
     * Look for the first SPELL_GO message. Using its timestamp and its field "Time" (aka timeTicks), we can compute
     * the "timeSyncDelta" using this relations:
     * timeTicks = timestamp + timeSyncDelta (this relation is used in the method fillHistograms)
     * <=> timeSyncDelta = timeTicks - timestamp
     *
     * @param parser
     */
    private void findTimeSyncDelta(Parser parser) {
        CriteriaSet criteriaSet = new CriteriaSet(
                new SpellGoCriteria()
        );
        parser.parseFile(criteriaSet, message -> {
            if(criteriaSet.IsSatisfiedBy(message)) {
                if (message instanceof SpellGoMessage && timeSyncDelta == null) {
                    SpellGoMessage spellGoMessage = (SpellGoMessage) message;
                    BigInteger timeSyncDeltaBig = BigInteger.valueOf(spellGoMessage.getTimeTicks()).subtract(getEpochTime(spellGoMessage));
                    timeSyncDelta =  timeSyncDeltaBig.longValueExact();
                }
            }
        });
    }

    /**
     * Get the number of millisecond since epoch based on the timestamp of the message.
     * @param message
     * @return
     */
    private static BigInteger getEpochTime(Message message) {
        return BigInteger.valueOf(message.getTime().toEpochSecond(ZoneOffset.UTC)).multiply(BigInteger.valueOf(1000));
    }

    /**
     * first part (position):
     * gather the last known positions of every units during the entirety of the sniff's duration
     *
     * second part (combat reach and bounding radius):
     * gather the last known bounding radius and combat reach of every units during the entirety of the sniff's duration
     *
     * @param parser
     * @param globalPositionMap
     * @param globalCombatReachMap
     * @param globalBoundingRadiusMap
     */
    public void fillHistograms(Parser parser,
                                      Map<Unit, TreeMap<Long, Position>> globalPositionMap,
                                      Map<Unit, TreeMap<Long, Double>> globalCombatReachMap,
                                      Map<Unit, TreeMap<Long, Double>> globalBoundingRadiusMap
    ) {
        CriteriaSet positionCriteria = new CriteriaSet(
//                new OnMonsterMoveCriteria(),
//                new MoveUpdateCriteria(),
                new PlayerMovementCriteria()
        );
        CriteriaSet radiusAndReachCriteria = new CriteriaSet(
                new UpdateObjectCriteria(null, "UNIT_FIELD_BOUNDINGRADIUS"),
                new UpdateObjectCriteria(null, "UNIT_FIELD_COMBATREACH")
        );
        parser.parseFile(new CriteriaSet(positionCriteria, radiusAndReachCriteria), message -> {
            /**
             *  >>>>>>>>>> FILLING POSITION <<<<<<<<<<<<<<<<<<<<<<
             */
            if(positionCriteria.IsSatisfiedBy(message)){
                Unit unit = null;
//                Integer packetNumber = null;
                Long timeTicks = null;
                Position position = null;
                /*
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
                */

                if(message instanceof PlayerMoveMessage) {
                    PlayerMoveMessage playerMoveMessage = (PlayerMoveMessage) message;

                    // skip compressed movement packets since they are not well understood and i get data with them that make no sense to me so far
                    if(playerMoveMessage.getIsPartOfAnotherPacket())
                        return;

                    unit = playerMoveMessage.getUnit();
                    timeTicks = playerMoveMessage.getTimeTicks();
                    position = playerMoveMessage.getPosition();
                    if (position == null && playerMoveMessage.getOpCodeFull().startsWith("MSG_MOVE_TELEPORT"))
                        return; // skip this one. this kind of packets (MSG_MOVE_TELEPORT and MSG_MOVE_TELEPORT_ACK) may or may not contain the unit position
                    if (position == null)
                        throw new RuntimeException("fuckkk");
                } else
                    throw new RuntimeException("fuckkk");

                addElementToGlobalMap(globalPositionMap, timeTicks, unit, position);
            }
            /**
             *  >>>>>>>>>> FILLING COMBAT REACH AND BOUNDING RADIUS <<<<<<<<<<<<<<<<<<<<<<
             */
            else if(radiusAndReachCriteria.IsSatisfiedBy(message)){
                UpdateObjectMessage updateObjectMessage = (UpdateObjectMessage) message;
                Long timeTicks = getEpochTime(message).add(BigInteger.valueOf(timeSyncDelta)).longValueExact(); // timeTicks = timestamp + timeSyncDelta
                for(UpdateObjectMessage.UpdateObject updateObject : updateObjectMessage.getUpdates()){
                    Unit unit = updateObject.getUnit();
                    if(updateObject.getBoundingRadius() != null)
                        addElementToGlobalMap(globalBoundingRadiusMap, timeTicks, unit, updateObject.getBoundingRadius());

                    if(updateObject.getCombatReach() != null)
                        addElementToGlobalMap(globalCombatReachMap, timeTicks, unit, updateObject.getCombatReach());
                }
            }
        });
    }

    private static <T> void addElementToGlobalMap(Map<Unit, TreeMap<Long, T>> globalMap, Long timeTicks, Unit unit, T newElement) {
        if(newElement == null)
            throw new IllegalArgumentException("newElement should not be null");

        TreeMap<Long, T> changesMap;
        if(globalMap.containsKey(unit))
            changesMap = globalMap.get(unit);
        else{
            changesMap = new TreeMap<>();
            globalMap.put(unit, changesMap);
        }

        changesMap.put(timeTicks, newElement);
    }

    public static void processAoeSpells(Parser parser, Map<Unit,
            TreeMap<Long, Position>> globalPositionMap, Map<Unit,
            TreeMap<Long, Double>> globalCombatReachMap, Map<Unit,
            TreeMap<Long, Double>> globalBoundingRadiusMap) {
        CriteriaSet criteriaSet = new CriteriaSet(
                new SpellGoCriteria()
        );

        List<Integer> exclusionSpellList = Arrays.asList(81280, 57578, 28542, 28560, 30541, 30657, 34441,
                48721, // Blood Boil
                53197, // Starfall. has increase radius talent/glypth
                53196, // Starfall. has increase radius talent/glypth
                30413, // Shadowfury. has increase radius talent/glypth
                53385,  // Divine Storm. has increase radius talent/glypth
                3600  // Earthbind. has increase radius talent/glypth, cf 16130
        );
        List<Integer> exclusionEntryList = Arrays.asList(32325, 32322, 32340);

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
                    if(exclusionEntryList.contains(caster.getEntry()))
                        return;
                }

                // exclusion by spellId
                if(exclusionSpellList.contains(spellGoMessage.getSpellId()))
                    return;

                if(spellGoMessage.getSourceLocation()==null && spellGoMessage.getDestinationLocation()==null)
                    return;

//                // exclude spell_go messages with empty hit & miss lists <======================================================== remove this later?
//                if(spellGoMessage.getHitUnits().isEmpty() && spellGoMessage.getMissedUnits().keySet().isEmpty())
//                    return;

                // only selected AoE using the implicit target values
                List<Integer> acceptedSpellImplicitTargets = Arrays.asList(15);
                int[] spellImplicitTargets = wrapperDBC.getSpellImplicitTargets(spellGoMessage.getSpellId());
                if (spellImplicitTargets == null)
                    return;
                if(!acceptedSpellImplicitTargets.contains(spellImplicitTargets[0]) && !acceptedSpellImplicitTargets.contains(spellImplicitTargets[1]))
                    return;

                // restrict to Aoe with a small radius
                float spellRadius = wrapperDBC.highestSpellRadius(spellGoMessage.getSpellId());
                if(spellRadius >= 50.0)
                    return;

                // restrict to caster being creatures
                if(spellGoMessage.getCasterUnit() instanceof Player)
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
                    logger.warn("------------------------");
                    logger.warn("Spell at "+dateTimeFormatter.format(spellGoMessage.getTime()));
                    logger.warn("Spell location: " + location.toFormatedStringWoOrientation());
                    logger.warn("Spell id: " + spellGoMessage.getSpellId());
                    logger.warn("Spell range: " + spellRadius);
                    logger.warn("Spell effect implicit targets: " + Arrays.toString(spellImplicitTargets));
                    logger.warn("Caster: " + spellGoMessage.getCasterUnit().toString());
                    logger.warn("Caster combat reach: " + SniffExplorerUtils.getLatestKnownValue(spellGoMessage.getTimeTicks(), spellGoMessage.getCasterUnit(), globalCombatReachMap));
                    for(RecordToDisplay recordToDisplay : recordToDisplayList)
                        logger.warn(recordToDisplay.toString());
                    logger.warn("------------------------");
                }
            }
        });
    }

    private static RecordToDisplay computeAndShowPosition(SpellGoMessage spellGoMessage, Unit target, Map<Unit, TreeMap<Long, Position>> globalPositionMap, Map<Unit, TreeMap<Long, Double>> globalCombatReachMap, Map<Unit, TreeMap<Long, Double>> globalBoundingRadiusMap) {
        counter++;
        Position location;
//        if(spellGoMessage.getSourceLocation()!=null && spellGoMessage.getDestinationLocation()!=null)
//            return null;
//        else
        if(spellGoMessage.getDestinationLocation()!=null)
            location=spellGoMessage.getDestinationLocation();
        else
            location=spellGoMessage.getSourceLocation();

        Long timeTicks = spellGoMessage.getTimeTicks();
        Integer spellId = spellGoMessage.getSpellId();

        // get the latest known position of the unit
        Position targetPosition = SniffExplorerUtils.getLatestKnownValueIfSameAsNext(timeTicks, target, globalPositionMap, ERROR_TOLERANCE);
        if(targetPosition == null){
            globalValuesCounter ++;
            return null;
        }
        Double targetCombatReach = SniffExplorerUtils.getLatestKnownValue(timeTicks, target, globalCombatReachMap);
        Double casterCombatReach = SniffExplorerUtils.getLatestKnownValue(timeTicks, spellGoMessage.getCaster(), globalCombatReachMap);
//        Double targetBoundingRadius = SniffExplorerUtils.getLatestKnownValue(packetNumber, target, globalBoundingRadiusMap);
//        Double casterBoundingRadius = SniffExplorerUtils.getLatestKnownValue(packetNumber, spellGoMessage.getCaster(), globalBoundingRadiusMap);

        double distance3D = GeometryUtils.getDistance3D(location, targetPosition);
//        double distance2D = GeometryUtils.getDistance2D(location, targetPosition);
//        logger.warn("distance 3D: "+distance3D+" distance 2D: "+distance2D+" Position: "+position.toFormatedString());
//        logger.warn(unit.getGUID() + " " +position.toFormatedStringWoOrientation() + " " + distance3D);

        float spellRadius = wrapperDBC.highestSpellRadius(spellId);
        boolean distanceCondition = distance3D >= spellRadius;

//        if(spellRadius > 0 && !spellGoMessage.getCasterUnit().equals(target) && distance3D >= spellRadius + casterCombatReach && distance3D <= spellRadius + targetCombatReach) {
        if(spellRadius > 0 && !spellGoMessage.getCasterUnit().equals(target) && distanceCondition) {
//        if(spellRadius > 0 && !spellGoMessage.getCasterUnit().equals(target) && distance3D > spellRadius + targetCombatReach + ERROR_TOLERANCE + 2 && distance3D < 90)  {
            predicateDistanceSatisfiedCounter++;
            return new RecordToDisplay(target, targetPosition.toFormatedStringWoOrientation(), distance3D, targetCombatReach != null ? targetCombatReach : 0.0);
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
