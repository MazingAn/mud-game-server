package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.object.manager.CommonItemContainerManager;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

/**
 * 根据dataKey和数量丢弃物品
 * <p>
 * 使用示例：
 * <pre>
 *      {
 *      "cmd" : "discard_by_number",
 *      "args":
 *          {
 *          "dataKey": "物品key",
 *          "number": 0  /销毁数量
 *          }
 *      }
 * </pre>
 */
public class DiscardByNumber extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public DiscardByNumber(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        //TODO  物品可销毁
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        String dbref = args.getString("dbref");
        int number = args.getInt("number");
        PlayerCharacterManager.discardObjectById(caller, dbref, number);
    }
}
