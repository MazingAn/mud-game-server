package com.mud.game.condition.general;

import com.mud.game.condition.BaseCondition;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;

/**
 *
 * 玩家必须没有此物品
 * <pre>
 *     录入形式：no_object(“dahuandao”)
 *     返回结果：玩家是否未拥有key为dahuandao的物品
 * </pre>
 * */
public class NoObject extends BaseCondition {
    /**
     * 构造函数
     * <p>
     * 构造函数用于捕获参数
     *
     * @param key             检测类的健
     * @param playerCharacter 检测的主体对象（要被检测的玩家）
     * @param args            检测函数参数列表（字符串列表）
     */
    public NoObject(String key, PlayerCharacter playerCharacter, String[] args) {
        super(key, playerCharacter, args);
    }

    @Override
    public boolean match() throws NoSuchFieldException, IllegalAccessException {
        String[] args = getArgs();
        String objectDataKey = args[0];
        return !PlayerCharacterManager.hasObject(getPlayerCharacter(), objectDataKey, 1);
    }
}
