package com.mud.game.object.manager;

import com.mud.game.handler.SkillFunctionHandler;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Skill;
import com.mud.game.worldrun.db.mappings.MongoMapper;

import java.util.HashMap;
import java.util.Map;

public class SkillObjectManager {
    public static SkillObject create(String skillTemplateKey){
        SkillObject skillObject = new SkillObject();
        Skill template = DbMapper.skillRepository.findSkillByDataKey(skillTemplateKey);
        skillObject.setName(template.getName());
        skillObject.setDataKey(template.getDataKey());
        skillObject.setActionTime(template.getActionTime());
        skillObject.setCd(template.getCd());
        skillObject.setEffects(new HashMap<>());
        return skillObject;
    }

    private static void computeEffects(CommonCharacter caller, CommonCharacter target, SkillObject skillObject){
        SkillFunctionHandler.execute(caller, target, skillObject);
    }
}
