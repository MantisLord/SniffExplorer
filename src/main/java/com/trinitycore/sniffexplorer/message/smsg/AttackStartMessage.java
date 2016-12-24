package com.trinitycore.sniffexplorer.message.smsg;

import com.trinitycore.sniffexplorer.exceptions.ParseException;
import com.trinitycore.sniffexplorer.game.entities.IdentifiableByEntry;
import com.trinitycore.sniffexplorer.game.entities.Unit;
import com.trinitycore.sniffexplorer.message.Message;
import com.trinitycore.sniffexplorer.message.ParseUtils;

import java.io.PrintWriter;
import java.util.List;

/**
 * Created by chaouki on 06-04-16.
 */
public class AttackStartMessage extends Message {

/*
ServerToClient: SMSG_ATTACK_START (0x2D15) Length: 16 ConnIdx: 2 Time: 06/16/2012 22:59:08.115 Number: 46112
GUID: Full: 0xF13040390000D679 Type: Creature Entry: 16441 Low: 54905
Victim GUID: Full: 0xF1305E8F0000D723 Type: Creature Entry: 24207 Low: 55075
 */

    private Unit attacker;
    private Unit target;

    @Override
    public void initialize(List<String> lines) throws ParseException {
        super.initialize(lines);
        this.attacker = ParseUtils.parseGuidRemovePrefix(lines.get(1), "GUID");
        this.target = ParseUtils.parseGuidRemovePrefix(lines.get(2), "Victim GUID");
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
    }

    public Unit getAttacker() {
        return attacker;
    }

    public Unit getTarget() {
        return target;
    }
}
