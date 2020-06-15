package com.mud.game.messages;

import com.mud.game.structs.CombatCommand;

import java.util.List;

/**
 * 返回玩家战斗中可执行的技能
 * */
public class CombatCommandsMessage {

    private List<CombatCommand> combat_commands;

    public CombatCommandsMessage(List<CombatCommand> combat_commands) {
        this.combat_commands = combat_commands;
    }

    public List<CombatCommand> getCombat_commands() {
        return combat_commands;
    }

    public void setCombat_commands(List<CombatCommand> combat_commands) {
        this.combat_commands = combat_commands;
    }
}
