package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.object.manager.GameChannelManager;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

/**
 * 玩家在频道内发言
 * {
 *      cmd: say,
 *      args: {
 *          channel : 频道Key
 *          message : 消息内容
 *      }
 * }
 * */
public class CmdSay extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public CmdSay(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        JSONObject args = getArgs();
        String channelKey = args.getString("channel");
        String message = args.getString("message");
        PlayerCharacter sender = (PlayerCharacter) getCaller();
        GameChannelManager.msgContents(sender, channelKey, message);
    }
}
