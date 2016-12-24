package com.trinitycore.sniffexplorer.criteria.smsg;

import com.trinitycore.sniffexplorer.criteria.BaseCriteria;
import com.trinitycore.sniffexplorer.criteria.Criteria;
import com.trinitycore.sniffexplorer.message.Message;
import com.trinitycore.sniffexplorer.message.smsg.AttackerStateUpdateMessage;
import com.trinitycore.sniffexplorer.message.smsg.PlayerMoveMessage;

/**
 * Created by chaouki on 10-04-16.
 */
public class PlayerMovementCriteria extends BaseCriteria {

    private String unitGUID;

    @Override
    public boolean isSatisfiedBy(Message message) {
        if (!super.isSatisfiedBy(message))
            return false;

        if (!(message instanceof PlayerMoveMessage))
            return false;

        PlayerMoveMessage playerMoveMessage = (PlayerMoveMessage) message;

        if(unitGUID!=null) {
            if (playerMoveMessage.getUnit() == null)
                return false;
            if (!unitGUID.equals(playerMoveMessage.getUnit().getGUID()))
                return false;
        }

        return true;
    }

    public PlayerMovementCriteria() {
    }

    public PlayerMovementCriteria(String unitGUID) {
        this.unitGUID = unitGUID;
    }
}
