/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinitycore.sniffexplorer.message.smsg;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.trinitycore.sniffexplorer.exceptions.ParseException;
import com.trinitycore.sniffexplorer.game.data.Position;
import com.trinitycore.sniffexplorer.game.data.SplineType;
import com.trinitycore.sniffexplorer.game.entities.IdentifiableByEntry;
import com.trinitycore.sniffexplorer.game.entities.Unit;
import com.trinitycore.sniffexplorer.message.Message;
import com.trinitycore.sniffexplorer.message.ParseUtils;

/**
 *
 * @author chaouki
 */
public class OnMonsterMoveMessage extends Message {

    private Unit unit;
    private SplineType splineType;
    private Unit facingUnit;
    private Double facingAngle;
    private List<Position> positions=new ArrayList<>();
    private String splineFlagsLine;
    private Integer moveTime;

    /*
ServerToClient: SMSG_ON_MONSTER_MOVE (0x00DD) Length: 53 ConnIdx: 0 Time: 07/15/2010 21:40:26.000 Number: 262441
GUID: Full: 0xF130003A210A8000 Type: Creature Entry: 14881 Low: 688128
Toggle AnimTierInTrans: false
Position: X: 5262.02 Y: 1970.027 Z: 707.6981
Move Ticks: 11432742
Spline Type: Normal (0)
Spline Flags: None (0)
Move Time: 3263
Waypoints: 2
Waypoint Endpoint: X: 5268.676 Y: 1965.316 Z: 707.6967
[1] Waypoint: X: 5263.348 Y: 1969.421 Z: 708.1974
 */

/*
ServerToClient: SMSG_ON_MONSTER_MOVE (0x29A5) Length: 25 ConnIdx: 2 Time: 04/14/2012 09:12:20.759 Number: 6030
GUID: Full: 0xF130C89200003CBF Type: Creature Entry: 51346 Low: 15551
Toggle AnimTierInTrans: false
Position: X: 1692.671 Y: -4178.664 Z: 143.8051
Move Ticks: 65427578
Spline Type: Stop (1)
 */

/*
ServerToClient: SMSG_ON_MONSTER_MOVE (0x29A5) Length: 58 ConnIdx: 2 Time: 04/14/2012 09:08:27.787 Number: 379
GUID: Full: 0xF130BDE100461877 Type: Creature Entry: 48609 Low: 4593783
Toggle AnimTierInTrans: false
Position: X: 5886.37 Y: 510.1203 Z: 641.6531
Move Ticks: 157085400
Spline Type: FacingTarget (3)
Facing GUID: Full: 0x60000000456E3D8 Type: Player Low: 72803288 Name: Salantharasa
Spline Flags: Unknown5, Unknown6 (3145728)
Move Time: 89
Waypoints: 1
Waypoint Endpoint: X: 5886.97 Y: 510.2612 Z: 641.5698
 */

/*
ServerToClient: SMSG_ON_MONSTER_MOVE (0x29A5) Length: 54 ConnIdx: 2 Time: 04/14/2012 09:08:27.787 Number: 380
GUID: Full: 0xF140B5130B003110 Type: Pet Low: 184561936
Toggle AnimTierInTrans: false
Position: X: 5887.709 Y: 508.2559 Z: 641.5698
Move Ticks: 157085401
Spline Type: FacingAngle (4)
Facing Angle: 2.824587
Spline Flags: None (0)
Move Time: 0
Waypoints: 1
Waypoint Endpoint: X: 5887.709 Y: 508.2559 Z: 641.5698
 */

    @Override
    public void initialize(List<String> lines) throws ParseException {
        this.unit = ParseUtils.parseGuidRemovePrefix(lines.get(1), "GUID");

        Position startPosition = ParseUtils.parsePositionRemovePrefix(lines.get(3), "Position");
        positions.add(startPosition);

        String splineTypeString = ParseUtils.removePrefixAndGetFirstElement(lines.get(5), "Spline Type");
        this.splineType=SplineType.valueOf(splineTypeString);

        if(splineType.equals(SplineType.Stop))
            return;
        else if(splineType.equals(SplineType.FacingTarget))
            facingUnit=ParseUtils.parseGuidRemovePrefix(lines.get(6), "Facing GUID");
        else if(splineType.equals(SplineType.FacingAngle)) {
            String angleString = ParseUtils.removePrefix(lines.get(6), "Facing Angle");
            facingAngle=Double.parseDouble(angleString);
        }

        int splineFlagIdx = ParseUtils.getLineIndexThatStartWithPrefix(lines, "Spline Flags");
        splineFlagsLine = lines.get(splineFlagIdx);
        if(splineFlagsLine.contains("Catmullrom"))
            return;

        // move time
        int moveTimeIdx = ParseUtils.getLineIndexThatStartWithPrefix(lines, "Move Time");
        String moveTimeString = ParseUtils.removePrefix(lines.get(moveTimeIdx), "Move Time");
        moveTime = Integer.valueOf(moveTimeString);

        int waypointCountIdx = ParseUtils.getLineIndexThatStartWithPrefix(lines, "Waypoints");
        Integer waypointCount= Integer.valueOf(ParseUtils.removePrefix(lines.get(waypointCountIdx), "Waypoints"));

        Position endPosition = null;
        if(!splineFlagsLine.contains("Flying"))
            endPosition= ParseUtils.parsePositionRemovePrefix(lines.get(++waypointCountIdx), "Waypoint Endpoint");

        for(int i=0 ; i<waypointCount-1 ; i++){
            Position waypoint = ParseUtils.parsePositionRemovePrefix(lines.get(++waypointCountIdx), "Waypoint");
            positions.add(waypoint);
        }


        if(endPosition!=null)
            positions.add(endPosition);
    }

    @Override
    public boolean contains(Integer relatedEntry) {
        if(!(unit instanceof IdentifiableByEntry))
            return false;

        IdentifiableByEntry identifiableByEntry=(IdentifiableByEntry) unit;
        if(identifiableByEntry.getEntry().equals(relatedEntry))
            return true;
        else
            return false;
    }

    @Override
    public boolean contains(String relatedGUID) {
        if(unit.getGUID().equals(relatedGUID))
            return true;
        else
            return false;
    }

    @Override
    public void display(PrintWriter writer) {
        super.display(writer);
        writer.println();
        writer.println(unit);
        writer.println(splineType);
        switch (splineType){
            case FacingTarget: writer.println(facingUnit); break;
            case FacingAngle: writer.println(facingAngle); break;
        }
        writer.println(splineFlagsLine);
        writer.println("move time: "+moveTime);
        writer.println(positions.size()+" points:");
        for(Position position:positions)
            writer.println(position.toFormatedString());
    }

    public Unit getUnit() {
        return unit;
    }

    public SplineType getSplineType() {
        return splineType;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public String getSplineFlagsLine() {
        return splineFlagsLine;
    }

    public Integer getMoveTime() {
        return moveTime;
    }

    public Unit getFacingUnit() {
        return facingUnit;
    }

    public Double getFacingAngle() {
        return facingAngle;
    }
}
