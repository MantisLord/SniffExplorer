/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.core;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import tc.sniffexplorer.message.Message;

/**
 *
 * @author chaouki
 */
public class ViewerFile implements Viewer{
    
    private PrintWriter out;
    
    public ViewerFile(String fileName){
        try {
            out= new PrintWriter(fileName);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ViewerFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void show(Message message) {
        message.display(out);
        
    }
    
    @Override
    public void cleanup(){
        out.close();
    }
}
