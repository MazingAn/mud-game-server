package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.AlertMessage;
import com.mud.game.messages.CheckSlotMessage;
import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.typeclass.EquipmentObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.structs.checkSlotInfo;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.SlotMaterial;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.List;

/**
 * {
 * "cmd" : "open_slot",
 * "args": <装备的dbref>
 * }
 */
public class OpenSlot extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public OpenSlot(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        if (null == args) {
            caller.msg("无效的参数！");
        }
        // 装备Id
        String dbref = args.getString("args");
        //装备信息
        EquipmentObject equipmentObject = MongoMapper.equipmentObjectRepository.findEquipmentObjectById(dbref);
        if (null == equipmentObject) {
            return;
        }
        //获取材料信息
        List<SlotMaterial> slotMaterialList = DbMapper.slotMaterialRepository.findSlotMaterialByDataKeyAndSlotNumber(equipmentObject.getDataKey(), equipmentObject.getOpendSlot());
        if (slotMaterialList.size() == 0) {
            return;
        }
        //从背包移除材料
        for (int i = 0; i < slotMaterialList.size(); i++) {
            CommonObject removeObject = CommonObjectBuilder.findObjectByDataKeyAndOwner(slotMaterialList.get(i).getDependency(), caller.getId());
            PlayerCharacterManager.removeObjectsFromBagpack(caller, removeObject, slotMaterialList.get(i).getNumber());
        }
        //打孔
        equipmentObject.setOpendSlot(equipmentObject.getOpendSlot() + 1);
        equipmentObject = MongoMapper.equipmentObjectRepository.save(equipmentObject);
        PlayerCharacterManager.syncBagpack(caller, equipmentObject);
        caller.msg(new AlertMessage("你的{g" + equipmentObject.getName() + "{n打孔成功!"));
        PlayerCharacterManager.showBagpack(caller);
        //返回下一级数据
        slotMaterialList = DbMapper.slotMaterialRepository.findSlotMaterialByDataKeyAndSlotNumber(equipmentObject.getDataKey(), equipmentObject.getOpendSlot());
        caller.msg(new CheckSlotMessage(new checkSlotInfo(equipmentObject, slotMaterialList, caller)));
    }
}
