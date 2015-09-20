/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.model.smsg;

import java.util.List;
import tc.sniffexplorer.exceptions.ParseException;
import tc.sniffexplorer.model.Message;
import tc.sniffexplorer.model.entities.Creature;
import tc.sniffexplorer.model.entities.Pet;
import tc.sniffexplorer.model.entities.Player;
import tc.sniffexplorer.model.entities.Unit;
import tc.sniffexplorer.model.entities.Vehicule;

/** Class which represent SMSG_SPELL_GO messages
 *
 * @author chaouki
 */
public class SpellGoMessage extends SpellMessage {
    

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
