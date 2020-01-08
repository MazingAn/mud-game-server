package com.mud.game.net.session;

import io.netty.channel.ChannelId;
import org.yeauty.pojo.Session;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GameSessionService {
    /*
    * @ 游戏Session管理
    * @ callerId2SessionMap, 是调用者ID到Session的一个映射  用于通过callerID得到对应的session
    * @ sessionID2callerIdMap, 是sessionId到调用者ID的一个映射  用于通过sessionID得到对应的caller
    * @ callerId2CallerType， 是callerType到callerId的一个映射  在得到caller之后要知道caller对应的命令集合则需要通过callerType去判断
    * */

    public static  Map<String, Session> callerId2SessionMap = new HashMap<>();
    public static  Map<ChannelId, String> sessionId2CallerIdMap = new HashMap<>();
    public static Map<String, CallerType> callerId2CallerTypeMap = new HashMap<>();

    public static void addCallerType(String callerId, CallerType callerType){
        callerId2CallerTypeMap.put(callerId, callerType);
    }

    public static CallerType getCallerTypeByCallerId(String callerId) {
        return callerId2CallerTypeMap.get(callerId);
    }

    public static void addSession(String callerId, Session session) {
        callerId2SessionMap.put(callerId, session);
        sessionId2CallerIdMap.put(session.id(), callerId);
    }

    public static Session getSessionByCallerId(String callerId){
        return callerId2SessionMap.get(callerId);
    }

    public static String getCallerIdBySessionId(ChannelId sessionId){
        return sessionId2CallerIdMap.get(sessionId);
    }


    public static void removeSessionByCallerId(String callerId) {
        /*
        * 通过调用者ID删除Session
        * */
        if (callerId2SessionMap.containsKey(callerId)) {
            Session session = getSessionByCallerId(callerId);
            sessionId2CallerIdMap.remove(session.id());
            callerId2SessionMap.remove(callerId);
            callerId2CallerTypeMap.remove(callerId);
        }
    }

    public static void removeSessionBySessionId(ChannelId sessionId) {
        /*
        * @ 通过sessionId删除Session
        * */
        if (sessionId2CallerIdMap.containsKey(sessionId)) {
            String callerId = sessionId2CallerIdMap.get(sessionId);
            callerId2SessionMap.remove(callerId);
            sessionId2CallerIdMap.remove(sessionId);
            callerId2CallerTypeMap.remove(callerId);
        }
    }

    public static void updateCallerId(String oldId, String newId, CallerType newType) {
        /*
        * @ 通过更新sessionId 完成caller身份的转换，适用于如下情况
        * @ 未登陆的匿名状态-》登陆后的账户状态
        * @ 登陆后的账户状态-》进入游戏后的角色状态
        * */
        if(callerId2SessionMap.containsKey(oldId)){
            // 保存session，更新session对应的caller
            Session session = callerId2SessionMap.get(oldId);
            removeSessionByCallerId(oldId);
            addSession(newId, session);
            // 更新身份对应关系
            callerId2CallerTypeMap.remove(oldId);
            callerId2CallerTypeMap.put(newId, newType);
        }
    }



}
