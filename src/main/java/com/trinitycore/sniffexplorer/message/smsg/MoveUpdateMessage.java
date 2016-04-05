/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinitycore.sniffexplorer.message.smsg;

import java.io.PrintWriter;
import java.util.List;

import com.trinitycore.sniffexplorer.exceptions.ParseException;
import com.trinitycore.sniffexplorer.game.data.Position;
import com.trinitycore.sniffexplorer.game.entities.IdentifiableByEntry;
import com.trinitycore.sniffexplorer.game.entities.Unit;
import com.trinitycore.sniffexplorer.message.Message;
import com.trinitycore.sniffexplorer.message.OpCode;
import com.trinitycore.sniffexplorer.message.OpCodeType;
import com.trinitycore.sniffexplorer.message.ParseUtils;

/** SMSG_MOVE_UPDATE
 *
 * @author chaouki
 */
public class MoveUpdateMessage extends Message{
    
    private Unit movingUnit;
    private Position currentPos;

/*
ServerToClient: SMSG_MOVE_UPDATE (0x79A2) Length: 29 ConnIdx: 2 Time: 06/16/2012 23:09:24.990 Number: 69944
Has spline data: False
Extra Movement Flags: Unknown10 (2048)
Timestamp: 4153343659
Guid: Full: 0x60000000320D4F0 Type: Player Low: 52483312 Name: Gansinolo
Position: X: 3694.42 Y: -5126.839 Z: 142.0237 O: 3.553918
 */

    @Override
    public void initialize(List<String> lines) throws ParseException {
        int guidIndex = ParseUtils.getLineIndexThatStartWithPrefix(lines, "Guid", 1);
        int positionIndex = ParseUtils.getLineIndexThatStartWithPrefix(lines, "Position", 2);
        movingUnit=ParseUtils.parseGuidRemovePrefix(lines.get(guidIndex), "Guid");
        currentPos=ParseUtils.parsePositionRemovePrefix(lines.get(positionIndex), "Position");
    }

    @Override
    public boolean contains(Integer relatedEntry) {
        if(!(movingUnit instanceof IdentifiableByEntry))
            return false;

        IdentifiableByEntry identifiableByEntry=(IdentifiableByEntry) movingUnit;
        if(identifiableByEntry.getEntry().equals(relatedEntry))
            return true;
        else
            return false;
    }

    @Override
    public boolean contains(String relatedGUID) {
        if(movingUnit.getGUID().equals(relatedGUID))
            return true;
        else
            return false;
    }

    @Override
    public void display(PrintWriter writer) {
        super.display(writer);
        writer.println();
        writer.println(movingUnit);
        writer.println(currentPos.toFormatedString());
    }

    @Override
    public OpCode getOpCode() {
        return OpCode.SMSG_MOVE_UPDATE;
    }

    @Override
    public OpCodeType getOpCodeType() {
        return OpCodeType.SMSG;
    }

    public Unit getMovingUnit() {
        return movingUnit;
    }

    public Position getCurrentPos() {
        return currentPos;
    }
}
