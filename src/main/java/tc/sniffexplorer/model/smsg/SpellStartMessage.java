/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.model.smsg;

import java.util.List;
import tc.sniffexplorer.exceptions.ParseException;
import tc.sniffexplorer.model.Message;
import tc.sniffexplorer.model.entities.Creature;
import tc.sniffexplorer.model.entities.Pet;
import tc.sniffexplorer.model.entities.Player;
import tc.sniffexplorer.model.entities.Unit;
import tc.sniffexplorer.model.entities.Vehicule;

/** Class which represent SMSG_SPELL_START messages
 *
 * @author chaouki
 */
public class SpellStartMessage extends Message {
    private Unit casterUnit;
    private String itemCasterGUID;
    
    private Integer targetFlags;
    private Unit targetUnit;
    
    private Integer spellId;

    @Override
    public void initialize(List<String> lines) throws ParseException {
        /**
         * Caster
         */
        String[] words=lines.get(1).split("\\s+");
        if(!words[0].equals("Caster") || (!words[5].equals("Creature") && !words[5].equals("Player") && !words[5].equals("Vehicle") && !words[5].equals("Pet") && !words[5].equals("Item")))
            throw new ParseException();
            
        if(words[5].equals("Creature"))
            casterUnit=new Creature(words[7], words[3]);
        else if(words[5].equals("Player"))
            casterUnit=new Player((words.length==10)?words[9]:"", words[3]);
        else if(words[5].equals("Vehicle"))
            casterUnit=new Vehicule(words[7], words[3]);
        else if(words[5].equals("Pet"))
            casterUnit=new Pet(words[3]);
        else if(words[5].equals("Item")){
            itemCasterGUID=words[3];
            
            words=lines.get(2).split("\\s+");
            if(words[6].equals("Player"))
                casterUnit=new Player((words.length==11)?words[10]:"", words[4]);
            else
                throw new ParseException();
        }
        
        /**
         *  Spell ID
         */
        words=lines.get(4).split("\\s+");
        if(!words[0].equals("Spell") || !words[1].equals("ID:"))
            throw new ParseException();
        
        spellId=Integer.valueOf(words[2]);
        
        /**
         * Target
         */
        words=lines.get(8).split("\\s+"); // Target Flags
        if(!words[0].equals("Target") || !words[1].equals("Flags:"))
            throw new ParseException();
        
        targetFlags=Integer.valueOf(words[words.length-1].substring(1, words.length-2)); // String "(XXX)" to Integer XXX
        
        if(targetFlags==0){ // case Self
            targetUnit=casterUnit;
        }
        else if(targetFlags==2){ // case Unit
            words=lines.get(9).split("\\s+");
            if(!words[0].equals("Target") || (!words[5].equals("Creature") && !words[5].equals("Player") && !words[5].equals("Vehicle")  && !words[5].equals("Pet")))
                throw new ParseException();
            
            if(words[5].equals("Creature"))
                targetUnit=new Creature(words[7], words[3]);
            else if(words[5].equals("Player"))
                targetUnit=new Player((words.length==10)?words[9]:"", words[3]);
            else if(words[5].equals("Vehicle"))
                targetUnit=new Vehicule(words[7], words[3]);
            else if(words[5].equals("Pet"))
                targetUnit=new Pet(words[3]);
        } 
//        else if()
//            ;
        else
            throw new ParseException("Target Flag "+targetFlags+" unsupported yet");
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
    Caster GUID: Full: 0x60000000456E3D8 Type: Player Low: 72803288 Name: Salantharasa
    Caster Unit GUID: Full: 0x60000000456E3D8 Type: Player Low: 72803288 Name: Salantharasa
    Cast Count: 0
    Spell ID: 54181 (54181)
    Cast Flags: PendingCast, HasTrajectory, Unknown3 (11)
    Time: 0
    Time2: 0
    Target Flags: Self (0)
    */

    public Unit getCasterUnit() {
        return casterUnit;
    }

    public void setCasterUnit(Unit casterUnit) {
        this.casterUnit = casterUnit;
    }

    public String getItemCasterGUID() {
        return itemCasterGUID;
    }

    public void setItemCasterGUID(String itemCasterGUID) {
        this.itemCasterGUID = itemCasterGUID;
    }

    public Unit getTargetUnit() {
        return targetUnit;
    }

    public void setTargetUnit(Unit targetUnit) {
        this.targetUnit = targetUnit;
    }

    

    public Integer getSpellId() {
        return spellId;
    }

    public void setSpellId(Integer spellId) {
        this.spellId = spellId;
    }
    
}
