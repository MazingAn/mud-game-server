package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.combat.FighterManager;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import com.mud.game.utils.StateConstants;
import org.json.JSONException;

import static com.mud.game.utils.StateConstants.CHECK_XUEHAIWUBIAN_STATE;

/**
 * 血魔刀法_敕
 * <p>
 * 以自身30%血量为代价造成对方30%血量的伤害
 */
public class XueMoDaofaChi extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public XueMoDaofaChi(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        super(caller, target, skillObject, key, args);
    }

    @Override
    public void attack() throws JSONException, JsonProcessingException {
        //基本参数
        CommonCharacter caller = getCaller();
        CommonCharacter target = getTarget();
        SkillObject skillObject = getSkillObject();
        //造成攻击
        double duration = 1;
        //判断是否在 血海无边的状态下
        if (StateConstants.checkState(caller, CHECK_XUEHAIWUBIAN_STATE)) {
            duration = 1.5;
        }
        FighterManager.autoCombatAttack(caller, target, skillObject, new Double(target.getHp() * 0.3 * duration).floatValue());
        //自身消耗血量
        caller = GameCharacterManager.getCharacterObject(caller.getId());
        GameCharacterManager.changeStatus(caller, "hp", new Double(caller.getHp() * -0.3).intValue(), caller);

    }
}
