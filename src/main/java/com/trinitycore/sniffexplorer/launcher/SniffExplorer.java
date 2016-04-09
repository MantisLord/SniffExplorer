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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        criteriaSet.setGlobalMinTime("06/16/2012 22:51:09.582");
        criteriaSet.setGlobalMaxTime("06/16/2012 23:01:02.589");

//        String npcGUID = "0xF1303E750000C3BD";
        String npcGUID = "0xF1303E760000BEC3";
        String playerGUID = "0x60000000320D4F0";

        criteriaSet.addCriteria(
                new OnMonsterMoveCriteria(npcGUID),
                new MoveUpdateCriteria(playerGUID),

//                new AttackerStateUpdateCriteria(null, playerGUID),
//                new AttackerStateUpdateCriteria(playerGUID, null),
//                new AttackerStateUpdateCriteria(null, npcGUID),
                new AttackerStateUpdateCriteria(npcGUID, playerGUID),

                new AttackStartStopCriteria(npcGUID, null),
                new UpdateObjectCriteria(npcGUID, "UNIT_FIELD_BOUNDINGRADIUS"),
                new UpdateObjectCriteria(npcGUID, "UNIT_FIELD_COMBATREACH"),
                new UpdateObjectCriteria(playerGUID, "UNIT_FIELD_BOUNDINGRADIUS"),
                new UpdateObjectCriteria(playerGUID, "UNIT_FIELD_COMBATREACH"),
                new SpellCriteria(null, null, npcGUID)
        );

        // select the way the output will be rendered.
        Viewer viewer=new ViewerFile(OUTPUT_SNIFF_FILE_NAME, false);

        Parser parser=new Parser();
        parser.parseFile(INPUT_SNIFF_FILE_NAME, criteriaSet, message -> {
            viewer.show(message);

        });

        viewer.cleanup();
    }
    
}
