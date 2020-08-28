package com.mud.game.messages;

import com.mud.game.structs.SkillCdInfo;

/**
 * 技能cd返回
 */
public class SkillCdMessage {
    private SkillCdInfo skill_cd;

    public SkillCdInfo getSkill_cd() {
        return skill_cd;
    }

    public void setSkill_cd(SkillCdInfo skill_cd) {
        this.skill_cd = skill_cd;
    }

    public SkillCdMessage(SkillCdInfo skill_cd) {

        this.skill_cd = skill_cd;
    }
}
