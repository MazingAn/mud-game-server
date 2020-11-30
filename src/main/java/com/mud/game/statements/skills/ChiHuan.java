package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.algorithm.AttackAlgorithm;
import com.mud.game.algorithm.HarmInfo;
import com.mud.game.combat.FighterManager;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import org.json.JSONException;

/**
 * 添加迟缓
 * <p>
 * 降低敌人的攻击速度和闪避
 * <p>
 * 降低攻速 不改变属性 在代码里完成
 */
public class ChiHuan extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public ChiHuan(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        super(caller, target, skillObject, key, args);
    }

    @Override
    public void attack() throws JSONException, JsonProcessingException {
        //基本参数
        CommonCharacter caller = getCaller();
        CommonCharacter target = getTarget();
        SkillObject skillObject = getSkillObject();
        //降低闪避-身法buffer
        int bDodge = Integer.parseInt(target.getCustomerAttr().get("dodge").get("value").toString());
        if (bDodge > 10) {
            bDodge = new Double(bDodge * 0.2).intValue();
            GameCharacterManager.addBuffer("呆若木鸡", 5, 0, 1, false,
                    "dodge", bDodge, target, skillObject, false, caller);
        } else if (bDodge > 1) {
            GameCharacterManager.addBuffer("呆若木鸡", 5, 0, 1, false,
                    "dodge", 1, target, skillObject, false, caller);
        }

    }
}
