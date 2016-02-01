/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinitycore.sniffexplorer.criteria.smsg;

import com.trinitycore.sniffexplorer.criteria.Criteria;
import com.trinitycore.sniffexplorer.gameentities.IdentifiableByEntry;
import com.trinitycore.sniffexplorer.message.Message;
import com.trinitycore.sniffexplorer.message.smsg.SpellMessage;

/**
 * Valid for SPELL_START or SPELL_GO messages
 *
 * @author chaouki
 */
public class SpellCriteria extends Criteria {
    
    private Integer casterEntry;
    private String casterGUID;
    
    private Integer spellId;

    @Override
    public boolean isSatisfiedBy(Message message){
        if(!super.isSatisfiedBy(message))
            return false;
        
        if(!(message instanceof SpellMessage))
            return false;
        
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

    public SpellCriteria() {
    }

    public SpellCriteria(Integer spellId) {
        this.spellId = spellId;
    }

    public SpellCriteria( Integer spellId, Integer casterEntry, String casterGUID) {
        this.casterEntry = casterEntry;
        this.casterGUID = casterGUID;
        this.spellId = spellId;
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
