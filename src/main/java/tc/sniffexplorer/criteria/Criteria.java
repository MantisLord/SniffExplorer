/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.criteria;

import tc.sniffexplorer.model.Message;
import tc.sniffexplorer.model.OpCode;

/**
 *
 * @author chaouki
 */
public class Criteria {
    
    private OpCode opcode;
//    private Date minTime;
//    private Date maxTime;
    
    //search for this GUID in every GUID contained by the message
    private Long relatedGUID; // (do not forget to use Long.parseUnsignedLong() and Long.toUnsignedString() for this Long.
    private Integer relatedEntry; //  search for this entry in every GUID contained by the message
    
    public boolean isSatisfiedBy(Message message){
        return true;
    }
}
