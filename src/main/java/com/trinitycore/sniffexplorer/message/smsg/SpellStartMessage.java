/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinitycore.sniffexplorer.message.smsg;

import java.io.PrintWriter;
import java.util.List;

import com.trinitycore.sniffexplorer.game.entities.*;
import com.trinitycore.sniffexplorer.message.OpCodeType;
import com.trinitycore.sniffexplorer.exceptions.ParseException;
import com.trinitycore.sniffexplorer.message.OpCode;
import com.trinitycore.sniffexplorer.message.ParseUtils;

/** Class which represent a SMSG_SPELL_START message
 * 
 * https://github.com/TrinityCore/WowPacketParser/blob/master/WowPacketParser/Parsing/Parsers/SpellHandler.cs#L726
 *
 * @author chaouki
 */
public class SpellStartMessage extends SpellMessage {
    
    @Override
    public OpCode getOpCode() {
        return OpCode.SMSG_SPELL_START;
    }

    @Override
    public OpCodeType getOpCodeType() {
        return OpCodeType.SMSG;
    }
    
    private Integer targetFlags;
    private Unit targetUnit;
    

    @Override
    public void initialize(List<String> lines) throws ParseException {
        super.initialize(lines);
        
        /**
         * Target(s)
         */
        int targetFlagsIndex=0;
        while(!lines.get(targetFlagsIndex).startsWith("Target Flags")) // each SPELL_GO message should have this line. no need to check for boundaries.
            targetFlagsIndex++;
        String[] words=lines.get(targetFlagsIndex).split("\\s+"); // Target Flags
        if(!words[0].equals("Target") || !words[1].equals("Flags:"))
            throw new ParseException();
        
        // Extract the integer inside the string "Target Flags: (XX)"
        String number = words[words.length-1].replace(')', ' ').replace('(', ' ').trim();
        targetFlags=Integer.valueOf(number);
        
        if(targetFlags==0){ // case Self
            targetUnit=getCasterUnit();
        }
        else if(targetFlags==2){ // case Unit
            targetUnit= ParseUtils.parseGuidRemovePrefix(lines.get(targetFlagsIndex+1), "Target GUID");
        }
        else if(targetFlags==16)                                    // Item
            log.warn("targetFlags 16 unsupported yet.");
        else if(targetFlags==64)                                    // DestinationLocation
            log.warn("targetFlags 64 unsupported yet.");
        else if(targetFlags==96)                                    // SourceLocation, DestinationLocation
            log.warn("targetFlags 96 unsupported yet.");
        else if(targetFlags==2048)                                  // GameObject
            log.warn("targetFlags 2048 unsupported yet.");
        else
            throw new ParseException("Target Flag "+targetFlags+" unsupported yet.");
    }

        @Override
    public boolean contains(Integer relatedEntry) {
        // the caster could have the specified entry...
        if(getCasterUnit() instanceof IdentifiableByEntry){
            IdentifiableByEntry caster=(IdentifiableByEntry) getCasterUnit();
            if(caster.getEntry().equals(relatedEntry))
                return true;
        }
        
        // or the target...
        if(getTargetUnit() instanceof IdentifiableByEntry){
            IdentifiableByEntry target=(IdentifiableByEntry) getTargetUnit();
            if(target.getEntry().equals(relatedEntry))
                return true;
        }
        
        // and that's it
        return false;
    }

    @Override
    public boolean contains(String relatedGUID) {
        if(getCasterUnit().getGUID().equals(relatedGUID))
            return true;
        
        if(getTargetUnit().getGUID().equals(relatedGUID))
            return true;
        
        return false;
    }

    @Override
    public void display(PrintWriter writer) {
        super.display(writer);

        writer.format("Spell ID: %6d. Caster Unit: %s. ", getSpellId(), getCasterUnit().toString());
        if(getCaster() instanceof Item)
            writer.format("Item caster GUID: %18s. ", getCaster().getGUID());
//        writer.format("Target Flags: %2d. Target Unit: %s Number: %d.", getTargetFlags(), getTargetUnit(), getId());
        writer.format("Target Flags: %2d. Target Unit: %s.", getTargetFlags(), getTargetUnit());
        writer.println();
    }
    
    
    /* template
 0   ServerToClient: SMSG_SPELL_START (0x6415) Length: 53 ConnIdx: 2 Time: 06/17/2012 01:41:34.793 Number: 413913
 1   Caster GUID: Full: 0xF130CD160001CF6B Type: Creature Entry: 52502 Low: 118635
 2   Caster Unit GUID: Full: 0xF130CD160001CF6B Type: Creature Entry: 52502 Low: 118635
 3   Cast Count: 0
 4   Spell ID: 97155 (97155)
 5   Cast Flags: HasTrajectory, Projectile (34)
 6   Time: 0
 7   Time2: 0
 8   Target Flags: Unit (2)
 9   Target GUID: Full: 0xF130CD1800027FCC Type: Creature Entry: 52504 Low: 163788
 10  Ammo Display ID: 2414
 11  Ammo Inventory Type: Ammo (24)
    */
    
    /*
    ServerToClient: SMSG_SPELL_START (0x6415) Length: 39 ConnIdx: 2 Time: 06/16/2012 22:48:02.677 Number: 7012
    Caster GUID: Full: 0xFFFFEEEEDDDDCCC Type: Player Low: 11112222 Name: XXXXX
    Caster Unit GUID: Full: 0xFFFFEEEEDDDDCCC Type: Player Low: 11112222 Name: XXXXX
    Cast Count: 0
    Spell ID: 99055 (99055)
    Cast Flags: PendingCast, HasTrajectory, Unknown3 (11)
    Time: 0
    Time2: 0
    Target Flags: Unit (2)
    Target GUID: Full: 0xFFFFEEEEDDDDCCC Type: Player Low: 11112222 Name: XXXXX
    */
    
    /*
    ServerToClient: SMSG_SPELL_START (0x6415) Length: 33 ConnIdx: 2 Time: 06/17/2012 01:41:15.932 Number: 412134
    Caster GUID: Full: 0xFFFFEEEEDDDDCCC Type: Player Low: 11112222 Name: XXXXX
    Caster Unit GUID: Full: 0xFFFFEEEEDDDDCCC Type: Player Low: 11112222 Name: XXXXX
    Cast Count: 0
    Spell ID: 54181 (54181)
    Cast Flags: PendingCast, HasTrajectory, Unknown3 (11)
    Time: 0
    Time2: 0
    Target Flags: Self (0)
    */



    public Unit getTargetUnit() {
        return targetUnit;
    }

    public void setTargetUnit(Unit targetUnit) {
        this.targetUnit = targetUnit;
    }

    public Integer getTargetFlags() {
        return targetFlags;
    }

    public void setTargetFlags(Integer targetFlags) {
        this.targetFlags = targetFlags;
    }
}
