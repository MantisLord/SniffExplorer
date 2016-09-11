package com.trinitycore.sniffexplorer.message.smsg;

import com.trinitycore.sniffexplorer.core.Parser;
import com.trinitycore.sniffexplorer.criteria.Criteria;
import com.trinitycore.sniffexplorer.criteria.CriteriaSet;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

/**
 * Created by chaouki on 09-07-16.
 */
public class UpdateObjectMessageTest {

    private static final String INPUT_SNIFF_FILE_NAME_1 = "/message/smsg/updateObjectMessage1.txt";
    private static final String INPUT_SNIFF_FILE_NAME_2 = "/launcher/sample2.txt";

    @Test
    public void parseCombatReach_sample1(){
        Parser parser = new Parser(getClass().getResource(INPUT_SNIFF_FILE_NAME_1));
        CriteriaSet criteriaSet=new CriteriaSet();
        criteriaSet.addCriteria(new Criteria());

        parser.parseFile( criteriaSet, message -> {
            Assert.assertThat(message.getClass(), is(UpdateObjectMessage.class));
            UpdateObjectMessage updateObjectMessage = (UpdateObjectMessage) message;

            UpdateObjectMessage.UpdateObject updateObject1 = updateObjectMessage.getUpdates().get(1);
            Assert.assertThat(updateObject1.getUnit().getGUID(), is("0xF140B5130B0025BC"));
            Assert.assertThat(updateObject1.getCombatReach(), is(0.75));
            Assert.assertThat(updateObject1.getBoundingRadius(), is(0.5));

            UpdateObjectMessage.UpdateObject updateObject2 = updateObjectMessage.getUpdates().get(2);
            Assert.assertThat(updateObject2.getUnit().getGUID(), is("0x60000000320D4F0"));
            Assert.assertThat(updateObject2.getCombatReach(), is(1.5));
            Assert.assertThat(updateObject2.getBoundingRadius(), is(0.383));

        });
    }

    @Test
    public void parseCombatReach_sample2(){
        Parser parser = new Parser(getClass().getResource(INPUT_SNIFF_FILE_NAME_2));
        CriteriaSet criteriaSet=new CriteriaSet();
        criteriaSet.addCriteria(new Criteria());

        parser.parseFile( criteriaSet, message -> {
            Assert.assertThat(message.getClass(), is(UpdateObjectMessage.class));
            UpdateObjectMessage updateObjectMessage = (UpdateObjectMessage) message;

            UpdateObjectMessage.UpdateObject updateObject1 = updateObjectMessage.getUpdates().get(1);
            Assert.assertThat(updateObject1.getUnit().getGUID(), is("0xF140B5130B0025BC"));
            Assert.assertThat(updateObject1.getCombatReach(), is(0.75));
            Assert.assertThat(updateObject1.getBoundingRadius(), is(0.5));

            UpdateObjectMessage.UpdateObject updateObject2 = updateObjectMessage.getUpdates().get(2);
            Assert.assertThat(updateObject2.getUnit().getGUID(), is("0x60000000320D4F0"));
            Assert.assertThat(updateObject2.getCombatReach(), is(1.5));
            Assert.assertThat(updateObject2.getBoundingRadius(), is(0.383));

        });
    }

    @Test
    public void splitTest(){
        String COMBAT_REACH_LINE = "[10] UNIT_FIELD_BOUNDINGRADIUS: 1050589266/0.31";
        Assert.assertThat(COMBAT_REACH_LINE.split("/")[1], is("0.31"));
    }
}
