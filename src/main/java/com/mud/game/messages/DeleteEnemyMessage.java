package com.mud.game.messages;

import com.mud.game.structs.SimpleCharacter;

public class DeleteEnemyMessage {

    private SimpleCharacter delete_enemy_info;

    public DeleteEnemyMessage(SimpleCharacter delete_enemy_info) {
        this.delete_enemy_info = delete_enemy_info;
    }

    public SimpleCharacter getDelete_enemy_info() {
        return delete_enemy_info;
    }

    public void setDelete_enemy_info(SimpleCharacter delete_enemy_info) {
        this.delete_enemy_info = delete_enemy_info;
    }
}
