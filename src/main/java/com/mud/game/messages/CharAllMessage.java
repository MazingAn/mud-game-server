package com.mud.game.messages;

import com.mud.game.structs.SimplePlayerCharacter;

import java.util.Set;

public class CharAllMessage {
    /*
    * @ 所有用户的用户信息
    * */
    private Set<SimplePlayerCharacter> char_all;

    public CharAllMessage(Set<SimplePlayerCharacter> char_all) {
        this.char_all = char_all;
    }

    public Set<SimplePlayerCharacter> getChar_all() {
        return char_all;
    }

    public void setChar_all(Set<SimplePlayerCharacter> char_all) {
        this.char_all = char_all;
    }
}
