package com.mud.game.structs;

import com.mud.game.object.supertypeclass.CommonCharacter;

import java.util.List;

public class CombatInfo {
    private List<CharacterCombatStatus> characters;
    private String desc;
    private float timeout;

    public CombatInfo() {
    }

    public CombatInfo(List<CharacterCombatStatus> characters, String desc, float timeout) {
        this.characters = characters;
        this.desc = desc;
        this.timeout = timeout;
    }

    public List<CharacterCombatStatus> getCharacters() {
        return characters;
    }

    public void setCharacters(List<CharacterCombatStatus> characters) {
        this.characters = characters;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public float getTimeout() {
        return timeout;
    }

    public void setTimeout(float timeout) {
        this.timeout = timeout;
    }
}
