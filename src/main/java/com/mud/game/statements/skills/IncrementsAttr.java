package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BasePassiveSkillStatement;
import com.mud.game.statements.BaseStatement;
import com.mud.game.structs.SkillEffect;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IncrementsAttr extends BasePassiveSkillStatement {
    /*
    * 函数  increaments_attr 增加使用者的属性
    * 调用key： increaments_attr
    * 参数： (玩家属性，增加的数值，生效的位置)
    * */

    public IncrementsAttr(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        super(caller, target, skillObject, key, args);
    }

    @Override
    public void calculus() throws JSONException, JsonProcessingException {
        /*
        * 计算技能的效果
        * */
        CommonCharacter caller = getCaller();
        String[] args = getArgs();
        SkillObject skillObject = getSkillObject();
        int skillLevel = skillObject.getLevel();
        String attrKey = args[0];
        float scale = Float.parseFloat(args[1]);
        String position = args[2];
        Set<SkillEffect> effects = skillObject.getEffects();
        effects.removeIf(skillEffect -> skillEffect.getPosition().equals(position) && skillEffect.getAttrKey().equals(attrKey));
        effects.add(new SkillEffect(position, attrKey, (int)(skillLevel / scale)));
        skillObject.setEffects(effects);
        MongoMapper.skillObjectRepository.save(skillObject);
    }
}
