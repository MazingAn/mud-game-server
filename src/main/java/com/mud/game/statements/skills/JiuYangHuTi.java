package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import org.json.JSONException;

/**
 * 增加防御、抗暴、减伤
 * <p>
 * 九阳护体
 */
public class JiuYangHuTi extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public JiuYangHuTi(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        super(caller, target, skillObject, key, args);
    }

    @Override
    public void attack() throws JSONException, JsonProcessingException {
        //基本参数
        CommonCharacter caller = getCaller();
        CommonCharacter target = getTarget();
        SkillObject skillObject = getSkillObject();
        String key = getKey();
        beforeAttack();
        double value = 0.2;
        //增加一个防御buffer
        int bDefence = Integer.parseInt(caller.getCustomerAttr().get("defence").get("value").toString());
        GameCharacterManager.addBuffer("九阳护体", 5, 0, 1, true,
                "defence", new Double(bDefence * value).intValue(), target, skillObject, false, caller);

    }
}
