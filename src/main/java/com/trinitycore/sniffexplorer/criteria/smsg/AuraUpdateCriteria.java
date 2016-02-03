package com.trinitycore.sniffexplorer.criteria.smsg;

import com.trinitycore.sniffexplorer.criteria.Criteria;
import com.trinitycore.sniffexplorer.game.entities.IdentifiableByEntry;
import com.trinitycore.sniffexplorer.message.Message;
import com.trinitycore.sniffexplorer.message.smsg.AuraUpdateMessage;

/**
 * Created by chaouki on 01-02-16.
 */
public class AuraUpdateCriteria extends Criteria {

    private Integer ownerEntry;
    private String ownerGUID;

    private Integer spellId;

    @Override
    public boolean isSatisfiedBy(Message message){
        if(!super.isSatisfiedBy(message))
            return false;

        if(!(message instanceof AuraUpdateMessage))
            return false;

        AuraUpdateMessage auraUpdateMessage=(AuraUpdateMessage) message;

        if(ownerEntry !=null){
            if(!(auraUpdateMessage.getOwner() instanceof IdentifiableByEntry))
                return false;
            IdentifiableByEntry entryInstance = (IdentifiableByEntry) auraUpdateMessage.getOwner();
            if(!entryInstance.getEntry().equals(ownerEntry))
                return false;
        }

        if(ownerGUID !=null && !auraUpdateMessage.getOwner().getGUID().equals(ownerGUID))
            return false;

        if(spellId!=null && !auraUpdateMessage.getSpellId().equals(spellId))
            return false;

        return true;
    }

    public AuraUpdateCriteria() {
    }

    public AuraUpdateCriteria(Integer spellId) {
        this.spellId = spellId;
    }

    public Integer getOwnerEntry() {
        return ownerEntry;
    }

    public void setOwnerEntry(Integer ownerEntry) {
        this.ownerEntry = ownerEntry;
    }


    public void setOwnerGUID(String ownerGUID) {
        this.ownerGUID = ownerGUID;
    }

    public Integer getSpellId() {
        return spellId;
    }

    public void setSpellId(Integer spellId) {
        this.spellId = spellId;
    }
}
