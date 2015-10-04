/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.core;

import tc.sniffexplorer.message.Message;

/**
 *
 * @author chaouki
 */
public abstract interface Viewer {
    
    public abstract void show(Message message);
    public abstract void cleanup();
    
}
