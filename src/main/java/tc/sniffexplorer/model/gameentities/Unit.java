/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.model.gameentities;

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
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Unit other = (Unit) obj;
        if (!Objects.equals(this.GUID, other.GUID)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Unit{" + "GUID=" + GUID + '}';
    }
    
    
}
