package com.mud.game.condition.general;

import com.mud.game.condition.BaseCondition;
import com.mud.game.object.typeclass.PlayerCharacter;

public class CheckRoomStep extends BaseCondition {
    /**
     * 构造函数
     * <p>
     * 构造函数用于捕获参数
     *
     * @param key             检测类的健
     * @param playerCharacter 检测的主体对象（要被检测的玩家）
     * @param args            检测函数参数列表（字符串列表）
     */
    public CheckRoomStep(String key, PlayerCharacter playerCharacter, String[] args) {
        super(key, playerCharacter, args);
    }

    /**
     * check_room_step
     * 检查当前房间玩家所在的高度
     * check_room_step(0) 玩家当前房间内的高度必须为0
     * */
    @Override
    public boolean match() throws NoSuchFieldException, IllegalAccessException {
        PlayerCharacter playerCharacter = getPlayerCharacter();
        int roomStep = Integer.parseInt(getArgs()[0]);
        return roomStep == playerCharacter.getRoomStep();
    }
}
