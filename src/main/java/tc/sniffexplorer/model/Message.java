/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.model;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tc.sniffexplorer.exceptions.ParseException;

/**
 *
 * @author chaouki
 */
public abstract class Message {
    
    protected static Logger log = LoggerFactory.getLogger(Message.class);
    
    private int id;
//    private String message;
//    private Date time;
    
    public abstract void initialize(List<String> lines) throws ParseException;
    public abstract String getOpCode();
    public abstract OpCodeType getOpCodeType();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void printError(List<String> lines){
        log.error("Coudn't process the following "+getClass().getSimpleName()+": START-------------------");
        for(String line:lines)
            log.error(line);
        log.error("END--------------------------------------------------------------------------");
    }
}

