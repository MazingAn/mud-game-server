package com.mud.game.messages;

import com.mud.game.structs.SimpleCharacter;

import java.util.List;

public class SimpleCharacterMessage {
    private List<SimpleCharacter> simpleCharacter_list;

    public SimpleCharacterMessage(List<SimpleCharacter> simpleCharacter_list) {
        this.simpleCharacter_list = simpleCharacter_list;
    }

    public List<SimpleCharacter> getSimpleCharacter_list() {
        return simpleCharacter_list;
    }

    public void setSimpleCharacter_list(List<SimpleCharacter> simpleCharacter_list) {
        this.simpleCharacter_list = simpleCharacter_list;
    }
}
