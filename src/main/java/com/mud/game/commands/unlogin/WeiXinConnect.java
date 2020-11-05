package com.mud.game.commands.unlogin;

import com.mud.game.commands.BaseCommand;
import com.mud.game.feign.FeignService.WeixinService;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.account.Player;
import com.mud.game.object.account.WeiXinPlayer;
import com.mud.game.object.manager.PlayerManager;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.utils.resultutils.GameWords;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;


/**
 * weiixn连接（登录）命令
 *
 * <pre>
 *     请求示例：
 *      {"cmd":"wexin_connect",
 *         "args":{
 *           "state":"state",
 *           "code":"*********"
 *           }
 *       }
 * </pre>
 */
public class WeiXinConnect extends BaseCommand {


    public WeiXinConnect(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        /**
         * 对一个游戏账户执行登陆操作
         * 本质上就是更新玩家的更新callerID为角色ID，然后修改角色的类型为账户类型
         * */
        Session session = getSession();
        String code = this.getArgs().getString("code");
        String state = this.getArgs().getString("state");

        try {
            JSONObject obj = WeixinService.weixinFeign.getAccessToken("wx77311c371826f8aa", "6ab5366ade1c754980fcb8f7e8a312ee", code, "authorization_code");
            if (obj.get("access_token") != null) {
                String token = obj.getString("access_token");
                String openid = obj.getString("openid");
                obj = WeixinService.weixinFeign.userinfo(token, openid);
                if (obj.get("unionID") != null) {
                    String unionID = obj.getString("unionID");
                    Player player = null;
                    if (MongoMapper.weiXinPlayerRepository.existsByUnionId(unionID)) {
                        WeiXinPlayer weiXinPlayer = MongoMapper.weiXinPlayerRepository.findWeiXinPlayerByUnionId(unionID);
                        player = MongoMapper.playerRepository.findPlayerById(weiXinPlayer.getPlayerId());
                    } else {
                        player = PlayerManager.create(null, null, session);
                        MongoMapper.weiXinPlayerRepository.save(new WeiXinPlayer(player.getId(), unionID));
                    }
                    PlayerManager.showCharacters(player, session);
                }
            }
        } catch (Exception e) {
            session.sendText(JsonResponse.JsonStringResponse(new ToastMessage("登陆失败！")));
        }
    }
}
