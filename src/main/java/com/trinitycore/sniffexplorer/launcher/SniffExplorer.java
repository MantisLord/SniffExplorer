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

        // Here you should construct the CriteriaSet. It represent every criteria that will be used to determine which
        // messages should be filtered. If you are an user of the application, you don't need to modify any other part of
        // the application than here.
        CriteriaSet criteriaSet=new CriteriaSet();

        String npcGUID = "0xF1303E750000C3BD";
        String playerGUID = "0x60000000320D4F0";

        OnMonsterMoveCriteria onMonsterMoveCriteria=new OnMonsterMoveCriteria();
//        onMonsterMoveCriteria.setFacingPlayer(true);

        onMonsterMoveCriteria.setUnitGUID(npcGUID);
        criteriaSet.addCriteria(onMonsterMoveCriteria);

        MoveUpdateCriteria moveUpdateCriteria=new MoveUpdateCriteria(playerGUID);
        criteriaSet.addCriteria(moveUpdateCriteria);


        AttackerStateUpdateCriteria aaCriteriaA=new AttackerStateUpdateCriteria();
        aaCriteriaA.setTargetGUID(playerGUID);
        AttackerStateUpdateCriteria aaCriteriaB=new AttackerStateUpdateCriteria();
        aaCriteriaB.setAttackerGUID(playerGUID);

        AttackerStateUpdateCriteria aaCriteriaC=new AttackerStateUpdateCriteria();
        aaCriteriaC.setTargetGUID(npcGUID);
        AttackerStateUpdateCriteria aaCriteriaD=new AttackerStateUpdateCriteria();
        aaCriteriaD.setAttackerGUID(npcGUID);
        aaCriteriaD.setTargetGUID(playerGUID);

        AttackStartStopCriteria attackStartStopCriteria=new AttackStartStopCriteria();
        attackStartStopCriteria.setAttackerGUID(npcGUID);


//        criteriaSet.addCriteria(aaCriteriaA);
//        criteriaSet.addCriteria(aaCriteriaB);
//        criteriaSet.addCriteria(aaCriteriaC);
        criteriaSet.addCriteria(aaCriteriaD);

        criteriaSet.addCriteria(attackStartStopCriteria);

        // select the way the output will be rendered.
        Viewer viewer=new ViewerFile(OUTPUT_SNIFF_FILE_NAME, false);

        Parser parser=new Parser();
        parser.parseFile(INPUT_SNIFF_FILE_NAME, criteriaSet, message -> {
            viewer.show(message);

        });

        viewer.cleanup();
    }
    
}
