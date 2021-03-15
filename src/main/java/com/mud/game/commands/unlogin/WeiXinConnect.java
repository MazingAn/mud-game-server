package com.mud.game.commands.unlogin;

import com.mud.game.commands.BaseCommand;
import com.mud.game.feign.FeignService.WeixinService;
import com.mud.game.messages.ToastMessage;
import com.mud.game.net.session.CallerType;
import com.mud.game.net.session.GameSessionService;
import com.mud.game.object.account.WeiXinPlayer;
import com.mud.game.object.manager.PlayerManager;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.Map;

import static com.mud.game.constant.PostConstructConstant.WEIXIN_APPID;
import static com.mud.game.constant.PostConstructConstant.WEIXIN_SECRET;


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
            Map<String, Object> map = WeixinService.weixinFeign.getAccessToken(WEIXIN_APPID, WEIXIN_SECRET, code, "authorization_code");
            if (map.get("access_token") != null) {
                String token = map.get("access_token").toString();
                String openid = map.get("openid").toString();
                map = WeixinService.weixinFeign.userinfo(token, openid);
                if (map.get("unionid") != null) {
                    String unionid = map.get("unionid").toString();
                    WeiXinPlayer player = null;
                    if (MongoMapper.weiXinPlayerRepository.existsByUnionId(unionid)) {
                        player = MongoMapper.weiXinPlayerRepository.findWeiXinPlayerByUnionId(unionid);
                    } else {
                        player = PlayerManager.weixinCreate(session, unionid, map.get("nickname").toString());
                    }
                    // 根据session的id拿到老的callerID
                    String oldId = GameSessionService.getCallerIdBySessionId(session.id());
                    // 转交session给新的caller
                    GameSessionService.updateCallerId(oldId, player.getId(), CallerType.ACCOUNT);
                    //session.sendText(JsonResponse.JsonStringResponse(new LoginSuccessMessage(player)));
                    PlayerManager.showWeiXinCharacters(player, session);
                }
            }
        } catch (Exception e) {
            session.sendText(JsonResponse.JsonStringResponse(new ToastMessage("登陆失败！")));
        }
    }
}
