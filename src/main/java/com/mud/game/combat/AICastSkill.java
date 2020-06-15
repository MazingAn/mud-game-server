package com.mud.game.combat;

import com.mud.game.object.account.Player;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.SkillObject;

public class AICastSkill {

    /**
     * 角色自动选择技能并攻击对手
     *
     * @param character 角色
     * */
    private void autoCombat(CommonCharacter character){

        if(character instanceof PlayerCharacter){
            CommonCharacter target = GameCharacterManager.getCharacterObject(character.getTarget());
        }

    }
}
