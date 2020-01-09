package com.mud.game.condition.attribute;

import com.mud.game.condition.BaseCondition;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;

import java.lang.reflect.Field;

public class AttrGt extends BaseCondition {

    public AttrGt(String key, PlayerCharacter playerCharacter, String[] args) {
        super(key, playerCharacter, args);
    }

    @Override
    public boolean match() throws NoSuchFieldException, IllegalAccessException {
        String[] args = getArgs();
        String attrKey = args[0].replaceAll("\"", "");
        PlayerCharacter playerCharacter = getPlayerCharacter();
        float matchValue = Float.parseFloat(args[1]);
        Object originValue =  GameCharacterManager.findAttributeByName(playerCharacter, attrKey) ;
        if(originValue == null){
            return false;
        }
        if(originValue.getClass() == Integer.class){
            // 如果比较的数据类型是Int类型
            originValue = (int)originValue * 1F;
        }
        return (float)originValue > matchValue;
    }

}
