/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinitycore.sniffexplorer.criteria;

import com.trinitycore.sniffexplorer.message.Message;
import com.trinitycore.sniffexplorer.message.OpCode;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author chaouki
 */
public class BaseCriteria extends Criteria {
    
    private OpCode opcode;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    
    //search for this GUID in every GUID mentioned by the message
    private String relatedGUID; // (do not forget to use Long.parseUnsignedLong() and Long.toUnsignedString() for this Long.
    private Integer relatedEntry; //  search for this entry in every Entry mentioned by the message

    @Override
    public boolean isSatisfiedBy(Message message){ // AND is applied between each condition inside a Criteria object. Meaning if one condition fails, the whole thing fails.
        if(message==null)
            return false;
        
        if(relatedGUID!= null && !message.contains(relatedGUID))
            return false;
        
        if(relatedGUID!= null && !message.contains(relatedEntry))
            return false;

        if(minTime != null && message.getTime().isBefore(minTime))
            return false;

        if(maxTime != null && message.getTime().isAfter(maxTime))
            return false;
         
        return true;
    }

    public OpCode getOpcode() {
        return opcode;
    }

    public void setOpcode(OpCode opcode) {
        this.opcode = opcode;
    }

    public String getRelatedGUID() {
        return relatedGUID;
    }

    public void setRelatedGUID(String relatedGUID) {
        this.relatedGUID = relatedGUID;
    }

    public Integer getRelatedEntry() {
        return relatedEntry;
    }

    public void setRelatedEntry(Integer relatedEntry) {
        this.relatedEntry = relatedEntry;
    }

    public void setMinTime(String formattedDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss.SSS");
        this.minTime = LocalDateTime.parse(formattedDateTime, formatter);
    }

    public void setMinTime(int year, int month, int day, int hour, int minute, int second, int millisecond) {
        this.minTime = LocalDateTime.of(year, month, day, hour, minute, second, millisecond*1_000_000);
    }

    public void setMaxTime(String formattedDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss.SSS");
        this.maxTime = LocalDateTime.parse(formattedDateTime, formatter);
    }

    public void setMaxTime(int year, int month, int day, int hour, int minute, int second, int millisecond) {
        this.maxTime = LocalDateTime.of(year, month, day, hour, minute, second, millisecond*1_000_000);
    }
}
