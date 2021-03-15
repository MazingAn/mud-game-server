package com.mud.game.commands.unlogin;


import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.DivideMessage;
import com.mud.game.messages.SimpleCharacterMessage;
import com.mud.game.object.account.Player;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.structs.SimpleCharacter;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 选择分区命令
 * 返回角色信息
 *
 * <pre>
 *     请求示例：
 *      {   "cmd": "divide",
 *           "args": {
 *              "divide":"test001"
 *              "userName":"********",
 *              }
 * *        }
 * </pre>
 */
public class Divide extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public Divide(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        //分区key
        String divide = this.getArgs().getString("divide");
        //用户名
        String userName = this.getArgs().getString("userName");
        Player player = MongoMapper.playerRepository.findByUsername(userName);
        Set<SimpleCharacter> simpleCharacters = player.getPlayerCharacters();
        List<SimpleCharacter> simpleCharacterList = new ArrayList<>();
        for (SimpleCharacter simpleCharacter : simpleCharacters) {
            if (simpleCharacter.getDivide().equals(divide)) {
                simpleCharacterList.add(simpleCharacter);
            }
        }
        getSession().sendText(JsonResponse.JsonStringResponse(new SimpleCharacterMessage(simpleCharacterList)));
    }
}
