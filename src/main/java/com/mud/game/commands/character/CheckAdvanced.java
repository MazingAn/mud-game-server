package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.CheckQualityMessage;
import com.mud.game.object.typeclass.EquipmentObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.structs.CheckQualityInfo;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.QualityMaterial;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.List;

/**
 * {
 * "cmd": check_advanced,
 * "args": "<装备的dbref>"
 * }
 */
public class CheckAdvanced extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public CheckAdvanced(String key, Object caller, JSONObject args, Session session) {
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
        //进阶材料信息
        List<QualityMaterial> qualityMaterialList = DbMapper.qualityMaterialRepository.findQualityMaterialByDataKeyAndQuality(equipmentObject.getDataKey(), equipmentObject.getQuality());
        //检查装备是否满足进阶条件-进阶材料
        CheckQualityInfo checkQualityInfo = new CheckQualityInfo(equipmentObject, qualityMaterialList, caller);
        caller.msg(new CheckQualityMessage(checkQualityInfo));
    }
}
