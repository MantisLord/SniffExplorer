/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.launcher;

import tc.sniffexplorer.core.Parser;
import tc.sniffexplorer.core.Viewer;
import tc.sniffexplorer.core.ViewerFile;
import tc.sniffexplorer.criteria.CriteriaSet;
import tc.sniffexplorer.criteria.smsg.SpellStartCriteria;

/**
 *
 * @author chaouki
 */
public class SniffExplorer {
    
    private static final String SNIFF_FILE_NAME="sniff.txt";
    private static final Integer CASTER_ENTRY=15989; // Sapphiron's entry in Naxx 10.

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /**
         * In the early  versions of the software, until the criteria UI is ready, 
         * we are going to display the SMSG_SPELL_START messages only. 
         */
        SpellStartCriteria criteria=new SpellStartCriteria();
        criteria.setCasterEntry((args.length==2)? Integer.parseInt(args[1]):CASTER_ENTRY);
        CriteriaSet criteriaSet=new CriteriaSet();
        criteriaSet.addCriteria(criteria);
        
        Viewer viewer=new ViewerFile();
        
        Parser parser=new Parser();
        parser.parseFile((args.length>0)?args[0]:SNIFF_FILE_NAME, criteriaSet, viewer); // the name of the file is either given as a parameter when executing the jar or it defaults to the one specified above
        
        viewer.cleanup();
    }
    
}
