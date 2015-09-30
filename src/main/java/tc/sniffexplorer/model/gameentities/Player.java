/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.model.gameentities;

/**
 *
 * @author chaouki
 */
public class Player extends Unit{
    
    private PlayerClass playerClass;
    private String name;
    
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
        return "Player{GUI="+GUID+"}";
    }
}
