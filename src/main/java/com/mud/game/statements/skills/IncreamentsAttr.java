package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BaseStatement;
import org.json.JSONException;

public class IncreamentsAttr extends BaseStatement {
    /*
    * 函数  increaments_attr 增加使用者的属性
    * 调用key： increaments_attr
    * 参数： (玩家属性，增加的数值，生效的位置)
    * */

    public IncreamentsAttr(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        super(caller, target, skillObject, key, args);
    }

    @Override
    public void execute() throws JSONException, JsonProcessingException {
        CommonCharacter caller = getCaller();
        String[] args = getArgs();
        SkillObject skillObject = getSkillObject();
        int skillLevel = skillObject.getLevel();
        String attrKey = args[0];
        float value = Float.parseFloat(args[1]);
        GameCharacterManager.changeStatus(caller, attrKey, value);
    }
}
