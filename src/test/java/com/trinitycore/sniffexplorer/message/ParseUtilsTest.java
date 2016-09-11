package com.trinitycore.sniffexplorer.message
        ;

import com.trinitycore.sniffexplorer.exceptions.ParseException;
import com.trinitycore.sniffexplorer.game.entities.Creature;
import com.trinitycore.sniffexplorer.game.entities.Player;
import com.trinitycore.sniffexplorer.game.entities.Unit;
import com.trinitycore.sniffexplorer.message.ParseUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;


/**
 * Created by chaouki on 02-02-16.
 */
public class ParseUtilsTest {

    @Test
    public void parseGuidCreature() throws ParseException {
        String stringToParse="Full: 0xF1306D7D00516C94 Type: Creature Entry: 28029 Low: 5336212";
        Unit unit = ParseUtils.parseGuid(stringToParse);
        Assert.assertThat(unit.getGUID(), is("0xF1306D7D00516C94"));
        Assert.assertThat(unit, is(instanceOf(Creature.class)));
        Creature creature=(Creature) unit;
        Assert.assertThat(creature.getEntry(), is(28029));
    }

    @Test
    public void parseGuidPlayer() throws ParseException {
        String stringToParse="Full: 0xF1306D7D00516C94 Type: Player Low: 5336212";
        Unit unit = ParseUtils.parseGuid(stringToParse);
        Assert.assertThat(unit.getGUID(), is("0xF1306D7D00516C94"));
        Assert.assertThat(unit, is(instanceOf(Player.class)));
    }

    @Test
    public void parseReadPackedGuidCasterGUID() throws ParseException {
        String stringToParse="Caster GUID: Full: 0xF1306D7D00516C94 Type: Player Low: 5336212";
        String prefix="Caster GUID";
        Unit unit = ParseUtils.parseGuidRemovePrefix(stringToParse, prefix);
        Assert.assertThat(unit.getGUID(), is("0xF1306D7D00516C94"));
        Assert.assertThat(unit, is(instanceOf(Player.class)));
    }

    @Test
    public void removePrefixTestA(){
        String stringToParse="Caster GUID: Full: 0xF1306D7D00516C94 Type: Player Low: 5336212";
        String prefix="Caster GUID";
        String result = ParseUtils.removePrefix(stringToParse, prefix);
        Assert.assertThat(result, is("Full: 0xF1306D7D00516C94 Type: Player Low: 5336212"));
    }

    @Test
    public void removePrefixTestAa(){
        String stringToParse="Caster GUID: Full: 0xF1306D7D00516C94 Type: Player Low: 5336212";
        String prefix="Caster GUID:";

        String result = ParseUtils.removePrefix(stringToParse, prefix);

        Assert.assertThat(result, is("Full: 0xF1306D7D00516C94 Type: Player Low: 5336212"));
    }

    @Test
    public void removePrefixTestB(){
        String stringToParse="[0] Hit GUID: Full: 0xF1306D7D00516C94 Type: Player Low: 5336212";
        String prefix="[0] Hit GUID";

        String result = ParseUtils.removePrefix(stringToParse, prefix);

        Assert.assertThat(result, is("Full: 0xF1306D7D00516C94 Type: Player Low: 5336212"));
    }

    @Test
    public void removePrefixTestBa(){
        String stringToParse="[0] Hit GUID: Full: 0xF1306D7D00516C94 Type: Player Low: 5336212";
        String prefix="[0] Hit GUID:";

        String result = ParseUtils.removePrefix(stringToParse, prefix);

        Assert.assertThat(result, is("Full: 0xF1306D7D00516C94 Type: Player Low: 5336212"));
    }

    @Test
    public void removePrefixTestBc(){
        String stringToParse="[0] Hit GUID: Full: 0xF1306D7D00516C94 Type: Player Low: 5336212";
        String prefix="Hit GUID:";

        String result = ParseUtils.removePrefix(stringToParse, prefix);

        Assert.assertThat(result, is("Full: 0xF1306D7D00516C94 Type: Player Low: 5336212"));
    }

    @Test
    public void parseGuidHandleNull() throws ParseException {
        String GUID="0x0";

        Unit unit = ParseUtils.parseGuid(GUID);

        Assert.assertThat(unit, is(nullValue()));
    }
}
