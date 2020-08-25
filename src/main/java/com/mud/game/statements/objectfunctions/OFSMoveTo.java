package com.mud.game.statements.objectfunctions;

import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.NormalObjectObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.statements.BaseObjectFunctionStatement;

public class OFSMoveTo extends BaseObjectFunctionStatement {
    public OFSMoveTo(CommonCharacter caller, CommonCharacter target, NormalObjectObject normalObjectObject, String key, String[] args) {
        super(caller, target, normalObjectObject, key, args);
    }

    @Override
    public void execute() {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        String[] args = getArgs();
        String roomKey = args[0];
        PlayerCharacterManager.moveTo(caller, roomKey);
    }

}
