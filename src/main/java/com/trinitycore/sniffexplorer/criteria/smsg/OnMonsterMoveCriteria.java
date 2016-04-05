package com.trinitycore.sniffexplorer.criteria.smsg;

import com.trinitycore.sniffexplorer.criteria.Criteria;
import com.trinitycore.sniffexplorer.game.data.SplineType;
import com.trinitycore.sniffexplorer.game.entities.Player;
import com.trinitycore.sniffexplorer.message.Message;
import com.trinitycore.sniffexplorer.message.smsg.OnMonsterMoveMessage;
import com.trinitycore.sniffexplorer.message.smsg.SpellMessage;

/**
 * Created by chaouki on 15-03-16.
 */
public class OnMonsterMoveCriteria extends Criteria {

    private String unitGUID;
    private SplineType splineType;
    private Boolean facingPlayer;

    @Override
    public boolean isSatisfiedBy(Message message) {
        if (!super.isSatisfiedBy(message))
            return false;

        if (!(message instanceof OnMonsterMoveMessage))
            return false;

        OnMonsterMoveMessage onMonsterMoveMessage = (OnMonsterMoveMessage) message;

        if(unitGUID!=null && !unitGUID.equals(onMonsterMoveMessage.getUnit().getGUID()))
            return false;

        if(splineType!=null && !splineType.equals(onMonsterMoveMessage.getSplineType()))
            return false;

        if(facingPlayer != null && facingPlayer && !(onMonsterMoveMessage.getFacingUnit() instanceof Player))
            return false;

        if(facingPlayer != null && !facingPlayer && onMonsterMoveMessage.getFacingUnit() instanceof Player)
            return false;

        return true;
    }

    public void setUnitGUID(String unitGUID) {
        this.unitGUID = unitGUID;
    }

    public void setSplineType(SplineType splineType) {
        this.splineType = splineType;
    }

    public void setFacingPlayer(Boolean facingPlayer) {
        this.facingPlayer = facingPlayer;
    }

    /*
        Constructors
         */
    public OnMonsterMoveCriteria(String unitGUID) {
        this.unitGUID = unitGUID;
    }

    public OnMonsterMoveCriteria(SplineType splineType) {
        this.splineType = splineType;
    }

    public OnMonsterMoveCriteria(String unitGUID, SplineType splineType) {
        this.unitGUID = unitGUID;
        this.splineType = splineType;
    }

    public OnMonsterMoveCriteria() {
    }
}
