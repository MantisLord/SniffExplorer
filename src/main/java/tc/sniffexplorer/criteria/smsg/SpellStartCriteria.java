/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.criteria.smsg;

import tc.sniffexplorer.criteria.Criteria;
import tc.sniffexplorer.model.Message;
import tc.sniffexplorer.model.OpCode;
import tc.sniffexplorer.model.gameentities.IdentifiableByEntry;
import tc.sniffexplorer.model.smsg.SpellStartMessage;

/**
 *
 * @author chaouki
 */
public class SpellStartCriteria extends SpellCriteria {
    
    private Integer targetEntry;
    private String targetGUID;
    
    // String casterDest;
    // String targetDest;
    
    public SpellStartCriteria() {
        setOpcode(OpCode.SMSG_SPELL_START);
    }
    
    @Override
    public boolean isSatisfiedBy(Message message){
        if(!super.isSatisfiedBy(message))
            return false;
        
        if(!(message instanceof SpellStartMessage))
            throw new IllegalArgumentException();
        SpellStartMessage spellStartMessage=(SpellStartMessage) message;
        
        if(targetEntry!=null){
            if(!(spellStartMessage.getTargetUnit()instanceof IdentifiableByEntry))
                return false;
            IdentifiableByEntry entryInstance = (IdentifiableByEntry) spellStartMessage.getTargetUnit();
            if(!entryInstance.getEntry().equals(targetEntry))
                return false;
        }
        
        if(targetGUID!=null && !spellStartMessage.getTargetUnit().getGUID().equals(targetGUID))
            return false;
        
        return true;
    }

    
    
    /*
    GETTERS SETTERS
    */

    public Integer getTargetEntry() {
        return targetEntry;
    }

    public void setTargetEntry(Integer targetEntry) {
        this.targetEntry = targetEntry;
    }

    public String getTargetGUID() {
        return targetGUID;
    }

    public void setTargetGUID(String targetGUID) {
        this.targetGUID = targetGUID;
    }
    
    
}
