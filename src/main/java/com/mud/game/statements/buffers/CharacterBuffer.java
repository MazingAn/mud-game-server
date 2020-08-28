package com.mud.game.statements.buffers;

import com.mud.game.algorithm.AttackAlgorithm;
import com.mud.game.algorithm.HarmInfo;
import com.mud.game.combat.CombatSense;
import com.mud.game.handler.CombatHandler;
import com.mud.game.messages.SkillCastMessage;
import com.mud.game.messages.ToastMessage;
import com.mud.game.net.session.GameSessionService;
import com.mud.game.object.manager.FightBufferScheduleManager;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.manager.PlayerScheduleManager;
import com.mud.game.object.manager.SkillObjectManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.buffers.BufferManager;
import com.mud.game.structs.SkillCastInfo;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.utils.regxutils.StringChecker;
import com.mud.game.utils.resultutils.GameWords;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.yeauty.pojo.Session;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;

public class CharacterBuffer {
    public String name;
    public float duration;
    public int addedCount;
    public int maxAdd;
    public boolean goodBuffer;
    public String attrKey;
    public Object changedValue;
    public CommonCharacter target;
    public CommonCharacter caller;
    public SkillObject skillObject;
    public String bufferId;
    public boolean persistent;

    public CharacterBuffer(String name, float duration, int maxAdd,
                           boolean goodBuffer, String attrKey, Object changedValue,
                           CommonCharacter target, SkillObject skillObject, boolean persistent, CommonCharacter caller) {
        this.name = name;
        this.duration = duration;
        this.maxAdd = maxAdd;
        this.goodBuffer = goodBuffer;
        this.attrKey = attrKey;
        this.changedValue = changedValue;
        this.target = target;
        this.caller = caller;
        this.skillObject = skillObject;
        this.persistent = persistent;
        this.bufferId = UUID.randomUUID().toString();
    }

    /**
     * buffer 应用buffer
     */
    public void apply() {
        try {

            Object change = changedValue;
            boolean isNumber = StringChecker.isNumber(changedValue.toString());
            if (isNumber)
                change = goodBuffer ? (float) changedValue * 1 : (float) changedValue * -1;
            GameCharacterManager.changeStatus(target, attrKey, change);


//            boolean isNumber = false;
//            float floatChangedValue = 0;
//            if(StringChecker.isNumber(changedValue.toString())) {
//                isNumber = true;
//                floatChangedValue = Float.parseFloat(changedValue.toString());
//            }
//            // 应用buffer
//            if(isNumber){
//                if(goodBuffer) {
//                    GameCharacterManager.changeStatus(target, attrKey, floatChangedValue * skillObject.getLevel());
//                }else {
//                    GameCharacterManager.changeStatus(target, attrKey, floatChangedValue * -1 * skillObject.getLevel());
//                }
//            }else{
//                GameCharacterManager.changeStatus(target, attrKey, changedValue);
//            }

            Map<String, Set<String>> buffers = target.getBuffers();
            if (!buffers.containsKey(name)) {
                buffers.put(name, new HashSet<>());
            }
            buffers.get(name).add(bufferId);
            target.setBuffers(buffers);
            GameCharacterManager.saveCharacter(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * buffer 取消buffer
     */
    public void undo() {
        //非连续效果，改变属性等
        if (!this.persistent) {
            Object change = changedValue;
            boolean isNumber = StringChecker.isNumber(changedValue.toString());
            if (isNumber)
                change = goodBuffer ? (float) changedValue * -1 : (float) changedValue * 1;

            boolean isBoolean = StringChecker.isBoolean(changedValue);
            if (isBoolean) {
                boolean booleanValue = ((Boolean) change).booleanValue();
                change = !booleanValue;
            }
            GameCharacterManager.changeStatus(target, attrKey, change);
        } else {
            // 连续效果：中毒掉血
            FightBufferScheduleManager.shutdownExecutorByBufferId(bufferId);
        }


        Map<String, Set<String>> buffers = target.getBuffers();
        if (buffers.containsKey(name)) {
            buffers.get(name).remove(bufferId);
        }
        target.setBuffers(buffers);
        GameCharacterManager.saveCharacter(target);
    }

    public Runnable scheduleAtFixedRate(CommonCharacter target, boolean goodBuffer, String attrKey, Object changedValue) {
        Runnable runnable = new Runnable() {
            CombatSense sense = CombatHandler.getCombatSense(caller.getId());
            SkillCastInfo skillCastInfo = null;

            @Override
            public void run() {
                //应用伤害
                GameCharacterManager.changeStatus(target, "hp", goodBuffer ? changedValue : Float.parseFloat(changedValue.toString()) * -1);
                //构建战斗输出
                if (target.getHp() > 0) {
                    skillCastInfo = new SkillCastInfo(caller, target, skillObject, target.getName() + "中毒失去" + changedValue + "气血!!!!!!!!!!!!！");
                    sense.msgContents(new SkillCastMessage(skillCastInfo));
                }
                //更新同步数据
                GameCharacterManager.saveCharacter(target);

            }

        };
        return runnable;
    }
}
