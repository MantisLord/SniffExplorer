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
public class Player extends Unit{
    
    private PlayerClass playerClass;
    private String name;
    
    private static final boolean ANONYMIZE_GUIDS=false;
    
    enum PlayerClass{
        DEATHKNIGHT,
        DRUID,
        HUNTER,
        MAGE,
        PALADIN,
        PRIEST,
        ROGUE,
        SHAMAN,
        WARLOCK,
        WARRIOR;
    }

    public Player() {
    }

    public Player(String name, String GUID) {
        super(GUID);
        this.name = name;
        
        // @todo: determine the class by GUID
    }

    public PlayerClass getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(PlayerClass playerClass) {
        this.playerClass = playerClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        if(!ANONYMIZE_GUIDS)
            return String.format("Player. Name= %s GUID= %s", name, getGUID());
        else
            return "Player{GUID=XXXXXXXXXXXXXXXXXX}";
    }
}
