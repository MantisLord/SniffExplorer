package com.trinitycore.sniffexplorer.criteria.smsg;

import com.trinitycore.sniffexplorer.criteria.Criteria;
import com.trinitycore.sniffexplorer.message.Message;
import com.trinitycore.sniffexplorer.message.smsg.UpdateObjectMessage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by chaouki on 06-04-16.
 */
public class UpdateObjectCriteria extends Criteria {

    private String unitGUID;
    private String valueChange;

    @Override
    public boolean isSatisfiedBy(Message message) {
        if (!super.isSatisfiedBy(message))
            return false;

        if (!(message instanceof UpdateObjectMessage))
            return false;

        UpdateObjectMessage updateObjectMessage = (UpdateObjectMessage) message;

        if(unitGUID!=null && valueChange!=null){
            List<UpdateObjectMessage.UpdateObject> updates = updateObjectMessage.getUpdates();

            List<UpdateObjectMessage.UpdateObject> updateObjects = updates.stream()
                    .filter(uObjc -> uObjc.getUnit() != null)
                    .filter(uObjc -> uObjc.getUnit().getGUID().equals(unitGUID))
                    .filter(uObjc -> uObjc.getRawData().stream().anyMatch(s -> s.contains(valueChange)))
                    .collect(Collectors.toList());

            updateObjects.forEach(updateObject -> updateObject.setDisplay(true));

            if (updateObjects.isEmpty())
                return false;
        }
        else if(unitGUID!=null){
            List<UpdateObjectMessage.UpdateObject> updates = updateObjectMessage.getUpdates();

            List<UpdateObjectMessage.UpdateObject> updateObjects = updates.stream()
                    .filter(uObjc -> uObjc.getUnit() != null)
                    .filter(uObjc -> uObjc.getUnit().getGUID().equals(unitGUID))
                    .collect(Collectors.toList());

            updateObjects.forEach(updateObject -> updateObject.setDisplay(true));

            if (updateObjects.isEmpty())
                return false;
        }

        return true;
    }

    public UpdateObjectCriteria(String unitGUID, String valueChange) {
        this.unitGUID = unitGUID;
        this.valueChange = valueChange;
    }

    public void restrictByUnit(String unitGUID){
        this.unitGUID=unitGUID;
        this.valueChange=null;
    }

    public void restrictByUnitAndChange(String unitGUID, String valueChange){
        this.unitGUID=unitGUID;
        this.valueChange=valueChange;
    }
}
