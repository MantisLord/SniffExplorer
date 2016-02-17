package com.trinitycore.sniffexplorer.message;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
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

    @Test
    public void creationDateTimObject(){
        LocalDateTime localDateTime = LocalDateTime.of(2012, Month.JUNE, 16, 22, 48, 4, 393_000_000);

        Assert.assertThat(localDateTime.getYear(), is(2012));
        Assert.assertThat(localDateTime.getMonth(), is(Month.JUNE));
        Assert.assertThat(localDateTime.getDayOfMonth(), is(16));
        Assert.assertThat(localDateTime.getHour(), is(22));
        Assert.assertThat(localDateTime.getMinute(), is(48));
        Assert.assertThat(localDateTime.getSecond(), is(4));
        Assert.assertThat(localDateTime.getLong(ChronoField.MILLI_OF_SECOND), is(393L));
    }
}
