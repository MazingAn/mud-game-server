package com.mud.game.object.manager;

import com.mud.game.messages.PlayerCharacterStateMessage;
import com.mud.game.net.session.GameSessionService;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.statements.buffers.CharacterBuffer;
import com.mud.game.structs.CharacterState;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.yeauty.pojo.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PlayerScheduleManager {
    public static Map<String, ScheduledExecutorService> scheduledExecutorServiceMap = new HashMap<>();

    public static ScheduledExecutorService createOrGetExecutorServiceForCaller(String callerId) {
        ScheduledExecutorService service = null;
        if (existExecutorServiceByCallerId(callerId)) {
            service = scheduledExecutorServiceMap.get(callerId);
        } else {
            service = Executors.newSingleThreadScheduledExecutor();
            scheduledExecutorServiceMap.put(callerId, service);
        }
        return service;
    }


    public static boolean existExecutorServiceByCallerId(String callerId) {
        return scheduledExecutorServiceMap.containsKey(callerId);
    }

    public static void shutdownExecutorByCallerId(String callerId) {
        ScheduledExecutorService service = scheduledExecutorServiceMap.get(callerId);
        if (service != null) {
            service.shutdown();
        }
        scheduledExecutorServiceMap.remove(callerId);
        PlayerCharacter caller = MongoMapper.playerCharacterRepository.findPlayerCharacterById(callerId);
        caller.setState(CharacterState.STATE_NORMAL);
        MongoMapper.playerCharacterRepository.save(caller);
        Session session = GameSessionService.getSessionByCallerId(callerId);
        if (session != null) {
            session.sendText(JsonResponse.JsonStringResponse(new PlayerCharacterStateMessage(caller.getState())));
        }
    }

    public static void startExecutorByCallerId(String callerId, Long delay, Long interval, Runnable runnable) {
        /*
         * 使用玩家的定时任务服务，开启一个定时任务刷新任务，定时任务启动延迟为delay毫秒，每隔interval毫秒执行一次，执行的内容是 runnable
         * */
        ScheduledExecutorService service = scheduledExecutorServiceMap.get(callerId);
        service.scheduleAtFixedRate(runnable, delay, interval, TimeUnit.MILLISECONDS);
    }

}
