package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.LoginSuccessMessage;
import com.mud.game.object.account.Player;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

/**
 * 获取当前用户信息
 * <p>
 * {cmd:"get_login" }
 * 返回命令
 * login:{
 * dbref: "5facd61760d49845b7feca2a"
 * name: "30"
 * }
 * </p>
 */
public class GetLogin extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public GetLogin(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        Session session = getSession();
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        Player player = MongoMapper.playerRepository.findPlayerById(caller.getPlayer());
        session.sendText(JsonResponse.JsonStringResponse(new LoginSuccessMessage(player)));
    }
}
