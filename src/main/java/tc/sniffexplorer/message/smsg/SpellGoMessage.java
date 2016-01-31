/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.message.smsg;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import tc.sniffexplorer.exceptions.ParseException;
import tc.sniffexplorer.message.OpCode;
import tc.sniffexplorer.message.OpCodeType;
import tc.sniffexplorer.gameentities.Unit;

/** Class which represent SMSG_SPELL_GO messages
 *
 * @author chaouki
 */
public class SpellGoMessage extends SpellMessage {
    
    @Override
    public OpCode getOpCode() {
        return OpCode.SMSG_SPELL_GO;
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

    @Override
    public boolean contains(Integer relatedEntry) {
        return false;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contains(Long relatedGUID) {
        return false;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void display(PrintWriter writer) { // todo: FINISH THIS. ATM ITS A QUICK AND DIRTY COPY PAST OF SPELL_START_MESSAGE
        DateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
        writer.print(getOpCode().toString()+"    "+dateFormat.format(getDate())+" "); // todo: extra spaces has been added temporarly to take into account the different lenghts of the op codes. Need a proper fix
        
        writer.format("Spell ID: %6d. Caster Unit: %s. ", getSpellId(), getCasterUnit().toString());
        if(getItemCasterGUID()!=null)
            writer.format("Item caster GUID: %18s. ", getItemCasterGUID().toString());
//        writer.format("Target Flags: %2d. Target Unit: %s Number: %d.%n", getTargetFlags(), getTargetUnit(), getId());
    }
    
}
