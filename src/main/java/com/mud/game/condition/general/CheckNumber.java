package com.mud.game.condition.general;

import com.mud.game.condition.BaseCondition;
import com.mud.game.object.typeclass.PlayerCharacter;

/**
 * 随机概率
 * */
public class CheckNumber extends BaseCondition {
    public CheckNumber(String key, PlayerCharacter playerCharacter, String[] args) {
        super(key, playerCharacter, args);
    }

    @Override
    public boolean match() throws NoSuchFieldException, IllegalAccessException {
        String[] args = getArgs();
        PlayerCharacter playerCharacter = getPlayerCharacter();
        try {
            int minValue = Integer.parseInt(args[0]);
            int maxValue = Integer.parseInt(args[1]);
            int randomNumber = (int) playerCharacter.getRandomNumber();
            return randomNumber <= maxValue && randomNumber > minValue;
        }catch (Exception e){
            return false;
        }
    }
}
