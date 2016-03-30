package com.trinitycore.sniffexplorer.criteria.smsg;

import com.trinitycore.sniffexplorer.criteria.Criteria;
import com.trinitycore.sniffexplorer.message.Message;
import com.trinitycore.sniffexplorer.message.smsg.OnMonsterMoveMessage;
import com.trinitycore.sniffexplorer.message.smsg.SpellMessage;

/**
 * Created by chaouki on 15-03-16.
 */
public class OnMonsterMoveCriteria extends Criteria {

    private String unitGUID;

    @Override
    public boolean isSatisfiedBy(Message message) {
        if (!super.isSatisfiedBy(message))
            return false;

        if (!(message instanceof OnMonsterMoveMessage))
            return false;

        OnMonsterMoveMessage onMonsterMoveMessage = (OnMonsterMoveMessage) message;

        if(!unitGUID.equals(onMonsterMoveMessage.getUnitGUID()))
            return false;

        return true;
    }

    public OnMonsterMoveCriteria(String unitGUID) {
        this.unitGUID = unitGUID;
    }

}
