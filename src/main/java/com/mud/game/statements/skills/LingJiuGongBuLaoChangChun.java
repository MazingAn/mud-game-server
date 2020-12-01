package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import com.mud.game.utils.StateConstants;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * 受攻击之后几率回血10秒
 */
public class LingJiuGongBuLaoChangChun extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public LingJiuGongBuLaoChangChun(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        super(caller, target, skillObject, key, args);
    }

    @Override
    public void attack() throws JSONException, JsonProcessingException {
        //基本参数
        CommonCharacter caller = getCaller();
        CommonCharacter target = getTarget();
        SkillObject skillObject = getSkillObject();
        String key = getKey();
        String[] args = getArgs();


        List<String> STATE = new ArrayList<String>() {
            {
                add("不老长春");
            }
        };
        if (StateConstants.checkState(caller, STATE)) {
            //增加一个不老长春buffer
            GameCharacterManager.addBuffer("不老长春", 10, 0, 1, true,
                    null, 2, target, skillObject, true, caller);
        }
    }
}
