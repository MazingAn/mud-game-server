package com.mud.game.statements.buffers;

import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BaseAttackSkillStatement;

/**
 * 增加一个buff到角色身上
 * */
public class AddBuffer extends BaseAttackSkillStatement {

    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public AddBuffer(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        super(caller, target, skillObject, key, args);
    }

    @Override
    public void attack(){
        CommonCharacter caller = getCaller();
        CommonCharacter target = getTarget();
        SkillObject skillObject = getSkillObject();
        String[] args = getArgs();
        String bufferName = args[0];
        String attrKey = args[1];
        float changedValue = Float.parseFloat(args[2]);
        float duration = Float.parseFloat(args[3]);
        int maxAdd = Integer.parseInt(args[4]);
        boolean goodBuffer = Integer.parseInt(args[5]) == 0;
        if(goodBuffer) target = caller;
        GameCharacterManager.addBuffer(bufferName, duration, 0, maxAdd, goodBuffer,
                attrKey, changedValue, target, skillObject);
    }
}
