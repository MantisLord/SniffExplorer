package com.antistupid.wardbc.lazy.rows;

import com.antistupid.wardbc.lazy.LazyRowId;

public class SpellData extends LazyRowId {
    
    static public final String FILE = "Spell.dbc";

    public int category;                // 1
    public int dispel;                  // 2
    public int Mechanic;                // 3
    public int Attributes;              // 4
    public int AttributesEx;            // 5
    public int AttributesEx2;
    public int AttributesEx3;
    public int AttributesEx4;
    public int AttributesEx5;
    public int AttributesEx6;           // 10
    public int AttributesEx7;
    public int Stances;
    public int unk_320_2;
    public int StancesNot;
    public int unk_320_3;               // 15
    public int Targets;
    public int TargetCreatureType;
    public int requiresSpellFocus;
    public int FacingCasterFlags;
    public int CasterAuraState;         // 20
    public int TargetAuraState;
    public int CasterAuraStateNot;
    public int TargetAuraStateNot;
    public int casterAuraSpell;
    public int targetAuraSpell;         // 25
    public int excludeCasterAuraSpell;
    public int excludeTargetAuraSpell;
    public int CastingTimeIndex;
    public int RecoveryTime;
    public int CategoryRecoveryTime;    // 30
    public int InterruptFlags;
    public int AuraInterruptFlags;
    public int ChannelInterruptFlags;
    public int procFlags;
    public int procChance;              // 35
    public int procCharges;
    public int maxLevel;
    public int baseLevel;
    public int spellLevel;
    public int DurationIndex;           // 40
    public int powerType;
    public int manaCost;
    public int manaCostPerlevel;
    public int manaPerSecond;
    public int manaPerSecondPerLevel;   // 45
    public int rangeIndex;
    public float speed;

    public int modalNextSpell;
    public int StackAmount;
    public int Totem1;
    public int Totem2;
    public int Reagent1;
    public int Reagent2;
    public int Reagent3;
    public int Reagent4;
    public int Reagent5;
    public int Reagent6;
    public int Reagent7;
    public int Reagent8;
    public int ReagentCount1;
    public int ReagentCount2;
    public int ReagentCount3;
    public int ReagentCount4;
    public int ReagentCount5;
    public int ReagentCount6;
    public int ReagentCount7;
    public int ReagentCount8;

    public int EquippedItemClass;
    public int EquippedItemSubClassMask;
    public int EquippedItemInventoryTypeMask;
    public int Effect1;
    public int Effect2;

    public int Effect3;
    public int EffectDieSides1;
    public int EffectDieSides2;
    public int EffectDieSides3;
    public float EffectRealPointsPerLevel1;
    public float EffectRealPointsPerLevel2;
    public float EffectRealPointsPerLevel3;

    public int EffectBasePoints1;
    public int EffectBasePoints2;
    public int EffectBasePoints3;
    public int EffectMechanic1;
    public int EffectMechanic2;
    public int EffectMechanic3;

    public int EffectImplicitTargetA1;
    public int EffectImplicitTargetA2;
    public int EffectImplicitTargetA3;
    public int EffectImplicitTargetB1;
    public int EffectImplicitTargetB2;
    public int EffectImplicitTargetB3;
    public int EffectRadiusIndex1;
    public int EffectRadiusIndex2;
    public int EffectRadiusIndex3;

//    public String name;
//    public String rank;
//    public String desc;
////    public String tooltip;
//    public int id_rune_cost;
//    public int id_missile;
//    public int id_desc_var;
//    //public float extra_coeff;
//    public int id_scaling;
//    public int id_aura_opt;
//    public int id_aura_rest;
//    public int id_cast_req;
//    public int id_categories;
//    public int id_class_opts;
//    public int id_cooldowns;
//    public int id_equip_items;
//    public int id_interrupts;
//    public int id_levels;
//    public int id_reagents; // these swapped places
//    public int id_power; // need to check this one
//    public int id_shapeshift;
//    public int id_tgt_rest;
//    public int id_totems;
//    public int id_misc;
    

}
