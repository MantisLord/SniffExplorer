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
import com.trinitycore.sniffexplorer.message.ParseUtils;

/** SMSG_MOVE_UPDATE
 *
 * @author chaouki
 */
public class MoveUpdateMessage extends Message{
    
    private Unit movingUnit;
    private Position currentPos;
    private String movementFlagsLine;

/*
ServerToClient: SMSG_MOVE_UPDATE (0x79A2) Length: 29 ConnIdx: 2 Time: 06/16/2012 23:09:24.990 Number: 69944
Has spline data: False
Extra Movement Flags: Unknown10 (2048)
Timestamp: 4153343659
Guid: Full: 0x60000000320D4F0 Type: Player Low: 52483312 Name: Gansinolo
Position: X: 3694.42 Y: -5126.839 Z: 142.0237 O: 3.553918
 */

/*
ServerToClient: SMSG_MOVE_UPDATE (0x79A2) Length: 33 ConnIdx: 2 Time: 06/16/2012 23:20:08.307 Number: 100363
Has spline data: False
Extra Movement Flags: Unknown10 (2048)
Movement flags: Forward (1)
Timestamp: 4153986851
Guid: Full: 0x60000000320D4F0 Type: Player Low: 52483312 Name: Gansinolo
Position: X: 3265.232 Y: -3477.408 Z: 287.0757 O: 6.171607
 */

    @Override
    public void initialize(List<String> lines) throws ParseException {
        super.initialize(lines);
        movementFlagsLine = ParseUtils.getLineThatStartWithPrefix(lines, "Movement flags");
        String guidLine = ParseUtils.getLineThatStartWithPrefix(lines, "Guid");
        movingUnit=ParseUtils.parseGuidRemovePrefix(guidLine, "Guid");
        String positionLine = ParseUtils.getLineThatStartWithPrefix(lines, "Position");
        currentPos=ParseUtils.parsePositionRemovePrefix(positionLine, "Position");
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
        if(movementFlagsLine!=null)
            writer.println(movementFlagsLine);
        writer.println(currentPos.toFormatedString());
    }

    public Unit getMovingUnit() {
        return movingUnit;
    }

    public Position getCurrentPos() {
        return currentPos;
    }

    public String getMovementFlagsLine() {
        return movementFlagsLine;
    }
}
