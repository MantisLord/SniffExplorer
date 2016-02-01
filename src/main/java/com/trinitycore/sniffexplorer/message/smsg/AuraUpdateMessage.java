package com.trinitycore.sniffexplorer.message.smsg;

import com.trinitycore.sniffexplorer.exceptions.ParseException;
import com.trinitycore.sniffexplorer.gameentities.*;
import com.trinitycore.sniffexplorer.message.Message;
import com.trinitycore.sniffexplorer.message.OpCode;
import com.trinitycore.sniffexplorer.message.OpCodeType;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

//ServerToClient: SMSG_AURA_UPDATE (0x4707) Length: 30 ConnIdx: 2 Time: 06/16/2012 23:41:13.241 Number: 170021
//GUID: Full: 0xF1303E9D0000BE4A Type: Creature Entry: 16029 Low: 48714
//[0] Slot: 1
//[0] Spell ID: 49560 (49560)
//[0] Flags: EffectIndex1, Duration, Negative (162)
//[0] Level: 85
//[0] Charges: 0
//[0] Caster GUID: Full: *** Type: Player Low: *** Name: PLAYER_1_DK
//[0] Max Duration: 3000
//[0] Duration: 3000

/**
 * Created by chaouki on 01-02-16.
 */
public class AuraUpdateMessage extends Message {

    private Unit owner;
    private Integer spellId;

    @Override
    public void initialize(List<String> lines) throws ParseException {

        String[] words=lines.get(1).split("\\s+");
        if(!words[0].equals("GUID:") || !words[1].equals("Full:") || !words[3].equals("Type:"))
            throw new ParseException();

        if(words[4].equals("Creature"))
            owner=new Creature(Integer.parseInt(words[6]), words[2]);
        else if(words[4].equals("Player"))
            owner=new Player((words.length==10)?words[8]:"", words[2]); // sometimes, the name is missing.
        else if(words[4].equals("Vehicle"))
            owner=new Vehicule(Integer.parseInt(words[6]), words[2]);
        else if(words[4].equals("Pet"))
            owner=new Pet(words[2]);
        else if(words[4].equals("GameObject")) // @todo: harmonize the way GOs are modeled and stored.
            owner=new GameObject(Integer.parseInt(words[6]), words[2]);
        else
            throw new ParseException();

        /**
         *  Spell ID
         */
        words=lines.get(3).split("\\s+");
        if(!words[1].equals("Spell") || !words[2].equals("ID:"))
            throw new ParseException();

        spellId=Integer.valueOf(words[3]);
    }

    @Override
    public OpCode getOpCode() {
        return OpCode.SMSG_AURA_UPDATE;
    }

    @Override
    public OpCodeType getOpCodeType() {
        return OpCodeType.SMSG;
    }

    @Override
    public boolean contains(Integer relatedEntry) {
        return false;
    }

    @Override
    public boolean contains(Long relatedGUID) {
        return false;
    }

    @Override
    public void display(PrintWriter writer) {
        DateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
        writer.print(getOpCode().toString()+" "+dateFormat.format(getDate())+" ");

        writer.format("Spell ID: %6d. Owner Unit: %s. ", getSpellId(), getOwner().toString());
        writer.println();
    }

    public Unit getOwner() {
        return owner;
    }

    public Integer getSpellId() {
        return spellId;
    }
}
