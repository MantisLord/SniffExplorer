/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinitycore.sniffexplorer.criteria.smsg;

import com.trinitycore.sniffexplorer.message.Message;
import com.trinitycore.sniffexplorer.message.smsg.SpellGoMessage;

/**
 * The search parameters specific to SMSG_SPELL_START should be:
 * - target GUID
 * - target entry
 * 
 * canceled:
 * - caster destination
 * - target destination
 *
 * @author chaouki
 */
public class SpellGoCriteria extends SpellCriteria {
    
    private Integer targetEntry;
    private String targetGUID;
    
    // String casterDest;
    // String targetDest;
    
    @Override
    public boolean isSatisfiedBy(Message message){
        if(!super.isSatisfiedBy(message))
            return false;
        
        if(!(message instanceof SpellGoMessage))
            return false;
        
        SpellGoMessage spellGoMessage=(SpellGoMessage) message;
        
        // todo: complete this
        
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
