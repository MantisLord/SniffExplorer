/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.criteria;

import tc.sniffexplorer.message.Message;
import tc.sniffexplorer.message.OpCode;

/**
 *
 * @author chaouki
 */
public class Criteria {
    
    private OpCode opcode;
//    private Date minTime;
//    private Date maxTime;
    
    //search for this GUID in every GUID mentioned by the message
    private Long relatedGUID; // (do not forget to use Long.parseUnsignedLong() and Long.toUnsignedString() for this Long.
    private Integer relatedEntry; //  search for this entry in every Entry mentioned by the message
    
    public boolean isSatisfiedBy(Message message){ // AND is applied between each condition inside a Criteria object. Meaning if one condition fails, the whole thing fails.
        if(message==null)
            return false;
        
        if(relatedGUID!= null && !message.contains(relatedGUID))
            return false;
        
        if(relatedGUID!= null && !message.contains(relatedEntry))
            return false;
         
        return true;
    }

    public OpCode getOpcode() {
        return opcode;
    }

    public void setOpcode(OpCode opcode) {
        this.opcode = opcode;
    }

    public Long getRelatedGUID() {
        return relatedGUID;
    }

    public void setRelatedGUID(Long relatedGUID) {
        this.relatedGUID = relatedGUID;
    }

    public Integer getRelatedEntry() {
        return relatedEntry;
    }

    public void setRelatedEntry(Integer relatedEntry) {
        this.relatedEntry = relatedEntry;
    }
    
    
}
