package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

/**
 * 删除仇人
 * <p>
 * 请求示例：
 * <pre>
 * {
 *      "cmd":"delete_enemy",
 *      "args":"dbref"  被删除的玩家id
 * }
 * </pre>
 */
public class DeleteEnemy extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public DeleteEnemy(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        Session session = getSession();
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        try {
            String enemyId = args.getString("args");
            PlayerCharacterManager.deleteEnemy(caller, enemyId, session);
        } catch (Exception e) {
            System.out.println("哎呀！在删除仇人的时候出现了异常");
        }
    }
}
