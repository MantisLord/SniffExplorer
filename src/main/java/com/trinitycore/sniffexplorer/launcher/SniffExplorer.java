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
import com.trinitycore.sniffexplorer.criteria.smsg.*;
import com.trinitycore.sniffexplorer.game.data.SplineType;
import com.trinitycore.sniffexplorer.message.smsg.SpellGoMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author chaouki
 */
public class SniffExplorer {


    protected static final Logger log = LoggerFactory.getLogger(SniffExplorer.class);

    private static final String INPUT_SNIFF_FILE_NAME ="sniff.txt";
//    private static final String INPUT_SNIFF_FILE_NAME ="sample";
    private static final String OUTPUT_SNIFF_FILE_NAME = "sniffexplorer.txt";

    private static final Integer SAPPHIRON_ENTRY =15989; // Sapphiron's entry in Naxx 10.
    private static final int GLUTH_ENTRY = 15932;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // construct the CriteriaSet
        CriteriaSet criteriaSet=new CriteriaSet();

//        SpellGoCriteria spellGoCriteria=new SpellGoCriteria();
//        spellGoCriteria.setHasNonEmptyImmuneMissList(true);
//        SpellCriteria spellCriteria=new SpellCriteria();
//        spellCriteria.setCasterEntry(GLUTH_ENTRY);
//        criteriaSet.addCriteria(spellCriteria);

        /*
        criteriaSet.addCriteria(new SpellCriteria(49560));
        criteriaSet.addCriteria(new SpellCriteria(49576));
        criteriaSet.addCriteria(new SpellCriteria(49575));
        criteriaSet.addCriteria(new SpellCriteria(51399));

        criteriaSet.addCriteria(new AuraUpdateCriteria(51399));
        criteriaSet.addCriteria(new AuraUpdateCriteria(49560));
//        criteriaSet.addCriteria(new AuraUpdateCriteria());
        */

//        SpellCriteria spellCriteria= new SpellCriteria();
//        spellCriteria.setCasterGUID("0x280000003283CBC");
//        spellCriteria.setMinTime("02/22/2010 02:46:11.000");
//        spellCriteria.setMaxTime("02/22/2010 02:46:13.000");
//        spellCriteria.setSpellId(85667);
//        criteriaSet.addCriteria(spellCriteria);

//        OnMonsterMoveCriteria onMonsterMoveCriteria=new OnMonsterMoveCriteria("0xF13060EC004BFC6C");
        OnMonsterMoveCriteria onMonsterMoveCriteria=new OnMonsterMoveCriteria(SplineType.FacingTarget);
        onMonsterMoveCriteria.setFacingPlayer(true);
        criteriaSet.addCriteria(onMonsterMoveCriteria);

        // select the way the output will be rendered.
        Viewer viewer=new ViewerFile(OUTPUT_SNIFF_FILE_NAME);

//        Set<Integer> spellIdSet=new HashSet<>();

        Parser parser=new Parser();
        parser.parseFile(INPUT_SNIFF_FILE_NAME, criteriaSet, message -> {
            viewer.show(message);
//            if(message instanceof SpellGoMessage)
//                spellIdSet.add(((SpellGoMessage) message).getSpellId());
        });

        viewer.cleanup();

//        log.warn(spellIdSet.toString());
    }
    
}
