package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.AlertMessage;
import com.mud.game.object.manager.CommonItemContainerManager;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.BagpackObject;
import com.mud.game.object.typeclass.EquipmentObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.NormalObject;
import com.mud.game.worlddata.db.models.StrengthenMaterial;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 强化装备
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
public class Strengthen extends BaseCommand {

    private static final Double coefficient = 1.2;
    private static final int maxLevel = 9;

    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public Strengthen(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        String bagpackId = caller.getBagpack();
        // 装备Id
        String dbref = args.getString("dbref");
        //获取装备信息
        EquipmentObject equipmentObject = MongoMapper.equipmentObjectRepository.findEquipmentObjectById(dbref);
        if (null == equipmentObject) {
            caller.msg(new AlertMessage("装备信息为空!"));
            return;
        }
        //校验
        if (equipmentObject.getLevel() > maxLevel) {
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
        //校验合成材料是否足够
        //背包信息校验
        BagpackObject bagpackObject = MongoMapper.bagpackObjectRepository.findBagpackObjectById(bagpackId);
        for (int i = 0; i < strengthenMaterialList.size(); i++) {
            if (!CommonItemContainerManager.checkCanRemove(bagpackObject, strengthenMaterialList.get(i).getDependency(), strengthenMaterialList.get(i).getNumber())) {
                //获取材料信息
                NormalObject target = DbMapper.normalObjectRepository.findNormalObjectByDataKey(strengthenMaterialList.get(i).getDependency());
                caller.msg(new AlertMessage("你的{g" + target.getName() + "{n不够!"));
                break;
            }
        }
        //从背包移除材料
        for (int i = 0; i < strengthenMaterialList.size(); i++) {
            PlayerCharacterManager.removeObjectsFromBagpack(caller, strengthenMaterialList.get(i).getDependency(), strengthenMaterialList.get(i).getNumber());
        }
        //强化
        equipmentObject.setLevel(equipmentObject.getLevel() + 1);
        Map<String, Map<String, Object>> attrs = equipmentObject.getAttrs();
        Iterator<Map.Entry<String, Map<String, Object>>> mapIterator = attrs.entrySet().iterator();
        while (mapIterator.hasNext()) {
            Map.Entry<String, Map<String, Object>> mapEntry = mapIterator.next();
            Iterator<Map.Entry<String, Object>> mapEntryIterator = mapEntry.getValue().entrySet().iterator();
            while (mapEntryIterator.hasNext()) {
                Map.Entry<String, Object> map = mapEntryIterator.next();
                if ("value".equals(map.getKey())) {
                    if (null != map.getValue()) {
                        int value = (int) map.getValue();
                        map.setValue(Math.floor(value * coefficient));
                    }
                }
            }
        }
        equipmentObject.setAttrs(attrs);
        MongoMapper.equipmentObjectRepository.save(equipmentObject);
    }
}
