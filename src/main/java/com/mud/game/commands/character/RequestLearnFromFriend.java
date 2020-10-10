package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.net.session.GameSessionService;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.structs.EmbeddedCommand;
import com.mud.game.structs.PlayerCharacterAppearance;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 玩家发送切磋请求给被切磋的玩家
 * 返回请求切磋信息
 * <p>
 * 使用示例：
 * <pre>
 *      {
 *          "cmd" : "request_learn_from_friend",
 *          "args" : "user.id" //切磋对象的ID
 *      }
 * </pre>
 */
public class RequestLearnFromFriend extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public RequestLearnFromFriend(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        String targetId = args.getString("args");
        PlayerCharacter target = MongoMapper.playerCharacterRepository.findPlayerCharacterById(targetId);

        Map<String, Object> lookMessage = new HashMap<>();
        PlayerCharacterAppearance appearance = new PlayerCharacterAppearance(target);
        appearance.setDesc(PlayerCharacterManager.descriptionForTarget(caller, target));
        appearance.setCmds(getAvailableCommands(caller, target));
        lookMessage.put("look_obj", appearance);
        Session session = GameSessionService.getSessionByCallerId(targetId);
        session.sendText(JsonResponse.JsonStringResponse(lookMessage));
    }

    private List<EmbeddedCommand> getAvailableCommands(PlayerCharacter caller, PlayerCharacter target) {
        // 设置玩家可以操作的命令
        List<EmbeddedCommand> cmds = new ArrayList<>();
        cmds.add(new EmbeddedCommand("同意", "learn_from_friend", caller.getId()));
        cmds.add(new EmbeddedCommand("拒绝", "refuse_learn_from_friend", caller.getId()));
        return cmds;
    }
}
