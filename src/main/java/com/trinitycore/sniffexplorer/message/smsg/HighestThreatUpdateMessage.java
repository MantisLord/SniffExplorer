package com.trinitycore.sniffexplorer.message.smsg;

import com.trinitycore.sniffexplorer.exceptions.ParseException;
import com.trinitycore.sniffexplorer.game.entities.Creature;
import com.trinitycore.sniffexplorer.game.entities.Unit;
import com.trinitycore.sniffexplorer.message.Message;
import com.trinitycore.sniffexplorer.message.OpCode;
import com.trinitycore.sniffexplorer.message.OpCodeType;
import com.trinitycore.sniffexplorer.message.ParseUtils;

import java.io.PrintWriter;
import java.util.List;

/**
 * Created by chaouki on 20-08-16.
 */
public class HighestThreatUpdateMessage extends Message {

    private Unit unit;
    private Unit newHighest;

/*
ServerToClient: SMSG_HIGHEST_THREAT_UPDATE (0x0482) Length: 50 ConnIdx: 0 Time: 07/15/2010 21:03:56.000 Number: 149258
GUID: Full: 0xF13000681114AB72 Type: Creature Entry: 26641 Low: 1354610
New Highest: Full: AAAA Type: Player Low: 2607342 Name: AAAA
Size: 3
[0] Hostile: Full: 0xBBBBBBBBBBBB Type: Player Low: 52622036 Name: BBBB
[0] Threat: 1637198
[1] Hostile: Full: 0xCCCCCCCCCCCC Type: Player Low: 64264793 Name: CCCC
[1] Threat: 963512
[2] Hostile: Full: 0xAAAAAAAAAAAA Type: Player Low: 2607342 Name: AAAA
[2] Threat: 4305318
 */

    @Override
    public void initialize(List<String> lines) throws ParseException {
        unit=ParseUtils.parseGuidRemovePrefix(lines.get(1), "GUID");
        newHighest=ParseUtils.parseGuidRemovePrefix(lines.get(2), "New Highest");
    }

    @Override
    public OpCode getOpCode() {
        return OpCode.SMSG_HIGHEST_THREAT_UPDATE;
    }

    @Override
    public OpCodeType getOpCodeType() {
        return OpCodeType.SMSG;
    }

    @Override
    public boolean contains(Integer relatedEntry) {
        if(unit instanceof Creature){
            Creature creature = (Creature) unit;
            if(creature.getEntry().equals(relatedEntry))
                return true;
        }

        if(newHighest instanceof Creature){
            Creature creature = (Creature) newHighest;
            if(creature.getEntry().equals(relatedEntry))
                return true;
        }

        return false;
    }

    @Override
    public boolean contains(String relatedGUID) {
        if(unit.getGUID().equals(relatedGUID))
            return true;
        if(newHighest.getGUID().equals(relatedGUID))
            return true;

        return false;
    }

    public Unit getUnit() {
        return unit;
    }

    public Unit getNewHighest() {
        return newHighest;
    }

//    @Override
//    public String toString() {
//        final StringBuilder sb = new StringBuilder("HighestThreatUpdateMessage{");
//        sb.append("time=").append(this.getTime());
//        sb.append(", newHighest=").append(newHighest);
//        sb.append(", unit=").append(unit);
//        sb.append('}');
//        return sb.toString();
//    }

    @Override
    public void display(PrintWriter writer){
        super.display(writer);
        writer.println();
        writer.println(unit);
        writer.println(newHighest);
    }
}
