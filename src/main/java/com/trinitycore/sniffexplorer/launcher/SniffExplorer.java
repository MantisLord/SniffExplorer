/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinitycore.sniffexplorer.launcher;

import com.trinitycore.sniffexplorer.core.Parser;
import com.trinitycore.sniffexplorer.core.Viewer;
import com.trinitycore.sniffexplorer.core.ViewerFullMessageFile;
import com.trinitycore.sniffexplorer.criteria.CriteriaSet;
import com.trinitycore.sniffexplorer.criteria.smsg.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 *
 * @author chaouki
 */
public class SniffExplorer {

    protected static final Logger logger = LoggerFactory.getLogger(SniffExplorer.class);

//    private static final String INPUT_SNIFF = "C:\\Sniffs\\335\\bin";
        private static final String INPUT_SNIFF ="C:\\Sniffs\\335\\bin\\19-07_parsed.txt";
    private static final String OUTPUT_SNIFF_FILE_NAME = "sniffexplorer.txt";

    private static final Integer SAPPHIRON_ENTRY = 15989; // Sapphiron's entry in Naxx 10.
    private static final int GLUTH_ENTRY = 15932;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File dir = new File(INPUT_SNIFF);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            // case where INPUT_SNIFF is a directory. then analyse every files in it.
            for (File file : directoryListing) {
                if (file.getName().endsWith("_parsed.txt")) {
                    logger.warn("parsing now: " + file.toString());
                    processFile(file);
                }
            }
        } else {
            // INPUT_SNIFF is a file. analyse it.
            logger.warn("parsing now: " + dir.toString());
            processFile(dir);
        }
    }

    private static void processFile(File file) {
        // Here you should construct the CriteriaSet. It represent every criteria that will be used to determine which
        // messages should be filtered. If you are an user of the application, you don't need to modify any other part of
        // the application than here.
        CriteriaSet criteriaSet=new CriteriaSet();
//        criteriaSet.setGlobalMinTime("06/16/2012 23:19:09.791");
//        criteriaSet.setGlobalMaxTime("06/16/2012 23:21:32.703");

        String npcGUID = "0xF13000681114AB72";
        String playerGUID = "0x28000000322F2D4";

        criteriaSet.addCriteria(
                // add your criterias here

                new PlayerMovementCriteria("0x28000000322F2D4"),
                new PlayerMovementCriteria("0x280000003437ABE")
        );

        // select the way the output will be rendered.
        Viewer viewer=new ViewerFullMessageFile(OUTPUT_SNIFF_FILE_NAME);

        Parser parser=new Parser(file);
        // output the filtered messages to the viewer
        parser.parseFile(criteriaSet, viewer::show);
        // this methods needs to be called once message has been read in order to clear the resources.
        viewer.close();

//        new AoeSpellAnalyser().processFile(file);
    }
}