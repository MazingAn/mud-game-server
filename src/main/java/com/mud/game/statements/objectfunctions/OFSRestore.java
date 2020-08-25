package com.mud.game.statements.objectfunctions;

import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.NormalObjectObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.statements.BaseObjectFunctionStatement;
import com.mud.game.worldrun.db.mappings.MongoMapper;

/**
 * 恢复
 */
public class OFSRestore extends BaseObjectFunctionStatement {
    public OFSRestore(CommonCharacter caller, CommonCharacter target, NormalObjectObject normalObjectObject, String key, String[] args) {
        super(caller, target, normalObjectObject, key, args);
    }

    @Override
    public void execute() {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        String[] args = getArgs();
        String attr = args[0];
        String value = args[1];
        //获取属性
        int maxHp = caller.getMax_hp();
        int maxMp = caller.getMax_mp();
        int hp = caller.getHp();
        int mp = caller.getMp();
        //恢复分为百分比和固定恢复，小于一对应百分比否则则固定
        Double valueDouble = Double.parseDouble(value);
        if (valueDouble < 1) {
            if ("hp".equals(attr.toLowerCase())) {
                caller.setHp((int) ((maxHp * valueDouble) + hp) > maxHp ? maxHp : (int) (maxHp * valueDouble) + hp);
            } else if ("mp".equals(attr.toLowerCase())) {
                caller.setHp((int) ((maxMp * valueDouble) + mp) > maxMp ? maxMp : (int) (maxMp * valueDouble) + mp);
            }
        } else {
            if ("hp".equals(attr.toLowerCase())) {
                caller.setHp(valueDouble + hp > maxHp ? maxHp : (int) (valueDouble + hp));
            } else if ("mp".equals(attr.toLowerCase())) {
                caller.setHp(valueDouble + mp > maxMp ? maxMp : (int) (valueDouble + mp));
            }
        }
        MongoMapper.playerCharacterRepository.save(caller);
    }
}
