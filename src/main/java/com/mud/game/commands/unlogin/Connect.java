package com.mud.game.commands.unlogin;

import com.mud.game.commands.BaseCommand;
import com.mud.game.feign.FeignService.RecordService;
import com.mud.game.messages.DivideMessage;
import com.mud.game.object.account.Player;
import com.mud.game.object.manager.PlayerManager;
import com.mud.game.utils.jsonutils.JsonResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.List;
import java.util.Map;


/**
 * 连接（登录）命令
 *
 * <pre>
 *     请求示例：
 *      {"cmd":"connect",
 *          "args":{
 *              "playername":"test001",
 *              "password":"*********"
 *              }
 *       }
 * </pre>
 */
public class Connect extends BaseCommand {


    public Connect(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        /**
         * 对一个游戏账户执行登陆操作
         * 本质上就是更新玩家的更新callerID为角色ID，然后修改角色的类型为账户类型
         * */
        Session session = getSession();
        String playername = this.getArgs().getString("playername");
        String password = this.getArgs().getString("password");
        Player player = PlayerManager.login(playername, password, session);
        if (player != null) {
            // 登陆成功返回角色列表
            // PlayerManager.showCharacters(player, session);
            //登陆成功返回区划
            Map<String, Object> map = RecordService.recordFeign.getDivideByGameKey("tscc");
            if (map.get("code").equals("0")) {
                List<Map<String, String>> list = (List<Map<String, String>>) map.get("data");
                //返回区划
                session.sendText(JsonResponse.JsonStringResponse(new DivideMessage(list)));
            }
        }
    }
}
