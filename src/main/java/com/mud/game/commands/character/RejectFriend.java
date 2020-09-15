package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;


/**
 * 拒绝其他玩家的好友请求/删除好友
 * <p>
 * 请求示例：
 * <pre>
 * {
 *      "cmd":"reject_friend",
 *      "args":"dbref"  被拒绝/删除的玩家id
 * }
 * </pre>
 */

public class RejectFriend extends BaseCommand {

    public RejectFriend(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        Session session = getSession();
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        try {
            String friendId = args.getString("args");
            PlayerCharacterManager.rejectFriendRequest(caller, friendId, session);
        } catch (Exception e) {
            System.out.println("哎呀！在删除好友/好友请求的时候出现了异常");
        }
    }


}
