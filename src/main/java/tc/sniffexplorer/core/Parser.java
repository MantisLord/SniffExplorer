/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tc.sniffexplorer.criteria.CriteriaSet;
import tc.sniffexplorer.message.Message;
import tc.sniffexplorer.message.smsg.EmoteMessage;
import tc.sniffexplorer.message.smsg.OnMonsterMoveMessage;
import tc.sniffexplorer.message.smsg.SpellGoMessage;
import tc.sniffexplorer.message.smsg.SpellPeriodicAuraLogMessage;
import tc.sniffexplorer.message.smsg.SpellStartMessage;
import tc.sniffexplorer.message.smsg.UpdateObjectMessage;

/**
 *
 * @author chaouki
 */
public class Parser {
    
    private static Logger log = LoggerFactory.getLogger(Parser.class);
    
//    private int countSMSG=0;
//    private int countCMSG=0;

    public void parseFile(String sniffFileName, CriteriaSet criteriaSet, Viewer viewer){
        
        List<String> lines=new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(sniffFileName))) {
            for (String line; (line = br.readLine()) != null;) {
                if(!line.isEmpty() && !line.trim().startsWith("# "))
                    lines.add(line);
                else if(!lines.isEmpty()){
                    Message msg=parseOneMessage(lines);
                    if(msg!=null && criteriaSet.IsSatisfiedBy(msg))
                        viewer.show(msg);
                    
                    lines.clear();
                }
            }
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    private Message parseOneMessage(List<String> lines) {
        Message msg=null;
        
        //      0               1               2       3     4     5     6   7     8           9           10      11
        // ServerToClient: SMSG_ATTACK_START (0x2D15) Length: 16 ConnIdx: 2 Time: 06/16/2012 22:48:04.393 Number: 7022
        // ClientToServer: 30740 (0x7814) Length: 33 ConnIdx: 2 Time: 06/16/2012 22:48:04.409 Number: 7023
        String[] words=lines.get(0).split("\\s+");
        if(words.length!=12 || !words[3].equals("Length:") || !words[5].equals("ConnIdx:") || !words[7].equals("Time:") || !words[10].equals("Number:")){
            log.info("Unidentified message found:");
            log.info(lines.get(0));
            return null;
        }
        
        String opCodeString=words[1];
        String dateString=words[8];
        String timeString=words[9];
                
        /** 
         *   READ OPCODE AND CONSTRUCT APPROPRIATE MESSAGE OBJECT
         */
        
        // unnamed op code. dont process.
        if(opCodeString.length()<4 || !opCodeString.substring(1, 4).equals("MSG")){
                log.debug("Unidentified OpCode found:");
                log.debug(lines.get(0));
                return null;
        }
        
        switch(opCodeString){
            case "SMSG_SPELL_START":                        // 0x131
                msg=new SpellStartMessage();
                break;
            case "SMSG_SPELL_GO":                           // 0x132
                msg=new SpellGoMessage();
                break;
            case "SMSG_SPELL_PERIODIC_AURA_LOG":            // 0x24E
                msg=new SpellPeriodicAuraLogMessage();
                break;
            case "SMSG_EMOTE":                              // 0x103
                msg=new EmoteMessage();
                break;
            case "SMSG_UPDATE_OBJECT":                      // 0x0A9
                msg=new UpdateObjectMessage();
                break;
            case "SMSG_ON_MONSTER_MOVE":                    // 0x0DD
                msg=new OnMonsterMoveMessage();
                break;
//            case "SMSG_MOVE_UPDATE":                        // XXXXXXX
//                msg=new SpellStartMessage();
//                break;
                
            default:
                log.info("Unsupported OpCode found: "+opCodeString);
                return null;
        }
        
        /**
         * READ AND SET UP THE TIME AND DATE 
         * Template: 06/16/2012 22:48:04.393
         */
        String date=words[8]+" "+words[9];
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS", Locale.ENGLISH);
        try {  
            Date result =  df.parse(date);
            msg.setDate(result);
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /**
         * READ AND SET UP THE ID
         */
        try{
            msg.setId(Integer.valueOf(words[11]));
        }catch(Exception e){
            e.printStackTrace();
            msg.setId(-1);
        }
        
        /**
         *  SPECIFIC MESSAGES PARSING PROCEDURE (dependent on the opcode)
         */
        try {
           msg.initialize(lines);
        } catch (Exception e) {
            log.error("Complete parsing failed:", e);
            msg.printError(lines);
        }
        
        return msg;
    }

    
}
