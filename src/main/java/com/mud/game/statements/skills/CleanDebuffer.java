package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.object.manager.FightBufferScheduleManager;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import com.mud.game.statements.buffers.CharacterBuffer;
import org.json.JSONException;

import java.util.Map;
import java.util.Set;

import static com.mud.game.statements.buffers.BufferManager.bufferMap;

/**
 * 解除负面buffer
 */
public class CleanDebuffer extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public CleanDebuffer(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        super(caller, target, skillObject, key, args);
    }

    @Override
    public void attack() throws JSONException, JsonProcessingException {
        CommonCharacter commonCharacter = GameCharacterManager.getCharacterObject(getCaller().getId());
        Map<String, Set<String>> map = commonCharacter.getBuffers();
        CharacterBuffer characterBuffer = null;
        for (String key : map.keySet()) {
            for (String characterBufferKey : map.get(key)) {
                characterBuffer = bufferMap.get(characterBufferKey);
                if (characterBuffer != null) {
                    if (!characterBuffer.goodBuffer) {
                        characterBuffer.undo();
                        FightBufferScheduleManager.shutdownExecutorByBufferId(characterBuffer.bufferId);
                    }
                }
            }
        }

    }
}