package com.trinitycore.sniffexplorer.criteria.smsg;

import com.trinitycore.sniffexplorer.criteria.Criteria;
import com.trinitycore.sniffexplorer.game.entities.Player;
import com.trinitycore.sniffexplorer.message.Message;
import com.trinitycore.sniffexplorer.message.smsg.MoveUpdateMessage;
import com.trinitycore.sniffexplorer.message.smsg.OnMonsterMoveMessage;

/**
 * Created by chaouki on 05-04-16.
 */
public class MoveUpdateCriteria extends Criteria {

    private String unitGUID;

    @Override
    public boolean isSatisfiedBy(Message message) {
        if (!super.isSatisfiedBy(message))
            return false;

        if (!(message instanceof MoveUpdateMessage))
            return false;

        MoveUpdateMessage moveUpdateMessage = (MoveUpdateMessage) message;

        if(unitGUID!=null && !unitGUID.equals(moveUpdateMessage.getMovingUnit().getGUID()))
            return false;

        return true;
    }

    public MoveUpdateCriteria() {
    }

    public MoveUpdateCriteria(String unitGUID) {
        this.unitGUID = unitGUID;
    }
}
