/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinitycore.sniffexplorer.message;

/**
 *
 * @author chaouki
 */
public enum OpCode {
    // SMSG
    SMSG_SPELL_START,
    SMSG_SPELL_GO,
    SMSG_SPELL_PERIODIC_AURA_LOG,
    SMSG_MOVE_UPDATE,
    SMSG_ON_MONSTER_MOVE,
    SMSG_UPDATE_OBJECT,
    SMSG_EMOTE,
    SMSG_AURA_UPDATE,

    SMSG_FORCE_RUN_SPEED_CHANGE,
    SMSG_FORCE_RUN_BACK_SPEED_CHANGE,
    SMSG_FORCE_SWIM_SPEED_CHANGE,
    SMSG_FORCE_FLIGHT_SPEED_CHANGE,
    SMSG_FORCE_WALK_SPEED_CHANGE,
    SMSG_FORCE_FLIGHT_BACK_SPEED_CHANGE,
    SMSG_FORCE_X_SPEED_CHANGE;
}
