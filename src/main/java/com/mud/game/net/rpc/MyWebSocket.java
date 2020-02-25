package com.mud.game.net.rpc;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.net.session.CallerDelegate;
import com.mud.game.net.session.CallerType;
import com.mud.game.net.session.GameSessionService;
import com.mud.game.object.manager.GameWorldManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.timeout.IdleStateEvent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.yeauty.annotation.*;
import org.yeauty.pojo.ParameterMap;
import org.yeauty.pojo.Session;
import java.io.IOException;
import java.util.UUID;

@Component
@ServerEndpoint(prefix = "netty-websocket")
public class MyWebSocket {

    @OnOpen
    public void onOpen(Session session, HttpHeaders headers, ParameterMap parameterMap) throws Exception {
        /*
        *
        * */
        System.out.println("New Connection!");
        // 第一次打开连接,保存session，并设置类型为匿名者
        String randomId = UUID.randomUUID().toString();
        GameSessionService.addSession(randomId,session);
        GameSessionService.addCallerType(randomId, CallerType.ANONYMOUS);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        /*
        * @ 在客户端与服务器断开连接的时候调用
        * */
        System.out.println("one connection closed");
        try{
            // 在session字典中查找当前session对应的游戏角色，并调用玩家离开游戏时应有的操作
            if(GameSessionService.sessionId2CallerIdMap.containsKey(session.id())){
                String callerId = GameSessionService.getCallerIdBySessionId(session.id());
                if(GameSessionService.getCallerTypeByCallerId(callerId).equals(CallerType.CHARACTER)){
                    PlayerCharacter playerCharacter = MongoMapper.playerCharacterRepository.findPlayerCharacterById(callerId);
                    GameWorldManager.onPlayerCharacterDisconnect(playerCharacter);
                }
            }
        }catch (Exception e){
            System.out.println("服务器被异常关闭,请在下次重启前清空房间内部的玩家！");
        }finally {
            GameSessionService.removeSessionBySessionId(session.id());
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(Session session, String message) throws JSONException, JsonProcessingException {
        if (message.length() < 1){
            //心跳包
        }else {
            // 根据当前session的id拿到对应的调用者，然后执行相应命令
            String callerId = GameSessionService.getCallerIdBySessionId(session.id());
            // 解析命令
            JSONArray jsonArray = new JSONArray(message);
            JSONObject jsonObject = new JSONObject(jsonArray.getString(1));
            JSONObject args;
            String commandKey = jsonObject.getString("cmd");
            try {
                args = jsonObject.getJSONObject("args");
            }catch (JSONException e){
                String str = jsonObject.getString("args");
                args = new JSONObject();
                args.put("args", str);
            }
            //创建代理，让代理去执行命令
            CallerDelegate callerDelegate = new CallerDelegate(callerId);
            callerDelegate.executeCommand(commandKey, args);
        }
    }

    @OnBinary
    public void onBinary(Session session, byte[] bytes) {
        for (byte b : bytes) {
            System.out.println(b);
        }
        session.sendBinary(bytes);
    }

    @OnEvent
    public void onEvent(Session session, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    System.out.println("read idle");
                    break;
                case WRITER_IDLE:
                    System.out.println("write idle");
                    break;
                case ALL_IDLE:
                    System.out.println("all idle");
                    break;
                default:
                    break;
            }
        }
    }

}
