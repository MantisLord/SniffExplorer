/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinitycore.sniffexplorer.core;

import com.trinitycore.sniffexplorer.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 *
 * @author chaouki
 */
public class ViewerFullMessageFile implements Viewer{

    private static Logger log = LoggerFactory.getLogger(ViewerFullMessageFile.class);

    private PrintWriter out;


    public ViewerFullMessageFile(String fileName){
        try {
            out= new PrintWriter(fileName);
        } catch (FileNotFoundException ex) {
            log.warn("FileNotFoundException thrown in ViewerFile.",ex);
        }
    }

    @Override
    public void show(Message message) {
        message.getMessageText().forEach(out::println);
        out.println();
    }

    @Override
    public void cleanup(){
        out.close();
    }
}
