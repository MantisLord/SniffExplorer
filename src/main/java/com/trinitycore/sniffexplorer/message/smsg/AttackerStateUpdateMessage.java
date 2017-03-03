package com.trinitycore.sniffexplorer.message.smsg;

import com.trinitycore.sniffexplorer.exceptions.ParseException;
import com.trinitycore.sniffexplorer.game.entities.IdentifiableByEntry;
import com.trinitycore.sniffexplorer.game.entities.Unit;
import com.trinitycore.sniffexplorer.message.Message;
import com.trinitycore.sniffexplorer.message.ParseUtils;

import java.io.PrintWriter;
import java.util.List;

/**
 * SMSG_ATTACKER_STATE_UPDATE
 * Created by chaouki on 06-04-16.
 */
public class AttackerStateUpdateMessage extends Message {

/*
ServerToClient: SMSG_ATTACKER_STATE_UPDATE (0x0B25) Length: 51 ConnIdx: 2 Time: 06/16/2012 22:54:26.674 Number: 27745
HitInfo: HITINFO_AFFECTS_VICTIM, HITINFO_UNK10, HITINFO_UNK11, HITINFO_RAGE_GAIN (8391682)
AttackerGUID: Full: 0xF130402B0000D167 Type: Creature Entry: 16427 Low: 53607
TargetGUID: Full: 0x60000000320D4F0 Type: Player Low: 52483312 Name: Gansinolo
Damage: 197
OverDamage: -1
Count: 1
[0] SchoolMask: 1
[0] Float Damage: 197
[0] Int Damage: 197
VictimState: VICTIMSTATE_NORMAL (1)
Unk Attacker State 0: 0
Melee Spell Id: 0 (0)
Rage Gained: 130
 */

    private Unit attacker;
    private Unit target;
    private Long damage;

    @Override
    public void initialize(List<String> lines) throws ParseException {
        this.attacker = ParseUtils.parseGuidRemovePrefix(lines.get(2), "AttackerGUID");
        this.target = ParseUtils.parseGuidRemovePrefix(lines.get(3), "TargetGUID");
        this.damage= Long.valueOf(ParseUtils.removePrefix(lines.get(4), "Damage"));
    }

    @Override
    public boolean contains(Integer relatedEntry) {
        if(attacker instanceof IdentifiableByEntry){
            IdentifiableByEntry identifiableByEntry=(IdentifiableByEntry) attacker;
            if(identifiableByEntry.getEntry().equals(relatedEntry))
                return true;
        }

        if(target instanceof IdentifiableByEntry){
            IdentifiableByEntry identifiableByEntry=(IdentifiableByEntry) target;
            if(identifiableByEntry.getEntry().equals(relatedEntry))
                return true;
        }

        return false;
    }

    @Override
    public boolean contains(String relatedGUID) {
        if(attacker.getGUID().equals(relatedGUID))
            return true;

        if(target.getGUID().equals(relatedGUID))
            return true;

        return false;
    }



    @Override
    public void display(PrintWriter writer) {
        super.display(writer);
        writer.println();
        writer.println(attacker);
        writer.println(target);
        writer.println(damage);
    }

    public Unit getAttacker() {
        return attacker;
    }

    public Unit getTarget() {
        return target;
    }

    public Long getDamage() {
        return damage;
    }
}
