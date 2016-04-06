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

        // Here you should construct the CriteriaSet. This is all the criterias that will be used to determin which
        // messages should be shown. If you are a user of the application, you don't need to modify any other part of
        // the application then here.
        CriteriaSet criteriaSet=new CriteriaSet();

        OnMonsterMoveCriteria onMonsterMoveCriteria=new OnMonsterMoveCriteria();
//        onMonsterMoveCriteria.setFacingPlayer(true);
        onMonsterMoveCriteria.setUnitGUID("0xF1303E750000C3BD");
        criteriaSet.addCriteria(onMonsterMoveCriteria);

        MoveUpdateCriteria moveUpdateCriteria=new MoveUpdateCriteria("0x60000000320D4F0");
        criteriaSet.addCriteria(moveUpdateCriteria);


        AttackerStateUpdateCriteria aaCriteriaA=new AttackerStateUpdateCriteria();
        aaCriteriaA.setTargetGUID("0x60000000320D4F0");
        AttackerStateUpdateCriteria aaCriteriaB=new AttackerStateUpdateCriteria();
        aaCriteriaB.setAttackerGUID("0x60000000320D4F0");

        AttackerStateUpdateCriteria aaCriteriaC=new AttackerStateUpdateCriteria();
        aaCriteriaC.setTargetGUID("0xF1303E750000C3BD");
        AttackerStateUpdateCriteria aaCriteriaD=new AttackerStateUpdateCriteria();
        aaCriteriaD.setAttackerGUID("0xF1303E750000C3BD");
        aaCriteriaD.setTargetGUID("0x60000000320D4F0");


//        criteriaSet.addCriteria(aaCriteriaA);
//        criteriaSet.addCriteria(aaCriteriaB);
//        criteriaSet.addCriteria(aaCriteriaC);
        criteriaSet.addCriteria(aaCriteriaD);

        // select the way the output will be rendered.
        Viewer viewer=new ViewerFile(OUTPUT_SNIFF_FILE_NAME, false);

        Parser parser=new Parser();
        parser.parseFile(INPUT_SNIFF_FILE_NAME, criteriaSet, message -> {
            viewer.show(message);

        });

        viewer.cleanup();
    }
    
}
