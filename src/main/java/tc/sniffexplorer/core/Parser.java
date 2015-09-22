/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
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
public class Parser {
    
    private static Logger log = LoggerFactory.getLogger(Parser.class);
    
//    private int countSMSG=0;
//    private int countCMSG=0;

    // file procuded during the serialiation of 
    // the messages after they have been all parsed and before they are selected by the criterias.
    // the goal here is to retake the data from this file each time the user use different filters.
    // this way, the parsing phase (first phase) can be skipped.
    public final static String DATA_FILE_NAME="data.ser";
    private String inputFileName; // sniff produced by wpp
    private FileOutputStream fop;
    private ObjectOutputStream oos;
    
    public Parser(String fileName){
        this.inputFileName=fileName;
        try {
            fop = new FileOutputStream(DATA_FILE_NAME);
            oos = new ObjectOutputStream(fop);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void parseFile(){
        
        List<String> lines=new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFileName))) {
            for (String line; (line = br.readLine()) != null;) {
                if(!line.isEmpty() && !line.trim().startsWith("# "))
                    lines.add(line);
                else if(!lines.isEmpty()){
                    Message msg=parseOneMessage(lines);
                    if(msg!=null)
                        serialize(msg);
                    
                    lines.clear();
                }
            }
        } catch (FileNotFoundException ex) {
            log.error(null, ex);
        } catch (IOException ex) {
            log.error(null, ex);
        } finally{
            cleanUp();
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
         *  READ AND SET UP TIME AND DATE 
         */
        // @todo: implement this
        
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

    /** THE OBJECT MUST BE SERIALIZED (AND UNSERIALIZED) ONE BY ONE. WE ARE NOT SERIALIZING A Collection<Message> OBJECT. THIS IS DONE ON PURPOSE.
     * 
     * @param msg 
     */
    private void serialize(Message msg) {
        try {
            oos.writeObject(msg);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cleanUp() {
        try {
            fop.close();
            oos.close();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
