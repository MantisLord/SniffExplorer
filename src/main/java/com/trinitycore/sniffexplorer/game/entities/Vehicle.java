/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinitycore.sniffexplorer.game.entities;

/**
 *
 * @author chaouki
 */
public class Vehicle extends Unit implements IdentifiableByEntry{
    
    private Integer entry;

    public Vehicle() {
    }

    public Vehicle(Integer entry, String GUID) {
        super(GUID);
        this.entry = entry;
    }

    @Override
    public Integer getEntry() {
        return entry;
    }

    public void setEntry(Integer entry) {
        this.entry = entry;
    }

    @Override
    public String toString() {
        return "Vehicle{" + "entry=" + entry + ", GUID="+GUID+"}";
    }
    
    
}
