package com.mud.game.object.manager;

import com.mud.game.statements.buffers.CharacterBuffer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class FightBufferScheduleManager {
    public static Map<String, ScheduledExecutorService> scheduledExecutorServiceBufferMap = new HashMap<>();

    public static ScheduledExecutorService createOrGetExecutorServiceForCallerAndBuffer(CharacterBuffer characterBuffer) {
        ScheduledExecutorService service = null;
        if (existExecutorServiceByBufferId(characterBuffer.bufferId)) {
            service = scheduledExecutorServiceBufferMap.get(characterBuffer.bufferId);
        } else {
            service = Executors.newSingleThreadScheduledExecutor();
            scheduledExecutorServiceBufferMap.put(characterBuffer.bufferId, service);
        }
        return service;
    }

    public static boolean existExecutorServiceByBufferId(String bufferId) {
        return scheduledExecutorServiceBufferMap.containsKey(bufferId);
    }

    public static void shutdownExecutorByBufferId(String bufferId) {
        ScheduledExecutorService service = scheduledExecutorServiceBufferMap.get(bufferId);
        if (service != null) {
            service.shutdown();
        }
        scheduledExecutorServiceBufferMap.remove(bufferId);
    }
}
