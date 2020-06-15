package com.mud.game.messages;

import com.mud.game.structs.SkillCastInfo;

public class SkillCastMessage {
    private SkillCastInfo skill_cast;

    public SkillCastMessage(SkillCastInfo skill_cast) {
        this.skill_cast = skill_cast;
    }

    public SkillCastInfo getSkill_cast() {
        return skill_cast;
    }

    public void setSkill_cast(SkillCastInfo skill_cast) {
        this.skill_cast = skill_cast;
    }
}
