/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.model.smsg;

import java.util.List;
import tc.sniffexplorer.model.Message;
import tc.sniffexplorer.model.OpCodeType;

/**
 *
 * @author chaouki
 */
public class SpellPeriodicAuraLogMessage extends Message {
    
    @Override
    public String getOpCode() {
        return "SMSG_SPELL_PERIODIC_AURA_LOG";
    }

    @Override
    public OpCodeType getOpCodeType() {
        return OpCodeType.SMSG;
    }

    @Override
    public void initialize(List<String> lines) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
