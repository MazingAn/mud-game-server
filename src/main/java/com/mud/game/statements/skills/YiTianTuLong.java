package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import org.json.JSONException;

/**
 * 倚天屠龙
 * <p>
 * 增加暴击率和暴击伤害
 */
public class YiTianTuLong extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public YiTianTuLong(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        super(caller, target, skillObject, key, args);
    }

    @Override
    public void attack() throws JSONException, JsonProcessingException {
        //基本参数
        CommonCharacter caller = getCaller();
        CommonCharacter target = getTarget();
        SkillObject skillObject = getSkillObject();
        String key = getKey();
        //增加暴击
        GameCharacterManager.addBuffer("倚天屠龙", 5, 0, 1, true,
                "crit_rate", 0.2, target, skillObject, false, caller);
        //增加爆伤
        GameCharacterManager.addBuffer("倚天屠龙", 5, 0, 1, true,
                "crit_damage", 0.2, target, skillObject, false, caller);
    }
}
