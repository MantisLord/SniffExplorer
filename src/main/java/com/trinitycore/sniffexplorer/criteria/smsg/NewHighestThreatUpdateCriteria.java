package com.trinitycore.sniffexplorer.criteria.smsg;

import com.trinitycore.sniffexplorer.criteria.BaseCriteria;
import com.trinitycore.sniffexplorer.criteria.Criteria;
import com.trinitycore.sniffexplorer.message.Message;
import com.trinitycore.sniffexplorer.message.smsg.HighestThreatUpdateMessage;
import com.trinitycore.sniffexplorer.message.smsg.PlayerMoveMessage;

/**
 * Created by chaouki on 10-04-16.
 */
public class NewHighestThreatUpdateCriteria extends BaseCriteria {

    private String unitGUID;
    private String newHighestUnitGUID;

    @Override
    public boolean isSatisfiedBy(Message message) {
        if (!super.isSatisfiedBy(message))
            return false;

        if (!(message instanceof HighestThreatUpdateMessage))
            return false;

        HighestThreatUpdateMessage highestThreatUpdateMessage = (HighestThreatUpdateMessage) message;

        if(unitGUID!=null) {
            if (highestThreatUpdateMessage.getUnit() == null)
                return false;
            if (!unitGUID.equals(highestThreatUpdateMessage.getUnit().getGUID()))
                return false;
        }

        if(newHighestUnitGUID!=null) {
            if (highestThreatUpdateMessage.getNewHighest() == null)
                return false;
            if (!newHighestUnitGUID.equals(highestThreatUpdateMessage.getNewHighest().getGUID()))
                return false;
        }

        return true;
    }

    public NewHighestThreatUpdateCriteria() {
    }

    public NewHighestThreatUpdateCriteria(String unitGUID) {
        this.unitGUID = unitGUID;
    }
    public NewHighestThreatUpdateCriteria(String unitGUID, String newHighestUnitGUID) {
        this.unitGUID = unitGUID;
        this.newHighestUnitGUID = newHighestUnitGUID;
    }
}
