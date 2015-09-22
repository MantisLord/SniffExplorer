/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.model.smsg;

import java.util.List;
import tc.sniffexplorer.exceptions.ParseException;
import tc.sniffexplorer.model.OpCodeType;
import tc.sniffexplorer.model.gameentities.Unit;

/** Class which represent SMSG_SPELL_GO messages
 *
 * @author chaouki
 */
public class SpellGoMessage extends SpellMessage {
    
    @Override
    public String getOpCode() {
        return "SMSG_SPELL_GO";
    }

    @Override
    public OpCodeType getOpCodeType() {
        return OpCodeType.SMSG;
    }

    private Integer targetFlags;
    private Unit targetUnit;

    @Override
    public void initialize(List<String> lines) throws ParseException {
        super.initialize(lines);
        
        /**
         * Target(s)
         */
//        throw new ParseException("Targets search unsupported yet.");
    }
    
}
