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
import tc.sniffexplorer.criteria.smsg.SpellGoCriteria;
import tc.sniffexplorer.criteria.smsg.SpellStartCriteria;

/**
 *
 * @author chaouki
 */
public class SniffExplorer {

    private static final String INPUT_SNIFF_FILE_NAME ="sniff.txt";
    private static final String OUTPUT_SNIFF_FILE_NAME = "sniffexplorer.txt";

//    private static final Integer CASTER_ENTRY=15989; // Sapphiron's entry in Naxx 10.

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /**
         * In the early  versions of the software, until the criteria UI is ready, 
         * we are going to display the SMSG_SPELL_START messages only. 
         */
        SpellStartCriteria spellStartCriteriaA=new SpellStartCriteria();
        spellStartCriteriaA.setSpellId(49560);
        
        SpellStartCriteria spellStartCriteriaB=new SpellStartCriteria();
        spellStartCriteriaB.setSpellId(49576);
        
        SpellStartCriteria spellStartCriteriaC=new SpellStartCriteria();
        spellStartCriteriaC.setSpellId(49575);
        
        SpellStartCriteria spellStartCriteriaD=new SpellStartCriteria();
        spellStartCriteriaD.setSpellId(51399);
        
        SpellGoCriteria spellGoCriteriaA=new SpellGoCriteria();
        spellGoCriteriaA.setSpellId(49560);
        
        SpellGoCriteria spellGoCriteriaB=new SpellGoCriteria();
        spellGoCriteriaB.setSpellId(49560);
        
        SpellGoCriteria spellGoCriteriaC=new SpellGoCriteria();
        spellGoCriteriaC.setSpellId(49575);
        
        SpellGoCriteria spellGoCriteriaD=new SpellGoCriteria();
        spellGoCriteriaD.setSpellId(51399);
        
        CriteriaSet criteriaSet=new CriteriaSet();
        criteriaSet.addCriteria(spellStartCriteriaA);
        criteriaSet.addCriteria(spellStartCriteriaB);
        criteriaSet.addCriteria(spellStartCriteriaC);
        criteriaSet.addCriteria(spellStartCriteriaD);
        
        criteriaSet.addCriteria(spellGoCriteriaA);
        criteriaSet.addCriteria(spellGoCriteriaB);
        criteriaSet.addCriteria(spellGoCriteriaC);
        criteriaSet.addCriteria(spellGoCriteriaD);
        
        Viewer viewer=new ViewerFile(OUTPUT_SNIFF_FILE_NAME);
        
        Parser parser=new Parser();
        parser.parseFile(INPUT_SNIFF_FILE_NAME, criteriaSet, viewer); // the name of the file is either given as a parameter when executing the jar or it defaults to the one specified above
        
        viewer.cleanup();
    }
    
}
