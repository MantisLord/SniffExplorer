/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.model.smsg;

import java.util.List;
import tc.sniffexplorer.exceptions.ParseException;
import tc.sniffexplorer.model.OpCode;
import tc.sniffexplorer.model.OpCodeType;
import tc.sniffexplorer.model.gameentities.Creature;
import tc.sniffexplorer.model.gameentities.GameObject;
import tc.sniffexplorer.model.gameentities.Pet;
import tc.sniffexplorer.model.gameentities.Player;
import tc.sniffexplorer.model.gameentities.Unit;
import tc.sniffexplorer.model.gameentities.Vehicule;

/** Class which represent SMSG_SPELL_START messages
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
        String[] words=lines.get(8).split("\\s+"); // Target Flags
        if(!words[0].equals("Target") || !words[1].equals("Flags:"))
            throw new ParseException();
        
        String number = words[words.length-1].replace(')', ' ').replace('(', ' ').trim();
        
        targetFlags=Integer.valueOf(number); // String "(XXX)" to Integer XXX
        if(targetFlags==6)
            log.debug(null);
        
        if(targetFlags==0){ // case Self
            targetUnit=getCasterUnit();
        }
        else if(targetFlags==2){ // case Unit
            words=lines.get(9).split("\\s+");
            if(!words[0].equals("Target") || 
                    (!words[5].equals("Creature") 
                    && !words[5].equals("Player") 
                    && !words[5].equals("Vehicle") 
                    && !words[5].equals("Pet") 
                    && !words[5].equals("Item")
                    && !words[5].equals("GameObject")))
                throw new ParseException();
            
            if(words[5].equals("Creature"))
                targetUnit=new Creature(Integer.parseInt(words[7]), words[3]);
            else if(words[5].equals("Player"))
                targetUnit=new Player((words.length==10)?words[9]:"", words[3]);
            else if(words[5].equals("Vehicle"))
                targetUnit=new Vehicule(Integer.parseInt(words[7]), words[3]);
            else if(words[5].equals("Pet"))
                targetUnit=new Pet(words[3]);
            else if(words[5].equals("GameObject")) // @todo: harmonize the way GOs are modeled and stored.
                targetUnit=new GameObject(Integer.parseInt(words[7]), words[3]);
            else
                throw new ParseException();
        }
        else if(targetFlags==16)                                    // Item
            log.info("targetFlags 16 unsupported yet.");
        else if(targetFlags==64)                                    // DestinationLocation
            log.info("targetFlags 64 unsupported yet.");
        else if(targetFlags==96)                                    // SourceLocation, DestinationLocation
            log.info("targetFlags 96 unsupported yet.");
        else if(targetFlags==2048)                                  // GameObject
            log.info("targetFlags 2048 unsupported yet.");
        else
            throw new ParseException("Target Flag "+targetFlags+" unsupported yet.");
    }

        @Override
    public boolean contains(Integer relatedEntry) {
        // the caster could have the specified entry...
        if(getCasterUnit() instanceof Creature){
            Creature caster=(Creature)getCasterUnit();
            if(caster.getEntry().equals(relatedEntry))
                return true;
        } else if(getCasterUnit() instanceof GameObject){
            GameObject caster=(GameObject)getCasterUnit();
            if(caster.getEntry().equals(relatedEntry))
                return true;
        } else if(getCasterUnit() instanceof Vehicule){
            Vehicule caster=(Vehicule)getCasterUnit();
            if(caster.getEntry().equals(relatedEntry))
                return true;
        }
        
        // or the target...
        if(getTargetUnit() instanceof Creature){
            Creature target=(Creature)getTargetUnit();
            if(target.getEntry().equals(relatedEntry))
                return true;
        } else if(getTargetUnit() instanceof GameObject){
            GameObject target=(GameObject)getTargetUnit();
            if(target.getEntry().equals(relatedEntry))
                return true;
        } else if(getTargetUnit() instanceof Vehicule){
            Vehicule target=(Vehicule)getTargetUnit();
            if(target.getEntry().equals(relatedEntry))
                return true;
        }
        
        // and that's it
        return false;
    }

    @Override
    public boolean contains(Long relatedGUID) {
        if(getCasterUnit().getGUID().equals(relatedGUID))
            return true;
        
        if(getTargetUnit().getGUID().equals(relatedGUID))
            return true;
        
        return false;
    }

    @Override
    public String toString() {
        return super.toString()+", SpellStartMessage{" + "targetFlags=" + targetFlags + ", targetUnit=" + targetUnit + '}';
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
}
