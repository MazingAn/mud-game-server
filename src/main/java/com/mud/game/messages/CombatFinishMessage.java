package com.mud.game.messages;

import java.util.HashMap;
import java.util.Map;

public class CombatFinishMessage {

    private Map<String, Boolean> combat_finish;

    public CombatFinishMessage() {
    }

    public CombatFinishMessage(boolean win) {
        this.combat_finish = new HashMap<>();
        this.combat_finish.put("win", win);
    }

    public Map<String, Boolean> getCombat_finish() {
        return combat_finish;
    }

    public void setCombat_finish(Map<String, Boolean> combat_finish) {
        this.combat_finish = combat_finish;
    }
}
