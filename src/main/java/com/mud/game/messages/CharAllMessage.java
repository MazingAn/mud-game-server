package com.mud.game.messages;

import com.mud.game.structs.SimpleCharacter;

import java.util.Set;

public class CharAllMessage {
    /*
    * @ 所有用户的用户信息
    * */
    private Set<SimpleCharacter> char_all;

    public CharAllMessage(Set<SimpleCharacter> char_all) {
        this.char_all = char_all;
    }

    public Set<SimpleCharacter> getChar_all() {
        return char_all;
    }

    public void setChar_all(Set<SimpleCharacter> char_all) {
        this.char_all = char_all;
    }
}
