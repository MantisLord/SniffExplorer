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
    
    private final boolean OUTPUT=false;
    
    private static int countSMSG=0;
    private static int countCMSG=0;
    
    public void execute(){
//        File file=new File();
        
        String file = "sniff2.txt";
        List<String> lines=new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            for (String line; (line = br.readLine()) != null;) {
                if(!line.isEmpty())
                    lines.add(line);
                else{
                    processOneMessage(lines);
                    lines.clear();
                }
            }
        } catch (FileNotFoundException ex) {
            log.error(null, ex);
        } catch (IOException ex) {
            log.error(null, ex);
        }
    }

    private void processOneMessage(List<String> lines) {
        Message msg=null;
        
        //      0               1               2       3     4     5     6   7     8           9           10      11
        // ServerToClient: SMSG_ATTACK_START (0x2D15) Length: 16 ConnIdx: 2 Time: 06/16/2012 22:48:04.393 Number: 7022
        // ClientToServer: 30740 (0x7814) Length: 33 ConnIdx: 2 Time: 06/16/2012 22:48:04.409 Number: 7023
        String[] words=lines.get(0).split("\\s+");
        if(words.length!=12 || !words[3].equals("Length:") || !words[5].equals("ConnIdx:") || !words[7].equals("Time:") || !words[10].equals("Number:")){
            log.info("Unidentified message found:");
            log.info(lines.get(0));
            return;
        }
        
        String opCodeTypeString=words[0];
        String opCodeString=words[1];
        String dateString=words[8];
        String timeString=words[9];
                
        /** 
         *   READ OPCODE AND CONSTRUCT APPROPRIATE MESSAGE OBJECT
         */
        
        // unnamed op code
        if(!opCodeString.substring(1, 4).equals("MSG")){
                log.info("Unnamed OpCode found: (skipping)");
                log.info(lines.get(0));
                return;
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
                log.info("Unidentified OpCode found: "+opCodeString);
                return;
        }
        
        /**
         *  READ AND SET UP TIME AND DATE @todo: implement this
         */
        
        /**
         *  SPECIFIC MESSAGE READING PROSEDURE
         */
        msg.initialize(lines);
        
        if(OUTPUT){
        for(String line:lines)
            System.out.println(line);
        System.out.println("FIN-------------------------");
        }
    }
    
}
