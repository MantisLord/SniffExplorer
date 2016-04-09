/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinitycore.sniffexplorer.message.smsg;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.trinitycore.sniffexplorer.exceptions.ParseException;
import com.trinitycore.sniffexplorer.game.entities.Unit;
import com.trinitycore.sniffexplorer.message.Message;
import com.trinitycore.sniffexplorer.message.OpCode;
import com.trinitycore.sniffexplorer.message.OpCodeType;
import com.trinitycore.sniffexplorer.message.ParseUtils;

/**
 * Encapsulate those 2 types of packets:
 * SMSG_UPDATE_OBJECT
 * SMSG_COMPRESSED_UPDATE_OBJECT
 * @author chaouki
 */
public class UpdateObjectMessage extends Message{

    private List<UpdateObject> updates;
    private Long count;

/*
ServerToClient: SMSG_UPDATE_OBJECT (0x4715) Length: 219 ConnIdx: 2 Time: 06/16/2012 23:06:44.808 Number: 54754
Map: 533
Count: 2
[0] UpdateType: Values
[0] GUID: Full: 0x60000000456E3D8 Type: Player Low: 72803288 Name: Salantharasa
[0] UNIT_FIELD_POWER1: 107943/1.512604E-40
[1] UpdateType: Values
[1] GUID: Full: 0x4600000327F3078C Type: Item Low: 13555140492
[1] ITEM_FIELD_DURABILITY: 69/9.668959E-44
 */
    
    @Override
    public OpCode getOpCode() {
        return OpCode.SMSG_UPDATE_OBJECT;
    }

    @Override
    public OpCodeType getOpCodeType() {
        return OpCodeType.SMSG;
    }

    @Override
    public void initialize(List<String> lines) throws ParseException {
        updates=new ArrayList<>();
        count=Long.valueOf(ParseUtils.removePrefix(lines.get(2), "Count"));
        for(int i=0 ; i<count;i++){
            int startIndex=ParseUtils.getLineIndexThatStartWithPrefix(lines, "["+i+"]");
            int endIndex=(i+1<count)?ParseUtils.getLineIndexThatStartWithPrefix(lines, "["+(i+1)+"]"):lines.size();
            updates.add(new UpdateObject(lines.subList(startIndex, endIndex)));
        }
    }

    @Override
    public boolean contains(Integer relatedEntry) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contains(String relatedGUID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void display(PrintWriter writer) {
        super.display(writer);
        writer.println();
        for(UpdateObject updateObject:updates)
            if(updateObject.display)
                for(String line:updateObject.getRawData())
                    writer.println(line);
    }

    public class UpdateObject {
        private List<String> rawData;
        private Unit unit;
        private UpdateType updateType;
        private boolean display=false;

        public UpdateObject(List<String> rawData) throws ParseException {
            this.rawData = rawData;
            updateType=UpdateType.valueOf(ParseUtils.removePrefixAndGetFirstElement(rawData.get(0), "UpdateType"));
            if(!updateType.equals(UpdateType.DestroyObjects))
                unit=ParseUtils.parseGuidRemovePrefix(rawData.get(1), "GUID");
        }

        public List<String> getRawData() {
            return rawData;
        }

        public Unit getUnit() {
            return unit;
        }

        public UpdateType getUpdateType() {
            return updateType;
        }

        public void setDisplay(boolean display) {
            this.display = display;
        }
    }

    private enum UpdateType{
        CreateObject1,
        CreateObject2,
        DestroyObjects,
        Values;
    }

    public List<UpdateObject> getUpdates() {
        return updates;
    }

    public Long getCount() {
        return count;
    }
}
