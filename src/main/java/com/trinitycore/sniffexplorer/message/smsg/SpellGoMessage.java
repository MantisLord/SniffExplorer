/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinitycore.sniffexplorer.message.smsg;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.trinitycore.sniffexplorer.exceptions.ParseException;
import com.trinitycore.sniffexplorer.game.data.MissType;
import com.trinitycore.sniffexplorer.game.data.Position;
import com.trinitycore.sniffexplorer.game.entities.Item;
import com.trinitycore.sniffexplorer.game.entities.Unit;
import com.trinitycore.sniffexplorer.message.OpCodeType;
import com.trinitycore.sniffexplorer.message.OpCode;
import com.trinitycore.sniffexplorer.message.ParseUtils;

/** Class which represent SMSG_SPELL_GO messages
 *
 * @author chaouki
 */
public class SpellGoMessage extends SpellMessage {

/*
ServerToClient: SMSG_SPELL_GO (0x0132) Length: 91 ConnIdx: 0 Time: 07/15/2010 21:18:47.000 Number: 198196
Caster GUID: Full: 0x28000000215A3FE Type: Player Low: 34972670 Name: Kalvaan
Caster Unit GUID: Full: 0x28000000215A3FE Type: Player Low: 34972670 Name: Kalvaan
Cast Count: 101
Spell ID: 42917 (42917)
Cast Flags: Unknown7, PredictedPower, Unknown12, Unknown16 (280832)
Time: 3623440014
Hit Count: 5
[0] Hit GUID: Full: 0xF13000951C0A820F Type: Creature Entry: 38172 Low: 688655
[1] Hit GUID: Full: 0xF13000951D0A8210 Type: Creature Entry: 38173 Low: 688656
[2] Hit GUID: Full: 0xF13000951D0A8211 Type: Creature Entry: 38173 Low: 688657
[3] Hit GUID: Full: 0xF1300095210A8213 Type: Creature Entry: 38177 Low: 688659
[4] Hit GUID: Full: 0xF1300096A70A8579 Type: Creature Entry: 38567 Low: 689529
Miss Count: 0
Target Flags: Unit, SourceLocation (34)
Target GUID: 0x0
Source Transport GUID: 0x0
Source Position: X: 5271.361 Y: 2035.772 Z: 709.3193
Rune Cooldown: 13316
 */

    private List<Unit> hitUnits=new ArrayList<>();
    private Map<Unit, MissType> missedUnits=new HashMap<>();
    private Position sourceLocation;
    private Position destinationLocation;
    private Long timeTicks;

    @Override
    public OpCode getOpCode() {
        return OpCode.SMSG_SPELL_GO;
    }

    @Override
    public OpCodeType getOpCodeType() {
        return OpCodeType.SMSG;
    }

    @Override
    public void initialize(List<String> lines) throws ParseException {
        super.initialize(lines);
        
        /**
         * Target(s)
         */

        /**
         * Hit list
         */
        // look for the hit section of the message. set hitIndex to the index of the "Hit Count" line.
        int hitIndex=0;
        while(!lines.get(hitIndex).startsWith("Hit Count")) // each SPELL_GO message should have this line. no need to check for boundaries.
            hitIndex++;

        /*
        Hit Count: 2
        [0] Hit GUID: Full: 0xF1306C4000517CEB Type: Creature Entry: 27712 Low: 5340395
        [1] Hit GUID: Full: 0xF1306C4000517CEB Type: Creature Entry: 27712 Low: 5340395

        Miss Count: 1
        [0] Miss GUID: Full: 0xF1306C4000517CEB Type: Creature Entry: 27712 Low: 5340395
        [0] Miss Type: Miss (1)
         */
        // get the hit count
        String hitCountLine=lines.get(hitIndex);
        Integer hitCount = Integer.parseInt(hitCountLine.split("\\s+")[2]);
        for(int i=0; i<hitCount; i++){
            hitIndex++;
            hitUnits.add(ParseUtils.parseGuidRemovePrefix(lines.get(hitIndex), "Hit GUID"));
        }

        /**
         * Miss list
         */
        // look for the miss section of the message. set missIndex to the index of the "Miss Count" line.
        int missIndex=hitIndex+1;
        while(!lines.get(missIndex).startsWith("Miss Count"))
            missIndex++;
        // get the miss count
        String missCountLine=lines.get(missIndex);
        Integer missCount = Integer.parseInt(missCountLine.split("\\s+")[2]);
        missIndex++;
        for(int i=0; i<missCount; i++, missIndex+=2){
            Unit missUnit = ParseUtils.parseGuidRemovePrefix(lines.get(missIndex), "Miss GUID");
            MissType missType=MissType.getMissType(ParseUtils.removePrefixAndGetFirstElement(lines.get(missIndex + 1), "Miss Type"));
            missedUnits.put(missUnit, missType);
        }

        /**
         * Source Position and Destination Position
         */

        Integer sourceMask=getTargetFlags() & 32;
        Integer destinationMask=getTargetFlags() & 64;
        if(sourceMask != 0){
            String sourcePositionString = ParseUtils.getLineThatStartWithPrefix(lines, "Source Position");
            sourceLocation=ParseUtils.parsePositionRemovePrefix(sourcePositionString, "Source Position");
        }
        if(destinationMask != 0){
            String sourcePositionString = ParseUtils.getLineThatStartWithPrefix(lines, "Destination Position");
            destinationLocation=ParseUtils.parsePositionRemovePrefix(sourcePositionString, "Destination Position");
        }

        /**
         * Time tick
         */

        String timeTickLine = ParseUtils.getLineThatStartWithPrefix(lines, "Time");
        timeTicks=Long.parseLong(ParseUtils.removePrefix(timeTickLine, "Time"));
    }

    @Override
    public boolean contains(Integer relatedEntry) {
        return false;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contains(String relatedGUID) {
        return false;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void display(PrintWriter writer) { // todo: FINISH THIS. ATM ITS A QUICK AND DIRTY COPY PAST OF SPELL_START_MESSAGE
        super.display(writer);

        writer.format("Spell ID: %6d. Caster Unit: %s. ", getSpellId(), getCasterUnit());
        if(getCaster() instanceof Item)
            writer.format(" Item caster GUID: %18s.", getCaster().getGUID());
//        writer.format("Target Flags: %2d. Target Unit: %s Number: %d.", getTargetFlags(), getTargetUnit(), getId());
        writer.format(" Hit list: %s.", hitUnits);
        writer.format(" Miss list: %s.", missedUnits);
        writer.println();
    }

    public List<Unit> getHitUnits() {
        return hitUnits;
    }

    public Map<Unit, MissType> getMissedUnits() {
        return missedUnits;
    }

    public Position getSourceLocation() {
        return sourceLocation;
    }

    public Position getDestinationLocation() {
        return destinationLocation;
    }

    public Long getTimeTicks() {
        return timeTicks;
    }
}
