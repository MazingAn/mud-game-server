package com.mud.game.commands.account;

import com.mud.game.commands.BaseCommand;
import com.mud.game.feign.FeignService.RecordService;
import com.mud.game.messages.DivideMessage;
import com.mud.game.messages.MailObjectMessage;
import com.mud.game.object.typeclass.PlayerCharacter;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.List;
import java.util.Map;

/**
 * 获取游戏分区
 *
 * <pre>
 *     请求示例：
 *      {"cmd":"get_divide",
 *       "args": "gameKey"
 *       }
 * </pre>
 */
public class GetDivide extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public GetDivide(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        String gameKey = getArgs().getString("args");
        Map<String, Object> map = RecordService.recordFeign.getDivideByGameKey("tscc");
        if (map.get("code").equals("0")) {
            //查询成功
            List<Map<String, String>> list = (List<Map<String, String>>) map.get("data");
            caller.msg(new DivideMessage(list));
        }
    }
}
