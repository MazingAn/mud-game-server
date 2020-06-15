package com.mud.game.condition.general;

import com.mud.game.condition.BaseCondition;
import com.mud.game.object.typeclass.PlayerCharacter;

public class NeverUnlock extends BaseCondition {

    /**
     * 构造函数
     * <p>
     * 构造函数用于捕获参数
     *
     * @param key             检测类的健
     * @param playerCharacter 检测的主体对象（要被检测的玩家）
     * @param args            检测函数参数列表（字符串列表）
     */
    public NeverUnlock(String key, PlayerCharacter playerCharacter, String[] args) {
        super(key, playerCharacter, args);
    }

    /**
     * 无需判断，永远返回false
     * */
    @Override
    public boolean match() throws NoSuchFieldException, IllegalAccessException {
        return false;
    }
}
