/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.launcher;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import tc.sniffexplorer.core.FileIO;
import tc.sniffexplorer.core.Parser;
import tc.sniffexplorer.core.Viewer;
import tc.sniffexplorer.core.ViewerFile;
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
        parser.parseFile(ioManager, (args.length>0)?args[0]:SNIFF_FILE_NAME); // the name of the file is either given as a parameter when executing the jar or it defaults to the one specified above
        
        /** Phase 3. We gonna ask the user for his criterias. in the early 
         * versions of the software, we are going to display  
         * the SPELL_START messages only. 
         */
        SpellStartCriteria criteria=new SpellStartCriteria(); // @todo: complete
        criteria.setCasterEntry(15989); // Sapphiron's entry in 10 man
        CriteriaSet criteriaSet=new CriteriaSet();
        criteriaSet.addCriteria(criteria);
        
        List<Message> messagesAfterFilter = new ArrayList<>();
        for(Message message=ioManager.deserializeOneMessage(); message!=null; message=ioManager.deserializeOneMessage()){
            if(criteriaSet.IsSatisfiedBy(message))
                messagesAfterFilter.add(message);
        }
        
        // phase 4
        Viewer viewer=new ViewerFile();
        for(Message message:messagesAfterFilter)
            viewer.show(message);
        viewer.cleanup();
    }
    
}
