/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.launcher;

import java.util.List;
import tc.sniffexplorer.core.FileIO;
import tc.sniffexplorer.core.Filter;
import tc.sniffexplorer.core.Parser;
import tc.sniffexplorer.criteria.Criteria;
import tc.sniffexplorer.criteria.CriteriaSet;
import tc.sniffexplorer.criteria.smsg.SpellStartCriteria;
import tc.sniffexplorer.model.Message;

/**
 *
 * @author chaouki
 */
public class SniffExplorer {
    
    private static final String SNIFF_FILE_NAME="sniff4.txt";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // phase 1 and 2 (serialisation is called inside the Parser)
        Parser parser=new Parser();
        FileIO ioManager=new FileIO();
        parser.parseFile(ioManager, SNIFF_FILE_NAME);
        
        /** Phase 3. We gonna ask the user for his criterias. in the early 
         * versions of the software, we are going to display  
         * the SPELL_START messages only. 
         */
        {
            Message message=ioManager.deserializeOneMessage();
            while(message!=null){
                // TREATMENT
                // XXXXXXXXXXXXXXXXXXXXXX
                
                message=ioManager.deserializeOneMessage();
            }
        }
        Criteria criteria=new SpellStartCriteria(); // @todo: complete
        CriteriaSet criteriaSet=new CriteriaSet();
        criteriaSet.addCriteria(criteria);
        List<Message> messages=Filter.applyCriterion(criteriaSet);
        
    }
    
}
