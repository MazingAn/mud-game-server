package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.combat.CombatSense;
import com.mud.game.handler.CombatHandler;
import com.mud.game.handler.NpcCombatHandler;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import com.mud.game.structs.AttackState;
import org.json.JSONException;

/**
 * 添加状态  群攻，命中后无法闪避、招架
 */
public class LingJiuGongTianNvSanHua extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public LingJiuGongTianNvSanHua(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        super(caller, target, skillObject, key, args);
    }

    @Override
    public void attack() throws JSONException, JsonProcessingException {
        CombatSense sense = null;
        if (getCaller() instanceof WorldNpcObject) {
            sense = NpcCombatHandler.getNpcCombatSense(getCaller().getId(), getTarget().getId());
        } else {
            sense = CombatHandler.getCombatSense(getCaller().getId());
        }
        //增加一个忙乱buffer
        for (CommonCharacter character : sense.getBlueTeam()) {
            GameCharacterManager.addBuffer("无法闪避、招架", 10, 0, 1, false,
                    "attackState", AttackState.STATE_NOTEVADEPARRY, character, getSkillObject(), false, getCaller());
        }

    }
}
