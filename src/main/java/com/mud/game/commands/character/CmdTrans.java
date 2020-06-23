package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.TransList;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

/**
 * 玩家移动到某一个房间（通过出口）
 * 请求示例:
 * <pre>
 *    {
 *        "cmd": "goto",
 *        "args": "exit_id" //要通过的出口的ID
 *    }
 * </pre>
 * */

public class CmdTrans extends BaseCommand {
    public CmdTrans(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        JSONObject args = getArgs();
        Session session = getSession();
        PlayerCharacter playerCharacter = (PlayerCharacter) getCaller();
        String roomKey = args.getString("room");
        String npcKey = args.getString("npc");
        TransList record = DbMapper.transListRepository.findTransListByNpcAndRoom(npcKey, roomKey);
        if(PlayerCharacterManager.removeObjectsFromBagpack(playerCharacter, record.getNeedObject(), record.getNumber())){
            PlayerCharacterManager.moveTo(playerCharacter, roomKey);
        }else{
            playerCharacter.msg(new ToastMessage("不想走路？看看自己口袋有没有人家要的{c东西{n？"));
        }
    }
}
