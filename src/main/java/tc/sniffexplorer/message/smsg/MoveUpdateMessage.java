/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.message.smsg;

import java.io.PrintWriter;
import java.util.List;
import tc.sniffexplorer.message.Message;
import tc.sniffexplorer.message.OpCode;
import tc.sniffexplorer.message.OpCodeType;

/** SMSG_MOVE_UPDATE
 *
 * @author chaouki
 */
public class MoveUpdateMessage extends Message{
    
    @Override
    public OpCode getOpCode() {
        return OpCode.SMSG_MOVE_UPDATE;
    }

    @Override
    public OpCodeType getOpCodeType() {
        return OpCodeType.SMSG;
    }

    @Override
    public void initialize(List<String> lines) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contains(Integer relatedEntry) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contains(Long relatedGUID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void display(PrintWriter printWriter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
