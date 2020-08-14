package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.object.manager.EquipmentObjectManager;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.EquipmentObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

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
        // 装备Id
        String dbref = args.getString("args");
        //获取装备信息
        EquipmentObject equipmentObject = MongoMapper.equipmentObjectRepository.findEquipmentObjectById(dbref);
        //强化
        EquipmentObjectManager.strengthen(equipmentObject, caller, getSession());
        //返回当前强化信息
        equipmentObject = MongoMapper.equipmentObjectRepository.findEquipmentObjectById(dbref);
        EquipmentObjectManager.checkStrengthen(equipmentObject, caller, getSession());
    }
}
