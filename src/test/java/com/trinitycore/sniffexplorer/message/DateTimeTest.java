package com.trinitycore.sniffexplorer.message;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

import static org.hamcrest.Matchers.is;

/**
 * Created by chaouki on 17-02-16.
 */
public class DateTimeTest {

    @Test
    public void dateTimeParse(){
        String stringToParse="06/16/2012 22:48:04.393";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss.SSS");
        LocalDateTime localDateTime = LocalDateTime.parse(stringToParse, formatter);
        Assert.assertThat(localDateTime.getLong(ChronoField.MILLI_OF_SECOND), is(393L));
    }
}
