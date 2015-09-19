/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.model.entities;

/**
 *
 * @author chaouki
 */
public class Vehicule extends Unit{
    
    private String entry;

    public Vehicule() {
    }

    public Vehicule(String entry, String GUID) {
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
