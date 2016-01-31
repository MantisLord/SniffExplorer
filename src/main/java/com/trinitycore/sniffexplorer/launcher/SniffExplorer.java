/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinitycore.sniffexplorer.launcher;

import com.trinitycore.sniffexplorer.core.Parser;
import com.trinitycore.sniffexplorer.core.Viewer;
import com.trinitycore.sniffexplorer.core.ViewerFile;
import com.trinitycore.sniffexplorer.criteria.CriteriaSet;
import com.trinitycore.sniffexplorer.criteria.smsg.SpellCriteria;

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

        // construct the CriteriaSet
        SpellCriteria spellCriteriaA=new SpellCriteria();
        spellCriteriaA.setSpellId(49560);
        
        SpellCriteria spellCriteriaB=new SpellCriteria();
        spellCriteriaB.setSpellId(49576);
        
        SpellCriteria spellCriteriaC=new SpellCriteria();
        spellCriteriaC.setSpellId(49575);
        
        SpellCriteria spellCriteriaD=new SpellCriteria();
        spellCriteriaD.setSpellId(51399);
       
        
        CriteriaSet criteriaSet=new CriteriaSet();
        criteriaSet.addCriteria(spellCriteriaA);
        criteriaSet.addCriteria(spellCriteriaB);
        criteriaSet.addCriteria(spellCriteriaC);
        criteriaSet.addCriteria(spellCriteriaD);

        // select the way the output will be rendered.
        Viewer viewer=new ViewerFile(OUTPUT_SNIFF_FILE_NAME);
        
        Parser parser=new Parser();
        parser.parseFile(INPUT_SNIFF_FILE_NAME, criteriaSet, viewer); // the name of the file is either given as a parameter when executing the jar or it defaults to the one specified above
        
        viewer.cleanup();
    }
    
}
