/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinitycore.sniffexplorer.game.entities;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author chaouki
 */
public abstract class Unit implements Serializable {
    protected String GUID;
//    protected Integer lowGUID;

    public Unit() {
    }

    public Unit(String GUID) {
        this.GUID = GUID;
    }

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Unit unit = (Unit) o;

        return GUID.equals(unit.GUID);

    }

    @Override
    public int hashCode() {
        return GUID.hashCode();
    }

    @Override
    public String toString() {
        return "Unit{" + "GUID=" + GUID + '}';
    }
    
    
}
