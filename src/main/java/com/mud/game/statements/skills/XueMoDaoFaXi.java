package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import com.mud.game.utils.StateConstants;
import org.json.JSONException;

import static com.mud.game.utils.StateConstants.CHECK_XUEHAIWUBIAN_STATE;
import static com.mud.game.utils.StateConstants.CHECK_XUEMODAOFAXI_STATE;

/**
 * 增加吸血10%，持续8秒
 */
public class XueMoDaoFaXi extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public XueMoDaoFaXi(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
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
        //持续时间
        float duration = Float.parseFloat(args[0]);
        //判断是否在 血海无边的状态下
        if (StateConstants.checkState(caller, CHECK_XUEHAIWUBIAN_STATE)) {
            duration = new Double(duration * 1.5).floatValue();
        }
        //增加一个血魔刀法—吸buffer
        GameCharacterManager.addBuffer(CHECK_XUEMODAOFAXI_STATE, duration, 0, 1, false,
                null, null, target, skillObject, false, caller);
    }
}
