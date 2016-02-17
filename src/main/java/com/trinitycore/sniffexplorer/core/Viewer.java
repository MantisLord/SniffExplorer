/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinitycore.sniffexplorer.core;

import com.trinitycore.sniffexplorer.message.Message;

/**
 *
 * @author chaouki
 */
public interface Viewer {
    
    void show(Message message);
    void cleanup();
    
}
