/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.gameentities;

/**
 *
 * @author chaouki
 */
public class GameObject extends Unit implements IdentifiableByEntry {
    
    private Integer entry;

    public GameObject() {
    }

    public GameObject(Integer entry, String GUID) {
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
        return "GameObject{" + "entry=" + entry + ", GUID="+GUID+"}";
    }
}
