package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.AlertMessage;
import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.manager.EquipmentObjectManager;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.typeclass.EquipmentObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.QualityMaterial;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.List;

import static com.mud.game.constant.Constant.MAX_LEVEL;
import static com.mud.game.constant.Constant.QUALITY_COEFFICIENT;


/**
 * {
 * cmd: "advanced",
 * args: <装备的dbref>
 * }
 */
public class Advanced extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public Advanced(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        // 装备Id
        String dbref = args.getString("args");
        //获取装备信息
        EquipmentObject equipmentObject = MongoMapper.equipmentObjectRepository.findEquipmentObjectById(dbref);
        if (null == equipmentObject) {
            caller.msg("装备信息错误！");
            return;
        }
        //判断是否达到强化的最高值
        if (equipmentObject.getLevel() != MAX_LEVEL) {
            caller.msg(new AlertMessage("装备强化值不足！"));
            return;
        }
        //材料验证
        //进阶材料信息
        List<QualityMaterial> qualityMaterialList = DbMapper.qualityMaterialRepository.findQualityMaterialByDataKeyAndQuality(equipmentObject.getDataKey(), equipmentObject.getQuality());
        //进阶材料信息
        if (qualityMaterialList.size() == 0) {
            caller.msg("进阶材料错误！");
            return;
        }
        //删除进阶材料
        //从背包移除材料
        for (int i = 0; i < qualityMaterialList.size(); i++) {
            CommonObject removeObject = CommonObjectBuilder.findObjectByDataKeyAndOwner(qualityMaterialList.get(i).getDependency(), caller.getId());
            PlayerCharacterManager.removeObjectsFromBagpack(caller, removeObject, qualityMaterialList.get(i).getNumber());
        }
        //进阶
        equipmentObject.setAttrs(EquipmentObjectManager.updateAttrs(equipmentObject, QUALITY_COEFFICIENT));
        equipmentObject.setLevel(0);
        equipmentObject.setQuality(equipmentObject.getQuality());
        MongoMapper.equipmentObjectRepository.save(equipmentObject);
        PlayerCharacterManager.syncBagpack(caller, equipmentObject);
        caller.msg(new AlertMessage("你的{g" + equipmentObject.getName() + "{n进阶成功!"));
        PlayerCharacterManager.showBagpack(caller);
    }
}
