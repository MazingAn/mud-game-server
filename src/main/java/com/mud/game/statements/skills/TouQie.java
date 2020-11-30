package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.manager.HangUpManager;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.typeclass.BagpackObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import com.mud.game.structs.CommonObjectInfo;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;

import java.util.Map;
import java.util.Random;

/**
 * 就是随机偷取玩家或者NPC的背包物品
 * <p>
 * 30%
 */
public class TouQie extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public TouQie(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        super(caller, target, skillObject, key, args);
    }

    @Override
    public void attack() throws JSONException, JsonProcessingException {
        if (HangUpManager.randomInterval(0, 2) == 0) {
            PlayerCharacter playerCharacter = MongoMapper.playerCharacterRepository.findPlayerCharacterById(getCaller().getId());
            BagpackObject bagpackObject = MongoMapper.bagpackObjectRepository.findBagpackObjectById(playerCharacter.getBagpack());
            Map<String, CommonObjectInfo> valuess = bagpackObject.getItems();
            String[] keys = valuess.keySet().toArray(new String[0]);
            Random random = new Random();
            String randomKey = keys[random.nextInt(keys.length)];
            CommonObjectInfo randomValue = valuess.get(randomKey);
            // 物品信息
            CommonObject commonObject = CommonObjectBuilder.findObjectById(randomValue.getDbref());
            // 数量随机
            int number = 1;
            int total = commonObject.getTotalNumber();
            //删除背包内的已被偷取的物品
            if (getTarget() instanceof PlayerCharacter) {
                PlayerCharacterManager.removeObjectsFromBagpack((PlayerCharacter) getTarget(), commonObject, number);
                PlayerCharacterManager.showBagpack((PlayerCharacter) getTarget());
            }

            PlayerCharacterManager.receiveObjectToBagpack((PlayerCharacter) getCaller(), commonObject, number);
            //物品唯一修改物品归属
            if (commonObject.isUnique()) {
                commonObject.setOwner(getCaller().getId());
                CommonObjectBuilder.save(commonObject);
            }
            //刷新背包
            PlayerCharacterManager.showBagpack((PlayerCharacter) getCaller());

        }
    }
}
