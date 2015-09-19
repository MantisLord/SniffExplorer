/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.model.smsg;

import java.util.List;
import tc.sniffexplorer.model.Message;

/** Class which represent SMSG_SPELL_START messages
 *
 * @author chaouki
 */
public class SpellStartMessage extends Message {
    private String casterGUID;
    private String targetGUID;

    @Override
    public void initialize(List<String> lines) {
        
    }

   
}
