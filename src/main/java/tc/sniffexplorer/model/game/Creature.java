/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.model.game;

/**
 *
 * @author chaouki
 */
public class Creature extends Unit {
    private Integer entry;
    private Integer lowGUID;

    public Creature() {
    }

    public Creature(Integer entry, Integer lowGUID, String GUID) {
        super(GUID);
        this.entry = entry;
        this.lowGUID = lowGUID;
    }
    
    public Integer getEntry() {
        return entry;
    }

    public void setEntry(Integer entry) {
        this.entry = entry;
    }

    public Integer getLowGUID() {
        return lowGUID;
    }

    public void setLowGUID(Integer lowGUID) {
        this.lowGUID = lowGUID;
    }
    
    
}
