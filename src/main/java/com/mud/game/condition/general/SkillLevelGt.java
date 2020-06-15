package com.mud.game.condition.general;

import com.mud.game.condition.BaseCondition;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;

/**
 * 玩家的等级必须大于某个数字
 * */
public class SkillLevelGt extends BaseCondition {
    /**
     * 构造函数
     * <p>
     * 构造函数用于捕获参数
     *
     * @param key             检测类的健
     * @param playerCharacter 检测的主体对象（要被检测的玩家）
     * @param args            检测函数参数列表（字符串列表）
     */
    public SkillLevelGt(String key, PlayerCharacter playerCharacter, String[] args) {
        super(key, playerCharacter, args);
    }

    @Override
    public boolean match() throws NoSuchFieldException, IllegalAccessException {
        PlayerCharacter playerCharacter = getPlayerCharacter();
        String[] args = getArgs();
        return  GameCharacterManager.skillLevelGt(playerCharacter, args[0], Integer.parseInt(args[1]));
    }
}
