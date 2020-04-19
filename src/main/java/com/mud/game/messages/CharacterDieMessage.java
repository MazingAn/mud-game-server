package com.mud.game.messages;

import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.structs.SimpleCharacter;

import java.util.Map;

public class CharacterDieMessage {
    private SimpleCharacter char_die;

    public CharacterDieMessage() {
    }

    public CharacterDieMessage(CommonCharacter character) {
        this.char_die = new SimpleCharacter(character);
    }

    public SimpleCharacter getChar_die() {
        return char_die;
    }

    public void setChar_die(SimpleCharacter char_die) {
        this.char_die = char_die;
    }
}
