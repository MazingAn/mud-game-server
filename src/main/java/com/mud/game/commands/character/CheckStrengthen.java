package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.AlertMessage;
import com.mud.game.object.typeclass.EquipmentObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.NormalObject;
import com.mud.game.worlddata.db.models.StrengthenMaterial;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.yeauty.pojo.Session;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 强化列表
 * <p>
 * {
 * cmd: "check_strengthen",   //命令
 * * args:{
 * * "dbref":123    //武器主键
 * * }
 * <p>
 * strengthen_check 返回命令
 * }
 */
public class CheckStrengthen extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public CheckStrengthen(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        // 装备Id
        String dbref = args.getString("dbref");
        //获取装备信息
        EquipmentObject equipmentObject = MongoMapper.equipmentObjectRepository.findEquipmentObjectById(dbref);
        if (null == equipmentObject) {
            caller.msg(new AlertMessage("装备信息为空!"));
            return;
        }
        //校验
        if (equipmentObject.getLevel() > 9) {
            caller.msg(new AlertMessage("装备以达到最大强化等级！"));
            return;
        }
        //获取装备强化需要的材料列表
        String dataKey = equipmentObject.getDataKey();
        List<StrengthenMaterial> strengthenMaterialList = DbMapper.strengthenMaterialRepository.findStrengthenMaterialByDataKey(dataKey);
        if (strengthenMaterialList == null || strengthenMaterialList.size() == 0) {
            caller.msg(new AlertMessage("装备的强化材料为空"));
            return;
        }
        Iterator<StrengthenMaterial> strengthenMaterialIterator = strengthenMaterialList.iterator();
        StrengthenMaterial strengthenMaterial = null;
        while (strengthenMaterialIterator.hasNext()) {
            strengthenMaterial = strengthenMaterialIterator.next();
            //获取材料信息
            NormalObject target = DbMapper.normalObjectRepository.findNormalObjectByDataKey(strengthenMaterial.getDependency());
            BeanUtils.copyProperties(target, strengthenMaterial);
        }

        caller.msg(new HashMap<String, Object>() {{
            put("strengthen_check", strengthenMaterialList);
        }});
    }
}
