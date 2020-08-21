package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.AlertMessage;
import com.mud.game.messages.CheckSlotMessage;
import com.mud.game.messages.ImbedGemsMessage;
import com.mud.game.messages.LoadGemsMessage;
import com.mud.game.object.typeclass.EquipmentObject;
import com.mud.game.object.typeclass.GemObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.structs.LoadGemsInfo;
import com.mud.game.structs.checkSlotInfo;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Gem;
import com.mud.game.worlddata.db.models.SlotMaterial;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 检查能够镶嵌在这件装备上的宝石
 * <p>
 * {
 * "cmd": loadgems,
 * "args": "<装备的dbref>"
 * }
 */
public class LoadGems extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public LoadGems(String key, Object caller, JSONObject args, Session session) {
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
        //  装备信息
        EquipmentObject equipmentObject = MongoMapper.equipmentObjectRepository.findEquipmentObjectById(dbref);
        if (null == equipmentObject) {
            return;
        }
        //装备可镶嵌的宝石
        // 可装备位置
        Set<String> positions = equipmentObject.getPositions();
        Iterator<String> positionsIterator = positions.iterator();
        String positionStr = null;
        List<GemObject> gemObjectList = null;
        List<GemObject> gemAllList = new ArrayList<>();
        while (positionsIterator.hasNext()) {
            gemObjectList = new ArrayList<>();
            positionStr = positionsIterator.next();
            gemObjectList = MongoMapper.gemObjectRepository.findGemObjectByOwnerAndPositionsLike(caller.getId(), positionStr);
            gemAllList.addAll(gemObjectList);
        }
        //获取材料信息
        List<SlotMaterial> slotMaterialList = DbMapper.slotMaterialRepository.findSlotMaterialByDataKeyAndSlotNumber(equipmentObject.getDataKey(), equipmentObject.getOpendSlot());
        //TODO 返回数据
        //检查装备是否满足开孔条件-开孔材料
        caller.msg(new CheckSlotMessage(new checkSlotInfo(equipmentObject, slotMaterialList, caller)));
        if (null == gemAllList) {
            gemAllList = new ArrayList<>();
        }
        caller.msg(new LoadGemsMessage(gemAllList));
        //已镶嵌的宝石
        caller.msg(new ImbedGemsMessage(equipmentObject.getGems()));
    }
}
