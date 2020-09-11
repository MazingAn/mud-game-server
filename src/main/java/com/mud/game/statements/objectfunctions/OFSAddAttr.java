package com.mud.game.statements.objectfunctions;

import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.NormalObjectObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.statements.BaseObjectFunctionStatement;
import com.mud.game.structs.PlayerCharacterStatus;

/**
 * 属性加成
 */
public class OFSAddAttr extends BaseObjectFunctionStatement {


    public OFSAddAttr(CommonCharacter caller, CommonCharacter target, NormalObjectObject normalObjectObject, String key, String[] args) {
        super(caller, target, normalObjectObject, key, args);
    }

    @Override
    public void execute() {
        PlayerCharacter character = (PlayerCharacter) getCaller();
        String[] args = getArgs();
        String attr = args[0];
        String value = args[1];
        //增加后天属性
        character = PlayerCharacterManager.addAffterAttr(character, attr, Integer.parseInt(value.trim()));
        character.msg(new PlayerCharacterStatus(character));

    }
}
