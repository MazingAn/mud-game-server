package com.mud.game.messages;

import java.util.HashMap;
import java.util.Map;

public class CombatFinishMessage {

    private Map<String, String> combat_finish;

    public CombatFinishMessage() {
    }

    public CombatFinishMessage(String status) {
        this.combat_finish = new HashMap<>();
        this.combat_finish.put("win", status);
    }

    public Map<String, String> getCombat_finish() {
        return combat_finish;
    }

    public void setCombat_finish(Map<String, String> combat_finish) {
        this.combat_finish = combat_finish;
    }
}
