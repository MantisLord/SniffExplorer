package com.trinitycore.sniffexplorer.game.data;

import com.trinitycore.sniffexplorer.game.entities.*;

/**
 * Utility class/enum.
 *
 * Created by chaouki on 02-02-16.
 */
public enum UnitType {
    CREATURE("Creature", Creature.class),
    PLAYER("Player", Player.class),
    VEHICLE("Vehicle", Vehicle.class),
    PET("Pet", Pet.class),
    GAME_OBJECT("GameObject", GameObject.class),
    ITEM("Item", Item.class),
    TRANSPORT("Transport", Transport.class),
    DYNAMIC_OBJECT("DynamicObject", DynamicObject.class);

    private Class<Unit> unitClass;
    private String name;
    UnitType(String name, Class unitClass) {
        this.name=name;
        this.unitClass=unitClass;
    }

    public static UnitType getUnitType(String name){
        for(UnitType unitType:UnitType.values())
            if(unitType.name.equals(name))
                return unitType;

        throw new IllegalArgumentException("The name of this unit type is not recognized.");
    }

    public static Unit getUnitInstance(String name){
        try {
            return getUnitType(name).unitClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("A problem occurred with the instantiation of a Unit object.", e);
        }
    }

    public static Unit getUnitInstance(UnitType unitType){
        try {
            return unitType.unitClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("A problem occurred with the instantiation of a Unit object.", e);
        }
    }
}
