package com.mud.game.commands.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.object.manager.EquipmentObjectManager;
import com.mud.game.object.typeclass.EquipmentObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;
/**
 * 卸掉装备，放回背包
 * Usage:
 * <pre>
 *   {
 *      "cmd":"take_off_equipment",
 *      "args":
 *          {
 *              "position": "left_hand", //要卸掉的装备位置
 *              "dbref": "#xxxx" //装备的ID
 *          }
 *   }
 * </pre>
 * */
public class TakeOffEquipment extends BaseCommand {

    public TakeOffEquipment(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        Session session = getSession();
        PlayerCharacter caller = (PlayerCharacter)getCaller();
        JSONObject args = getArgs();
        String equipmentObjectId = args.getString("dbref");
        String position = args.getString("position");
        EquipmentObject equipmentObject = MongoMapper.equipmentObjectRepository.findEquipmentObjectById(equipmentObjectId);
        EquipmentObjectManager.takeOff(equipmentObject, caller, position, session);
    }
}
