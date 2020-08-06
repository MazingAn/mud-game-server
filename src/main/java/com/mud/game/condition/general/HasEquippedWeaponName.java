package com.mud.game.condition.general;

import com.mud.game.condition.BaseCondition;
import com.mud.game.messages.MsgMessage;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.typeclass.EquipmentObject;
import com.mud.game.object.typeclass.PlayerCharacter;

import java.util.Set;

public class HasEquippedWeaponName extends BaseCondition {
    /**
     * 构造函数
     * <p>
     * 构造函数用于捕获参数
     *
     * @param key             检测类的健
     * @param playerCharacter 检测的主体对象（要被检测的玩家）
     * @param args            检测函数参数列表（字符串列表）
     */
    public HasEquippedWeaponName(String key, PlayerCharacter playerCharacter, String[] args) {
        super(key, playerCharacter, args);
    }

    @Override
    public boolean match() throws NoSuchFieldException, IllegalAccessException {
        boolean result = false;
        PlayerCharacter playerCharacter = getPlayerCharacter();
        String[] args = getArgs();
        String weaponName = args[0];
        Set<EquipmentObject> equipmentObjectSet = GameCharacterManager.getAllEquippedWeapon(playerCharacter);
        for(EquipmentObject eo : equipmentObjectSet){
            if(eo!=null && eo.getName().equals(weaponName)){
                result = true;
                break;
            }
        }
        if(!result){
            //BadCode 希望前端能够听句劝 后端就不用发两条了
            playerCharacter.msg(new ToastMessage(String.format("需要装备武器{g%s{n", weaponName)));
            playerCharacter.msg(new MsgMessage(String.format("需要装备武器{g%s{n", weaponName)));
        }
        return result;
    }
}
