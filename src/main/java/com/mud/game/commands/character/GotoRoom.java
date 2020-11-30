package com.mud.game.commands.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.utils.StateConstants;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.ArrayList;
import java.util.List;

import static com.mud.game.utils.StateConstants.CHECK_GOTO_ROOM_STATE;

/**
 * 玩家移动到某一个房间（通过出口）
 * 请求示例:
 * <pre>
 *    {
 *        "cmd": "goto",
 *        "args": "exit_id" //要通过的出口的ID
 *    }
 * </pre>
 */

public class GotoRoom extends BaseCommand {
    public GotoRoom(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {

        JSONObject args = getArgs();
        Session session = getSession();
        PlayerCharacter playerCharacter = (PlayerCharacter) getCaller();
        //判断能否移动
        if (StateConstants.checkState(playerCharacter, CHECK_GOTO_ROOM_STATE)) {
            String exitId = args.getString("args");
            long start = System.currentTimeMillis();
            PlayerCharacterManager.gotoRoom(playerCharacter, exitId, session);
            long end = System.currentTimeMillis();
            System.out.println("从一个房间移动到另一个房间，耗费时间：" + (end - start) + "ms");
        } else {
            playerCharacter.msg(new ToastMessage("当前状态不可移动！"));
        }
    }


}
