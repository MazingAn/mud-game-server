package com.mud.game.messages;

import com.mud.game.structs.PlayerCharacterState;

public class PlayerCharacterStateMessage {
    private PlayerCharacterState state;
    private boolean is_normal;

    public PlayerCharacterStateMessage(PlayerCharacterState state) {
        this.state = state;
        this.is_normal = (state == PlayerCharacterState.STATE_NORMAL);
    }

    public PlayerCharacterState getState() {
        return state;
    }

    public void setState(PlayerCharacterState state) {
        this.state = state;
    }

    public boolean isIs_normal() {
        return is_normal;
    }

    public void setIs_normal(boolean is_normal) {
        this.is_normal = is_normal;
    }
}
