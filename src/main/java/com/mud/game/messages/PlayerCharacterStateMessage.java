package com.mud.game.messages;

import com.mud.game.structs.CharacterState;

public class PlayerCharacterStateMessage {
    /*
    * 当玩家的状态发生改变后这条信息被发送
    * state 玩家当前状态
    * is_normal 玩家当前是不是挂机状态
    * */

    private CharacterState state;
    private boolean is_normal;

    public PlayerCharacterStateMessage(CharacterState state) {
        this.state = state;
        this.is_normal = (state == CharacterState.STATE_NORMAL);
    }

    public CharacterState getState() {
        return state;
    }

    public void setState(CharacterState state) {
        this.state = state;
    }

    public boolean isIs_normal() {
        return is_normal;
    }

    public void setIs_normal(boolean is_normal) {
        this.is_normal = is_normal;
    }
}
