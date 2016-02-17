package com.trinitycore.sniffexplorer.game.entities;

/**
 * Created by chaouki on 17-02-16.
 */
public class DynamicObject extends Unit {

    public DynamicObject() {
    }

    public DynamicObject(String GUID) {
        super(GUID);
    }


    @Override
    public String toString() {
        return "DynamicObject{GUID="+GUID+"}";
    }
}
