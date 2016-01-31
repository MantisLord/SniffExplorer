/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinitycore.sniffexplorer.gameentities;

/**
 *
 * @author chaouki
 */
public class Pet extends Unit{

    public Pet() {
    }

    public Pet(String GUID) {
        super(GUID);
    }
    
    @Override
    public String toString() {
        return "Pet{GUID="+GUID+"}";
    }
}
