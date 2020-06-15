package com.mud.game.messages;

import com.mud.game.structs.SimpleSkill;

import java.util.Map;

public class PlayerCharacterSkills {

    private Map<String, Map<String, SimpleSkill>> skills;

    public PlayerCharacterSkills(Map<String, Map<String, SimpleSkill>> skills) {
        this.skills = skills;
    }

    public Map<String, Map<String, SimpleSkill>> getSkills() {
        return skills;
    }

    public void setSkills(Map<String, Map<String, SimpleSkill>> skills) {
        this.skills = skills;
    }
}
