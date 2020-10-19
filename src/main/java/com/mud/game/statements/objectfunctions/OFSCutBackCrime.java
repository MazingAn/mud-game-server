package com.mud.game.statements.objectfunctions;

import com.mud.game.messages.ToastMessage;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.NormalObjectObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.statements.BaseObjectFunctionStatement;
import com.mud.game.worldrun.db.mappings.MongoMapper;

import static com.mud.game.constant.PostConstructConstant.CUT_BACKCRIME_ATTACK;
import static com.mud.game.constant.PostConstructConstant.CUT_BACKCRIME_ZERO_OBJECT;

/**
 * 减少犯罪值
 */
public class OFSCutBackCrime extends BaseObjectFunctionStatement {
    public OFSCutBackCrime(CommonCharacter caller, CommonCharacter target, NormalObjectObject normalObjectObject, String key, String[] args) {
        super(caller, target, normalObjectObject, key, args);
    }

    @Override
    public void execute() {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        String[] args = getArgs();
        int value = Integer.parseInt(args[0]);
        if (caller.getCrimeValue() > 0) {
            int newCrimeValue = caller.getCrimeValue() - value > 0 ? caller.getCrimeValue() - value : 0;
            int cutValue = caller.getCrimeValue() - newCrimeValue;
            caller.setCrimeValue(newCrimeValue);
            MongoMapper.playerCharacterRepository.save(caller);
            caller.msg(new ToastMessage(String.format(CUT_BACKCRIME_ATTACK, cutValue)));
            PlayerCharacterManager.showStatus(caller);
        } else {
            caller.msg(new ToastMessage(CUT_BACKCRIME_ZERO_OBJECT));
        }

    }
}
