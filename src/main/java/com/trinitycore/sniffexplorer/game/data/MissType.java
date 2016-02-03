package com.trinitycore.sniffexplorer.game.data;

/**
 * Created by chaouki on 03-02-16.
 */
public enum MissType {
    NONE("None"),
    MISS("Miss"),
    RESIST("Resist"),
    DODGE("Dodge"),
    PARRY("Parry"),
    BLOCK("Block"),
    EVADE("Evade"),
    IMMUNE1("Immune1"),
    IMMUNE2("Immune2"),
    DEFLECT("Deflect"),
    ABSORB("Absorb"),
    REFLECT("Reflect");

    private String name;
    MissType(String name) {
        this.name=name;
    }

    public static MissType getMissType(String name){
        for(MissType missType:MissType.values())
            if(missType.name.equals(name))
                return missType;

        throw new IllegalArgumentException("The name of this miss type is not recognized.");
    }

    @Override
    public String toString() {
        return this.name;
    }
}
