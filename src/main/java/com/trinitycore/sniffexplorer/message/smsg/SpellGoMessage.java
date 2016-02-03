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

    private List<Unit> hitUnits=new ArrayList<>();
    private Map<Unit, MissType> missedUnits=new HashMap<>();
    
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
    }

    @Override
    public boolean contains(Integer relatedEntry) {
        return false;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contains(Long relatedGUID) {
        return false;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void display(PrintWriter writer) { // todo: FINISH THIS. ATM ITS A QUICK AND DIRTY COPY PAST OF SPELL_START_MESSAGE
        super.display(writer);

        writer.format("Spell ID: %6d. Caster Unit: %s. ", getSpellId(), getCasterUnit().toString());
        if(getCaster() instanceof Item)
            writer.format(" Item caster GUID: %18s.", getCaster().getGUID());
//        writer.format("Target Flags: %2d. Target Unit: %s Number: %d.", getTargetFlags(), getTargetUnit(), getId());
        writer.format(" Hit list: %s.", hitUnits);
        writer.format(" Miss list: %s.", missedUnits);
        writer.println();
    }
    
}
