package com.antistupid.wardbc.lazy;

import com.antistupid.wardbc.lazy.rows.SpellData;
import com.antistupid.wardbc.lazy.rows.SpellRadius;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

/**
 * Created by chaouki on 05-05-16.
 */
public class WrapperDBC {

    private LazyDBC lazy;
    private Map<Integer,SpellData> spellDataMap;
    private Map<Integer,SpellRadius> spellRadiusMap;

    public WrapperDBC(String dbcFolderPath) {
        lazy = new LazyDBC(Paths.get(dbcFolderPath));
        spellDataMap = lazy.map(SpellData.class);
        spellRadiusMap = lazy.map(SpellRadius.class);
    }

    public Map<Integer, SpellData> getSpellDataMap() {
        return Collections.unmodifiableMap(spellDataMap);
    }

    public Map<Integer, SpellRadius> getSpellRadiusMap() {
        return Collections.unmodifiableMap(spellRadiusMap);
    }

    /**
     * returns the radius of each spell effect of the spell specified its spellId
     * @param spellId spell id of the spell
     * @return a float array: [Effect1Radius, Effect2Radius, Effect3Radius] if the spell exist. null otherwise
     */
    public float[] getSpellRadius(Integer spellId){
        SpellData spellData = spellDataMap.get(spellId);
        if(spellData==null)
            return null;
        else{
            int effectRadiusIndex1 = spellData.EffectRadiusIndex1;
            int effectRadiusIndex2 = spellData.EffectRadiusIndex2;
            int effectRadiusIndex3 = spellData.EffectRadiusIndex3;

            float radiusEffect1 = effectRadiusIndex1==0 ? 0 : spellRadiusMap.get(effectRadiusIndex1).radius;
            float radiusEffect2 = effectRadiusIndex2==0 ? 0 : spellRadiusMap.get(effectRadiusIndex2).radius;
            float radiusEffect3 = effectRadiusIndex3==0 ? 0 : spellRadiusMap.get(effectRadiusIndex3).radius;
            return new float[]{radiusEffect1, radiusEffect2, radiusEffect3};
        }
    }

    public int[] getSpellImplicitTargets(Integer spellId){
        SpellData spellData = spellDataMap.get(spellId);
        if(spellData==null)
            return null;
        else{
            if(spellData.EffectRadiusIndex1 !=0)
                return new int[]{spellData.EffectImplicitTargetA1, spellData.EffectImplicitTargetB1};
            if(spellData.EffectRadiusIndex2 !=0)
                return new int[]{spellData.EffectImplicitTargetA2, spellData.EffectImplicitTargetB2};
            if(spellData.EffectRadiusIndex3 !=0)
                return new int[]{spellData.EffectImplicitTargetA3, spellData.EffectImplicitTargetB3};

            return null;
        }
    }

    public float highestSpellRadius(Integer spellId){
        float[] spellRadius = getSpellRadius(spellId);
        if(spellRadius==null)
            return -1;
        float highest=spellRadius[0];

        if(spellRadius[1]>highest)
            highest=spellRadius[1];

        if(spellRadius[2]>highest)
            highest=spellRadius[2];

        return highest;
    }


}
