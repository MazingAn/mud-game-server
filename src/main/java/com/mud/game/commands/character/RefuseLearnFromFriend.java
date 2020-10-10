package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import static com.mud.game.utils.resultutils.GameWords.REFUSE_LEARN_FROM_FRIEND;

/**
 * 玩家拒绝切磋命令
 * 返回拒绝信息
 * <p>
 * 使用示例：
 * <pre>
 *      {
 *          "cmd" : "refuse_learn_from_friend",
 *          "args" : "user.id" //被拒绝的ID
 *      }
 * </pre>
 */
public class RefuseLearnFromFriend extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public RefuseLearnFromFriend(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        String targetId = args.getString("args");
        PlayerCharacter target = MongoMapper.playerCharacterRepository.findPlayerCharacterById(targetId);
        target.msg(new ToastMessage(String.format(REFUSE_LEARN_FROM_FRIEND, target.getName())));
    }
}
