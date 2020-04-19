package com.mud.game.messages;

import com.mud.game.structs.CombatInfo;

public class CombatInfoMessage {
    private CombatInfo combat_info;

    public CombatInfoMessage() {
    }

    public CombatInfoMessage(CombatInfo combat_info) {
        this.combat_info = combat_info;
    }

    public CombatInfo getCombat_info() {
        return combat_info;
    }

    public void setCombat_info(CombatInfo combat_info) {
        this.combat_info = combat_info;
    }
}
