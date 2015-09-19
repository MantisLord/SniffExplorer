/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.model;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author chaouki
 */
public abstract class Message {
    
    private static Logger log = LoggerFactory.getLogger(Message.class);
    
    private int id;
    private OpCodeType type;
//    private String message;
//    private Date time;
    
    public abstract void initialize(List<String> lines);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OpCodeType getType() {
        return type;
    }

    public void setType(OpCodeType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        String ret=type.toString()+System.getProperty("line.separator");
        
        return ret;
    }
}

