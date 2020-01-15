package com.mud.game.object.builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.object.manager.SkillObjectManager;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Skill;

public class CommonObjectBuilder {

    public static SkillObject buildSkill(String skillKey) throws JsonProcessingException {
        return SkillObjectManager.create(skillKey);
    }
}
