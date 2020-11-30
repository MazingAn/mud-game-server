package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.net.session.GameSessionService;
import com.mud.game.object.manager.EquipmentObjectManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.EquipmentObject;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.mapstruct.Mapper;
import org.yeauty.pojo.Session;

import java.util.Map;

/**
 * 下掉对方武器
 */
public class XiaWu extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public XiaWu(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        super(caller, target, skillObject, key, args);
    }

    @Override
    public void attack() throws JSONException, JsonProcessingException {
        Map<String, String> sourceData = getCaller().getEquippedEquipments();
        String equipmentObjectId = sourceData.get("POSITION_LEFT_HAND");
        if (equipmentObjectId == null) {
            return;
        }
        EquipmentObject equipmentObject = MongoMapper.equipmentObjectRepository.findEquipmentObjectById(equipmentObjectId);
        if (null == equipmentObject) {
            return;
        }
        Session session = GameSessionService.getSessionByCallerId(getTarget().getId());
        EquipmentObjectManager.takeOff(equipmentObject, getTarget(), "POSITION_LEFT_HAND", session);
    }
}
