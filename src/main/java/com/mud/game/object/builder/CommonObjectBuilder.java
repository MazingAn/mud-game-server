package com.mud.game.object.builder;

import com.mud.game.object.manager.SkillObjectManager;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Skill;

public class CommonObjectBuilder {

    public static SkillObject buildSkill(String skillKey){
        return SkillObjectManager.create(skillKey);
    }
}
