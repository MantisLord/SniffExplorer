import com.antistupid.wardbc.lazy.LazyDBC;
import com.antistupid.wardbc.lazy.WrapperDBC;
import com.antistupid.wardbc.lazy.rows.SpellData;
import com.antistupid.wardbc.lazy.rows.SpellRadius;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.TreeMap;

/**
 * Created by chaouki on 05-05-16.
 */
public class WarDBCTest {

    @Test
    public void warDbcTest(){
        LazyDBC lazy = new LazyDBC(Paths.get("F:\\TrinityCore\\bin\\dbc"));
//        lazy.dump(SpellData.class);

        TreeMap<Integer,SpellData> spellDataMap = lazy.map(SpellData.class);
//        int spellId = 32911;
        int spellId = 64145;
        SpellData spellData = spellDataMap.get(spellId);
        spellData.dump();
        int effectRadiusIndex1 = spellData.EffectRadiusIndex1;
        int effectRadiusIndex2 = spellData.EffectRadiusIndex2;
        int effectRadiusIndex3 = spellData.EffectRadiusIndex3;

        TreeMap<Integer,SpellRadius> spellRadiusMap = lazy.map(SpellRadius.class);
        int spellRadiusId = 28;
        spellRadiusMap.get(spellRadiusId).dump();
    }

    @Test
    public void testSpellWithDifferentRadius() {
        WrapperDBC wrapperDBC = new WrapperDBC("F:\\TrinityCore\\bin\\dbc");
        for(SpellData spellData:wrapperDBC.getSpellDataMap().values()){
            int radius1 = spellData.EffectRadiusIndex1;
            int radius2 = spellData.EffectRadiusIndex2;
            int radius3 = spellData.EffectRadiusIndex3;
            if(radius1!=0 && radius2!= 0 && radius1!=radius2)
                System.out.println(spellData.id);
        }

    }
}
