/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tc.sniffexplorer.model.Message;
import tc.sniffexplorer.model.OpCode;
import tc.sniffexplorer.model.OpCodeType;
import tc.sniffexplorer.model.smsg.EmoteMessage;
import tc.sniffexplorer.model.smsg.OnMonsterMoveMessage;
import tc.sniffexplorer.model.smsg.SpellGoMessage;
import tc.sniffexplorer.model.smsg.SpellPeriodicAuraLogMessage;
import tc.sniffexplorer.model.smsg.SpellStartMessage;
import tc.sniffexplorer.model.smsg.UpdateObjectMessage;

/**
 *
 * @author chaouki
 */
public class Core {
    
    private static Logger log = LoggerFactory.getLogger(Core.class);
    
    private static final boolean OUTPUT=false;
    
    private static int countSMSG=0;
    private static int countCMSG=0;
    
    public static void parseFile(String fileName){
        
        List<String> lines=new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            for (String line; (line = br.readLine()) != null;) {
                if(!line.isEmpty() && !line.trim().startsWith("# "))
                    lines.add(line);
                else if(!lines.isEmpty()){
                    Message msg=parseOneMessage(lines);
                    if(msg!=null && isMsgToBeIncluded(msg))
                        addToModel(msg);
                    
                    lines.clear();
                }
            }
        } catch (FileNotFoundException ex) {
            log.error(null, ex);
        } catch (IOException ex) {
            log.error(null, ex);
        }
    }

    private static Message parseOneMessage(List<String> lines) {
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
                log.debug("Unvalid OpCode found:");
                log.debug(lines.get(0));
                return null;
        }
        
        switch(opCodeString){
            case "SMSG_SPELL_START":                        // 0x131
                msg=new SpellStartMessage();
                msg.setType(OpCodeType.SMSG);
                break;
            case "SMSG_SPELL_GO":                           // 0x132
                msg=new SpellGoMessage();
                msg.setType(OpCodeType.SMSG);
                break;
            case "SMSG_SPELL_PERIODIC_AURA_LOG":            // 0x24E
                msg=new SpellPeriodicAuraLogMessage();
                msg.setType(OpCodeType.SMSG);
                break;
            case "SMSG_EMOTE":                              // 0x103
                msg=new EmoteMessage();
                msg.setType(OpCodeType.SMSG);
                break;
            case "SMSG_UPDATE_OBJECT":                      // 0x0A9
                msg=new UpdateObjectMessage();
                msg.setType(OpCodeType.SMSG);
                break;
            case "SMSG_ON_MONSTER_MOVE":                    // 0x0DD
                msg=new OnMonsterMoveMessage();
                msg.setType(OpCodeType.SMSG);
                break;
//            case "SMSG_MOVE_UPDATE":                        // XXXXXXX
//                msg=new SpellStartMessage();
//                break;
                
            default:
                log.info("Unsupported OpCode found: "+opCodeString);
                return null;
        }
        
        /**
         *  READ AND SET UP TIME AND DATE 
         */
        // @todo: implement this
        
        /**
         *  SPECIFIC MESSAGE READING PROSEDURE
         */
        try {
           msg.initialize(lines);
        } catch (Exception e) {
            log.error("Parsing failed", e);
            msg.printError(lines);
        }
        
        if(OUTPUT){
        for(String line:lines)
            System.out.println(line);
        System.out.println("FIN-------------------------");
        }
        
        return msg;
    }

    private static boolean isMsgToBeIncluded(Message msg) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return true;
    }

    private static void addToModel(Message msg) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
