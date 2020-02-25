package com.mud.game.messages;

import com.mud.game.structs.SimpleSkill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PositionSkillsMessage {
    private Map<String, Set<String>> position_skills;

    public PositionSkillsMessage(Set<String> usedSkills, Set<String> canReplacedSkills) {
        this.position_skills = new HashMap<>();
        this.position_skills.put("used", usedSkills);
        this.position_skills.put("can_replace", canReplacedSkills);
    }

    public Map<String, Set<String>> getPosition_skills() {
        return position_skills;
    }

    public void setPosition_skills(Map<String, Set<String>> position_skills) {
        this.position_skills = position_skills;
    }
}
