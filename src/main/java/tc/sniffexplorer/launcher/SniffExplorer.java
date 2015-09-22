/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.launcher;

import java.util.List;
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // phase 1 and 2 (serialisation is done in Parser too)
        Parser parser=new Parser("sniff4.txt");
        parser.parseFile();
        
        /** Phase 3. We gonna ask the user for his criterias. in the early 
         * versions of the software, we are just gonna display only 
         * the SPELL_START messages. 
         */
        Criteria criteria=new SpellStartCriteria(); // @todo: complete
        CriteriaSet criteriaSet=new CriteriaSet();
        criteriaSet.addCriteria(criteria);
        List<Message> messages=Filter.applyCriterion(criteriaSet);
        
    }
    
}
