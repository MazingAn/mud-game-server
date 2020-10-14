package com.mud.game.statements.buffers;

import com.mud.game.object.manager.FightBufferScheduleManager;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * buffer管理器
 * 负责buffer的创建、应用、撤销和销毁
 */
public class BufferManager {

    /**
     * buffer 和对应的定时器绑定
     */
    public static Map<String, Timer> bufferTimerMap = new HashMap<>();


    /**
     * buffer bufferID和对应的Buffer
     */
    public static Map<String, CharacterBuffer> bufferMap = new HashMap<>();


    /**
     * 新建buffer
     *
     * @param name         buffer名称
     * @param duration     持续时间
     * @param maxAdd       最大叠加次数
     * @param goodBuffer   是否是增益buffer
     * @param attrKey      作用的属性名称
     * @param changedValue 作用的属性值
     * @param target       buffer施加的对象
     * @param skillObject  技能对象
     * @param persistent
     * @param caller
     */
    public static CharacterBuffer CreateBuffer(String name, float duration, int maxAdd,
                                               boolean goodBuffer, String attrKey, Object changedValue,
                                               CommonCharacter target, SkillObject skillObject, boolean persistent, CommonCharacter caller) {
        CharacterBuffer characterBuffer = new CharacterBuffer(name, duration, maxAdd, goodBuffer, attrKey, changedValue, target, skillObject, persistent, caller);
        Timer timer = new Timer();
        bufferMap.put(characterBuffer.bufferId, characterBuffer);
        bufferTimerMap.put(characterBuffer.bufferId, timer);
        return characterBuffer;
    }

    /**
     * 获得buffer的计时器
     *
     * @param bufferId bufferId
     * @return Timer 对应的定时器
     */
    public static Timer getBufferTimer(String bufferId) {
        return bufferTimerMap.get(bufferId);
    }

    /**
     * 获得buffer
     *
     * @param bufferId bufferID
     * @return CharacterBuffer buffer对象
     */
    public static CharacterBuffer getBuffer(String bufferId) {
        return bufferMap.get(bufferId);
    }


    /**
     * 设置buffer的定时器生效 同时作用buffer
     *
     * @param bufferId bufferId
     */
    public static void startBufferTimer(String bufferId) {
        Timer timer = getBufferTimer(bufferId);
        CharacterBuffer characterBuffer = getBuffer(bufferId);
        if (characterBuffer.persistent) {
            //连续效果，例如：中毒掉血
            Runnable runnable = characterBuffer.scheduleAtFixedRate(characterBuffer.target, characterBuffer.goodBuffer, characterBuffer.attrKey, characterBuffer.changedValue);
            if (runnable != null) {
                ScheduledExecutorService service = FightBufferScheduleManager.createOrGetExecutorServiceForCallerAndBuffer(characterBuffer);
                service.scheduleAtFixedRate(runnable, 0, 2, TimeUnit.SECONDS);

                Map<String, Set<String>> buffers = characterBuffer.target.getBuffers();
                if (!buffers.containsKey(characterBuffer.name)) {
                    buffers.put(characterBuffer.name, new HashSet<>());
                }
                buffers.get(characterBuffer.name).add(bufferId);
                characterBuffer.target.setBuffers(buffers);
                GameCharacterManager.saveCharacter(characterBuffer.target);
            }
        } else {
            //非连续效果，例如属性改变
            characterBuffer.apply();
        }
        //取消buffer
        long duration = (long) (characterBuffer.duration * 1000);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                characterBuffer.undo();
            }
        };
        timer.schedule(task, duration);
    }

}
