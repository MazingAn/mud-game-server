package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

/**
 * 查看属性
 * {
 * cmd:"show_character_status"
 * }
 */
public class ShowCharacterStatus extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public ShowCharacterStatus(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        Session session = getSession();
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        //查看Id
        String id = args.getString("args");
        GameCharacterManager.showStatus(caller, id);
    }
}
