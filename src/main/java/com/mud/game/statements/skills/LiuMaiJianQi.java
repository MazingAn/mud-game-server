package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import org.json.JSONException;

/**
 * 随机造成眩晕&忙乱&回血&追魂&破甲
 */
public class LiuMaiJianQi extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public LiuMaiJianQi(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
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
        int duration = 5;
        //生成1~5 随机数
        int num = (int) (Math.random() * 5 + 1);
        caller = GameCharacterManager.getCharacterObject(caller.getId());
        target = GameCharacterManager.getCharacterObject(target.getId());
        switch (num) {
            case 1:
                //眩晕
                GameCharacterManager.addBuffer("眩晕", duration, 0, 1, false,
                        null, null, target, skillObject, false, caller);
                break;
            case 2:
                //忙乱
                GameCharacterManager.addBuffer("忙乱", duration, 0, 1, false,
                        "canCombat", false, target, skillObject, false, caller);
                break;
            case 3:
                //回血
                new AddHpPersent(caller, caller, skillObject, key, getArgs());
                break;
            case 4:
                //追魂
                GameCharacterManager.addBuffer("追魂", duration, 0, 1, false,
                        null, null, target, skillObject, false, caller);
                break;
            case 5:
                //破甲
                new PoJia(caller, caller, skillObject, key, getArgs());
                break;
        }

    }
}
