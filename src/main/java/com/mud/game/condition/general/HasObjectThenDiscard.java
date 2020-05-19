package com.mud.game.condition.general;

import com.mud.game.condition.BaseCondition;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;

/**
 * 检查是否拥有某种物品
 * 检查通过之后销毁物品
 *
 * 使用： has_object_then_discard(“dahuandao”) 玩家必须拥有key为"dahuandao"的物品，检查完毕之后销毁
 *
 * */
public class HasObjectThenDiscard extends BaseCondition {
    /**
     * 构造函数
     * <p>
     * 构造函数用于捕获参数
     *
     * @param key             检测类的健
     * @param playerCharacter 检测的主体对象（要被检测的玩家）
     * @param args            检测函数参数列表（字符串列表）
     */
    public HasObjectThenDiscard(String key, PlayerCharacter playerCharacter, String[] args) {
        super(key, playerCharacter, args);
    }

    @Override
    public boolean match() throws NoSuchFieldException, IllegalAccessException {
        PlayerCharacter playerCharacter = getPlayerCharacter();
        String[] args = getArgs();
        String objectDataKey = args[0];
        return PlayerCharacterManager.discardObject(playerCharacter, objectDataKey, 1);
    }
}
