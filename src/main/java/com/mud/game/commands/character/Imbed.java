package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.AlertMessage;
import com.mud.game.messages.ImbedGemsMessage;
import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.typeclass.EquipmentObject;
import com.mud.game.object.typeclass.GemObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * {
 * cmd: "imbed",
 * args: {
 * "equipment" : "<装备的dbref>",
 * "gem": "<所用宝石的dbref>"
 * }
 * }
 */
public class Imbed extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public Imbed(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        // 装备id
        String dbref = args.getString("equipment");
        //宝石id
        String gemDbref = args.getString("gem");
        if (StringUtils.isEmpty(dbref) || StringUtils.isEmpty(gemDbref)) {
            caller.msg(new AlertMessage("镶嵌失败！"));
            return;
        }
        EquipmentObject equipmentObject = MongoMapper.equipmentObjectRepository.findEquipmentObjectById(dbref);
        if (null == equipmentObject) {
            caller.msg(new AlertMessage("镶嵌失败!"));
            return;
        }
        //装备已镶嵌的宝石
        List<GemObject> gemList = equipmentObject.getGems();
        if (null == gemList) {
            gemList = new ArrayList<>();
        }
        // 校验镶嵌的装备是否有空余孔数
        if (equipmentObject.getOpendSlot() - gemList.size() <= 0) {
            caller.msg(new AlertMessage("镶嵌失败!"));
            return;
        }
        //查询要装备的宝石
        GemObject gemObject = MongoMapper.gemObjectRepository.findGemObjectById(gemDbref);
        if (null == gemObject) {
            caller.msg(new AlertMessage("镶嵌失败!"));
            return;
        }
        //镶嵌
        gemList.add(gemObject);
        equipmentObject.setGems(gemList);
        MongoMapper.equipmentObjectRepository.save(equipmentObject);
        //删除背包里的宝石
        CommonObject removeObject = CommonObjectBuilder.findObjectById(gemDbref);
        if (null == removeObject) {
            caller.msg(new AlertMessage("镶嵌失败！"));
            return;
        }
        PlayerCharacterManager.removeObjectsFromBagpack(caller, removeObject, 1);
        if (removeObject.getMaxStack() == 1) {
            CommonObjectBuilder.deleteObjectById(removeObject.getId());
        }
        PlayerCharacterManager.showBagpack(caller);
        caller.msg(new AlertMessage("镶嵌成功！"));
        //返回已镶嵌的所有宝石
        caller.msg(new ImbedGemsMessage(equipmentObject.getGems()));
    }
}
