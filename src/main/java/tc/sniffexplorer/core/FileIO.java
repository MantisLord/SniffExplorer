/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.core;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import tc.sniffexplorer.model.Message;

/**
 *
 * @author chaouki
 */
public class FileIO {

    // file procuded during the serialiation of 
    // the messages after they have been all parsed and before they are selected by the criterias.
    // the goal here is to retake the data from this file each time the user use different filters.
    // this way, the parsing phase (first phase) can be skipped.
    public final static String DATA_FILE_NAME = "data.ser";
    
    // OUT
    private FileOutputStream fop;
    private ObjectOutputStream oos;
    
    // IN
    ObjectInputStream ois;
    FileInputStream fip;

    public FileIO() {
        try {
            fop = new FileOutputStream(DATA_FILE_NAME);
            oos = new ObjectOutputStream(fop);
            
            fip = new FileInputStream(DATA_FILE_NAME);
            ois = new ObjectInputStream(fip);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * EACH MESSAGE OBJECT IS SERIALIZED (AND UNSERIALIZED) ONE BY ONE. WE ARE
     * NOT SERIALIZING A Collection<Message> OBJECT. THIS IS DONE ON PURPOSE IN
     * ORDER TO HANDLE EACH MESSAGE ONE AT THE TIME AND TO SAVE MEMORY.
     *
     * @param msg
     */
    public void serializeOneMessage(Message msg) {
        try {
            oos.writeObject(msg);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /** return a message or null if the end of file is reached.
     * 
     * @return 
     */
    public Message deserializeOneMessage(){
        Message message=null;
        try {
            message=(Message) ois.readObject();
        } catch (EOFException e) {  // end of file
            try {
                Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, "End of data file reached (input).");
                ois.close();
                fip.close();
            } catch (IOException ex) {
                Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            return message;
        }
    }

    public void cleanUp() {
        try {
            fop.close();
            oos.close();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
