package com.trinitycore.sniffexplorer.message.smsg;

import com.trinitycore.sniffexplorer.exceptions.ParseException;
import com.trinitycore.sniffexplorer.game.entities.Unit;
import com.trinitycore.sniffexplorer.message.Message;
import com.trinitycore.sniffexplorer.message.ParseUtils;

import java.util.List;

/**
 * Created by chaouki on 25-03-16.
 */
public class ForceSpeedChangeMessage extends Message {

    private Unit unit;
    private double newSpeed;

    @Override
    public void initialize(List<String> lines) throws ParseException {
        unit = ParseUtils.parseGuidRemovePrefix(lines.get(1), "Guid");
        int newSpeedIdx = ParseUtils.getLineIndexThatStartWithPrefix(lines, "New Speed");
        newSpeed = Double.parseDouble(ParseUtils.removePrefixAndGetFirstElement(lines.get(newSpeedIdx), "New Speed"));
    }

    @Override
    public boolean contains(Integer relatedEntry) {
        return false;
    }

    @Override
    public boolean contains(String relatedGUID) {
        return false;
    }

    public Unit getUnit() {
        return unit;
    }

    public double getNewSpeed() {
        return newSpeed;
    }
}

/*
ServerToClient: SMSG_FORCE_RUN_SPEED_CHANGE (0x00E2) Length: 16 ConnIdx: 0 Time: 07/15/2010 19:22:09.000 Number: 33653
Guid: Full: 0x00000000000 Type: Player Low: 00000 Name: ***
MoveEvent: 7
Unk Byte: 1
New Speed: 3.5
 */

/*
ServerToClient: SMSG_FORCE_RUN_BACK_SPEED_CHANGE (0x00E4) Length: 15 ConnIdx: 0 Time: 07/15/2010 19:22:09.000 Number: 33654
Guid: Full: 0x00000000000 Type: Player Low: 00000 Name: ***
MoveEvent: 8
New Speed: 2.25
 */