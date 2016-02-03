package com.trinitycore.sniffexplorer.message;

import com.trinitycore.sniffexplorer.exceptions.ParseException;
import com.trinitycore.sniffexplorer.game.entities.IdentifiableByEntry;
import com.trinitycore.sniffexplorer.game.entities.Unit;
import com.trinitycore.sniffexplorer.game.data.UnitType;

import java.util.regex.Pattern;

/**
 * Created by chaouki on 02-02-16.
 */
public class ParseUtils {

    /**
     * method to parse this kind of line:
     [0] Hit GUID: Full: 0xF1306D7D00516C94 Type: Creature Entry: 28029 Low: 5336212
     *
     * cf https://github.com/TrinityCore/WowPacketParser/blob/master/WowPacketParser/Parsing/Parsers/SpellHandler.cs#L730
     * @return
     */
    public static Unit parseReadGuid(){
        return null;
    }

    /**
     * method to parse this kind of line:
     * XXX Full: 0xF130CD160001CF6B Type: Creature Entry: 52502 Low: 118635
     * used to parse lines write by the method ReadPackedGuid() in WPP.
     *
     * @return
     */
    public static Unit parseGuidRemovePrefix(String line, String prefix) throws ParseException {
        String lineGUID = removePrefix(line, prefix);
        return parseGuid(lineGUID);
    }

    /**
     * removes the prefix from the line and everything on its left.
     *
     * @param line
     * @param prefix
     * @return
     */
    public static String removePrefix(String line, String prefix){
        return line.replaceFirst(".*" + Pattern.quote(prefix) + ":?", "").trim();
    }

    public static String removePrefixAndGetFirstElement(String line, String prefix){
        String[] split = removePrefix(line, prefix).split("\\s+");
        return split[0];
    }

    /**
     * method to parse this kinds of line:
     Full: 0xF1306D7D00516C94 Type: Creature Entry: 28029 Low: 5336212
     Full: 0xF1306D7D00516C94 Type: Player Low: 5336212
     Full: 0xF150815900029F17 Type: Vehicle Entry: 33113 Low: 171799
     Full: 0xF140A1B13C000054 Type: Pet Low: 1006633044
     Full: 0xF112ED35000000ED Type: GameObject Entry: 191797 Low: 237
     Full: 0x45000002D317CE47 Type: Item Low: 12131487303
     Full: 0x1FC0000000000073 Type: Transport Low: 115
     *
     * @return
     */
    public static Unit parseGuid(String line) throws ParseException {
        if(line.equals("0x0"))
            return null;

        String[] words=line.split("\\s+");
        String GUID=words[1];
        UnitType unitType = UnitType.getUnitType(words[3]);
        Unit unit = UnitType.getUnitInstance(unitType);

        // we set the GUID for every units.
        unit.setGUID(GUID);

        // we set the entry of units that have one.
        switch (unitType){
            case CREATURE:
            case GAME_OBJECT:
            case VEHICLE:
                ((IdentifiableByEntry)unit).setEntry(Integer.parseInt(words[5]));
        }

        return unit;
    }
}
