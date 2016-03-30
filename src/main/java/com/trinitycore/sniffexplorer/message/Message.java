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
    
    protected static Logger log = LoggerFactory.getLogger(Message.class);
    
    private int id;
    private LocalDateTime time;
    
    public abstract void initialize(List<String> lines) throws ParseException;
    public abstract OpCode getOpCode();
    public abstract OpCodeType getOpCodeType();
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

    public void printError(List<String> lines){
        log.error("Coudn't process the following "+getClass().getSimpleName()+": START-------------------");
        for(String line:lines)
            log.error(line);
        log.error("END--------------------------------------------------------------------------");
    }
}

