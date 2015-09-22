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
public class Creature extends Unit {
    
    private String entry;

    public Creature() {
    }

    public Creature(String entry, String GUID) {
        super(GUID);
        this.entry = entry;
    }
    
    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }
    
}
