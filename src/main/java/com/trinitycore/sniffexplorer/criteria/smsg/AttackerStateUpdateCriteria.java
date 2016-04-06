package com.trinitycore.sniffexplorer.criteria.smsg;

import com.trinitycore.sniffexplorer.criteria.Criteria;
import com.trinitycore.sniffexplorer.game.entities.Player;
import com.trinitycore.sniffexplorer.game.entities.Unit;
import com.trinitycore.sniffexplorer.message.Message;
import com.trinitycore.sniffexplorer.message.smsg.AttackerStateUpdateMessage;

/**
 * Created by chaouki on 06-04-16.
 */
public class AttackerStateUpdateCriteria extends Criteria {

    private String attackerGUID;
    private String targetGUID;

    @Override
    public boolean isSatisfiedBy(Message message) {
        if (!super.isSatisfiedBy(message))
            return false;

        if (!(message instanceof AttackerStateUpdateMessage))
            return false;

        AttackerStateUpdateMessage attackerStateUpdateMessage = (AttackerStateUpdateMessage) message;

        if(attackerGUID!=null && !attackerGUID.equals(attackerStateUpdateMessage.getAttacker().getGUID()))
            return false;

        if(targetGUID!=null && !targetGUID.equals(attackerStateUpdateMessage.getTarget().getGUID()))
            return false;

        return true;
    }

    public AttackerStateUpdateCriteria() {
    }

    public AttackerStateUpdateCriteria(String attackerGUID, String targetGUID) {
        this.attackerGUID = attackerGUID;
        this.targetGUID = targetGUID;
    }

    public void setAttackerGUID(String attackerGUID) {
        this.attackerGUID = attackerGUID;
    }

    public void setTargetGUID(String targetGUID) {
        this.targetGUID = targetGUID;
    }

}
