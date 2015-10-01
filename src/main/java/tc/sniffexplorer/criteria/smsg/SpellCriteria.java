/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.criteria.smsg;

import tc.sniffexplorer.criteria.Criteria;
import tc.sniffexplorer.model.Message;
import tc.sniffexplorer.model.gameentities.IdentifiableByEntry;
import tc.sniffexplorer.model.smsg.SpellMessage;
import tc.sniffexplorer.model.smsg.SpellStartMessage;

/**
 *
 * @author chaouki
 */
public abstract class SpellCriteria extends Criteria {
    
    private Integer casterEntry;
    private String casterGUID;
    
    private Integer spellId;

    @Override
    public boolean isSatisfiedBy(Message message){
        if(!super.isSatisfiedBy(message))
            return false;
        
        if(!(message instanceof SpellMessage))
            throw new IllegalArgumentException();
        SpellMessage spellMessage=(SpellMessage) message;
        
        if(casterEntry!=null){
            if(!(spellMessage.getCasterUnit() instanceof IdentifiableByEntry))
                return false;
            IdentifiableByEntry entryInstance = (IdentifiableByEntry) spellMessage.getCasterUnit();
            if(!entryInstance.getEntry().equals(casterEntry))
                return false;
        }
        
        if(casterGUID!=null && !spellMessage.getCasterUnit().getGUID().equals(casterGUID))
            return false;
        
        if(spellId!=null && !spellMessage.getSpellId().equals(spellId))
            return false;
        
        return true;
    }

    /*
    GETTERS AND SETTERS
    */
    public Integer getCasterEntry() {
        return casterEntry;
    }

    public void setCasterEntry(Integer casterEntry) {
        this.casterEntry = casterEntry;
    }

    public String getCasterGUID() {
        return casterGUID;
    }

    public void setCasterGUID(String casterGUID) {
        this.casterGUID = casterGUID;
    }

    public Integer getSpellId() {
        return spellId;
    }

    public void setSpellId(Integer spellId) {
        this.spellId = spellId;
    }
    
    
}
