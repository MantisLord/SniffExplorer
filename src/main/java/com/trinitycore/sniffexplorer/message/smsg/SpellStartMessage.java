/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinitycore.sniffexplorer.message.smsg;

import java.io.PrintWriter;
import java.util.List;

import com.trinitycore.sniffexplorer.game.entities.*;
import com.trinitycore.sniffexplorer.exceptions.ParseException;

/** Class which represent a SMSG_SPELL_START message
 * 
 * https://github.com/TrinityCore/WowPacketParser/blob/master/WowPacketParser/Parsing/Parsers/SpellHandler.cs#L726
 *
 * @author chaouki
 */
public class SpellStartMessage extends SpellMessage {


    @Override
    public void initialize(List<String> lines) throws ParseException {
        super.initialize(lines);
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
}
