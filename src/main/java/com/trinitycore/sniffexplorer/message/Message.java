/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinitycore.sniffexplorer.message;

import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.trinitycore.sniffexplorer.exceptions.ParseException;

/**
 *
 * @author chaouki
 */
public abstract class Message implements Serializable {
    
    private static Logger log = LoggerFactory.getLogger(Message.class);

    private List<String> messageText;
    private int id;
    private LocalDateTime time;
    private Direction direction;
    private OpCode opCode;
    
    public void initialize(List<String> lines) throws ParseException{
        this.messageText = lines;
    }

    abstract public boolean contains(Integer relatedEntry);
    abstract public boolean contains(String relatedGUID);

    public void display(PrintWriter printWriter){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss.SSS");
        Formatter formatter = new Formatter(printWriter);
        formatter.format("%-17s %23s ", getOpCode().toString(), dateTimeFormatter.format(time));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public List<String> getMessageText() {
        return messageText;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public OpCode getOpCode() {
        return opCode;
    }

    public void setOpCode(OpCode opCode) {
        this.opCode = opCode;
    }

}

