package com.trinitycore.sniffexplorer.message.smsg;

import com.trinitycore.sniffexplorer.exceptions.ParseException;
import com.trinitycore.sniffexplorer.game.data.Position;
import com.trinitycore.sniffexplorer.game.entities.Player;
import com.trinitycore.sniffexplorer.game.entities.Unit;
import com.trinitycore.sniffexplorer.message.Message;
import com.trinitycore.sniffexplorer.message.OpCode;
import com.trinitycore.sniffexplorer.message.OpCodeType;
import com.trinitycore.sniffexplorer.message.ParseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.List;

/**
 * Created by chaouki on 10-04-16.
 */
public class PlayerMoveMessage extends Message {


    protected static final Logger log = LoggerFactory.getLogger(PlayerMoveMessage.class);

    private String opCodeFull;
    private Unit unit;
    private Position position;

/*
ServerToClient: MSG_MOVE_TELEPORT (0x00C5) Length: 52 ConnIdx: 0 Time: 07/15/2010 19:13:01.000 Number: 2454
GUID: Full: 0x280000000EA49B0 Type: Player Low: 15354288 Name: Crockysdrood
Movement Flags: Falling (4096)
Extra Movement Flags: None (0)
Time: -679073033
Position: X: 411.37 Y: 794.947 Z: 831.323
Orientation: 5.497787
Fall Time: 0
Fall Velocity: 0
Fall Sin Angle: 0.707107
Fall Cos Angle: -0.7071065
Fall Speed: 0
 */

    /*
    ServerToClient: MSG_MOVE_SET_FACING (0x00DA) Length: 40 ConnIdx: 0 Time: 09/14/2010 09:08:34.000 Number: 1937
    GUID: Full: 0x600000002A8980F Type: Player Low: 44603407
    Movement Flags: Forward, CanFly, Flying (50331649)
    Extra Movement Flags: None (0)
    Time: 260123968
    Position: X: 7301.787 Y: 1436.087 Z: 652.8785
    Orientation: 2.694264
    Swim Pitch: -0.4195756
    Fall Time: 794
     */

    @Override
    public void initialize(List<String> lines) throws ParseException {
        opCodeFull=lines.get(0).split("\\s+")[1];

//        if(opCodeFull.equals("MSG_MOVE_TELEPORT_ACK"))
//            log.debug("derp");

        // hack because depending on the version, the GUID line is prefixed by the "GUID" or "Guid"...
        String guidLine = ParseUtils.getLineThatStartWithPrefix(lines, "GUID");
        if(guidLine == null)
            guidLine = ParseUtils.getLineThatStartWithPrefix(lines, "Guid");
        try {
            unit = ParseUtils.parseGuidRemovePrefix(guidLine, "GUID");
        } catch (Exception e){
            try{
                unit = ParseUtils.parseGuidRemovePrefix(guidLine, "Guid");
            } catch (Exception e1){}
        }

        int indexPosition = ParseUtils.getLineIndexThatStartWithPrefix(lines, "Position");
        position=ParseUtils.parsePositionRemovePrefix(lines.get(indexPosition), "Position");
    }

    @Override
    public OpCode getOpCode() {
        return OpCode.MSG_MOVE_;
    }

    @Override
    public OpCodeType getOpCodeType() {
        return null;
    }

    @Override
    public boolean contains(Integer relatedEntry) {
        return false;
    }

    @Override
    public boolean contains(String relatedGUID) {
        return false;
    }

    public String getOpCodeFull() {
        return opCodeFull;
    }

    public Unit getUnit() {
        return unit;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public void display(PrintWriter writer){
        super.display(writer);
        writer.println();
        writer.println(opCodeFull);
        writer.println(this.getUnit());
        if(this.getPosition() != null)
            writer.println(this.getPosition().toFormatedStringWoOrientation());
    }
}
