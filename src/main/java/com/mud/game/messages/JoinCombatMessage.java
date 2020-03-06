package com.mud.game.messages;

public class JoinCombatMessage {
    private boolean joined_combat;

    public JoinCombatMessage() {
        this.joined_combat = true;
    }

    public boolean isJoined_combat() {
        return joined_combat;
    }

    public void setJoined_combat(boolean joined_combat) {
        this.joined_combat = joined_combat;
    }
}
