/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinitycore.sniffexplorer.criteria.smsg;

import com.trinitycore.sniffexplorer.game.data.MissType;
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

    private Boolean hasNonEmptyHitList;
    private Boolean hasNonEmptyMissList;

    private Boolean hasNonEmptyImmuneMissList;

    // String casterDest;

    @Override
    public boolean isSatisfiedBy(Message message){
        if(!super.isSatisfiedBy(message))
            return false;

        if(!(message instanceof SpellGoMessage))
            return false;

        SpellGoMessage spellGoMessage=(SpellGoMessage) message;

        // todo: complete this

//        if(hasNonEmptyHitList!=null && !spellGoMessage.getHitUnits().isEmpty())
//            return false;

        if(hasNonEmptyMissList!=null){
            if(hasNonEmptyMissList){
                if(!spellGoMessage.getMissedUnits().isEmpty())
                    return true;
                else
                    return false;
            } else{
                if(spellGoMessage.getMissedUnits().isEmpty())
                    return true;
                else
                    return false;
            }
        }

        if(hasNonEmptyImmuneMissList!=null){
            if(hasNonEmptyImmuneMissList){
                if(spellGoMessage.getMissedUnits().containsValue(MissType.IMMUNE1))
                    return true;
                else
                    return false;
            } else{
                if(!spellGoMessage.getMissedUnits().containsValue(MissType.IMMUNE1))
                    return true;
                else
                    return false;
            }
        }

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

    public void setHasNonEmptyHitList(Boolean hasNonEmptyHitList) {
        this.hasNonEmptyHitList = hasNonEmptyHitList;
    }

    public void setHasNonEmptyMissList(Boolean hasNonEmptyMissList) {
        this.hasNonEmptyMissList = hasNonEmptyMissList;
    }

    // String targetDest;
    public void setHasNonEmptyImmuneMissList(Boolean hasNonEmptyImmuneMissList) {
        this.hasNonEmptyImmuneMissList = hasNonEmptyImmuneMissList;
    }
}
