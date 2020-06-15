package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;


/**
 * 玩家打开商店
 * Usage:
 *         {"cmd":"shopping",
 *          "args":<shop's key>
 *         }
 * */
public class CmdShopping extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public CmdShopping(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter playerCharacter = (PlayerCharacter)getCaller();
        String shopKey = getArgs().getString("args");
        PlayerCharacterManager.visitShop(playerCharacter, shopKey, false);
    }
}
