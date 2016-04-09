package com.trinitycore.sniffexplorer.criteria.smsg;

import com.trinitycore.sniffexplorer.criteria.Criteria;
import com.trinitycore.sniffexplorer.message.Message;
import com.trinitycore.sniffexplorer.message.smsg.AttackStartMessage;
import com.trinitycore.sniffexplorer.message.smsg.AttackStopMessage;
import com.trinitycore.sniffexplorer.message.smsg.AttackerStateUpdateMessage;

/**
 * Created by chaouki on 06-04-16.
 */
public class AttackStartStopCriteria extends Criteria {

    private String attackerGUID;
    private String targetGUID;

    @Override
    public boolean isSatisfiedBy(Message message) {
        if (!super.isSatisfiedBy(message))
            return false;

        if (message instanceof AttackStartMessage){
            AttackStartMessage attackStartMessage = (AttackStartMessage) message;

            if(attackerGUID!=null && !attackerGUID.equals(attackStartMessage.getAttacker().getGUID()))
                return false;

            if(targetGUID!=null && !targetGUID.equals(attackStartMessage.getTarget().getGUID()))
                return false;

            return true;

        } else if (message instanceof AttackStopMessage){
            AttackStopMessage attackStopMessage = (AttackStopMessage) message;
            if(attackerGUID!=null && !attackerGUID.equals(attackStopMessage.getAttacker().getGUID()))
                return false;

            if(targetGUID!=null && !targetGUID.equals(attackStopMessage.getTarget().getGUID()))
                return false;

            return true;

        } else
            return false;
    }

    public AttackStartStopCriteria(String attackerGUID, String targetGUID) {
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
