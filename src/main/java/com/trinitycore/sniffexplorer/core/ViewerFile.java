/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinitycore.sniffexplorer.core;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import com.trinitycore.sniffexplorer.message.Message;
import org.slf4j.LoggerFactory;

/**
 *
 * @author chaouki
 */
public class ViewerFile implements Viewer{

    private static Logger log = LoggerFactory.getLogger(Parser.class);
    
    private PrintWriter out;

    // used to group together spells and other events happening at the same time
    private static final int MAX_DURATION_DIFFERENCE = 400;
    private static final boolean GROUP_BY_TIMESTAMP = true;
    private LocalDateTime timeStampOfPreviousMessage;
    
    public ViewerFile(String fileName){
        try {
            out= new PrintWriter(fileName);
        } catch (FileNotFoundException ex) {
            log.warn("FileNotFoundException thrown in ViewerFile.",ex);
        }
    }

    @Override
    public void show(Message message) {
        if(GROUP_BY_TIMESTAMP && timeStampOfPreviousMessage != null &&
                message.getTime().until(timeStampOfPreviousMessage, ChronoUnit.MILLIS) > MAX_DURATION_DIFFERENCE)
            out.println();

        message.display(out);
        timeStampOfPreviousMessage=message.getTime();
    }

    @Override
    public void cleanup(){
        out.close();
    }
}
