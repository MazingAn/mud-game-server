package com.mud.game.statements.buffers;

import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.buffers.BufferManager;
import com.mud.game.utils.regxutils.StringChecker;

import java.util.*;

public class CharacterBuffer {
    public String name;
    public float duration;
    public int addedCount;
    public int maxAdd;
    public boolean goodBuffer;
    public String attrKey;
    public Object changedValue;
    public CommonCharacter target;
    public SkillObject skillObject;
    public String bufferId;


    public CharacterBuffer(String name, float duration, int maxAdd,
                           boolean goodBuffer, String attrKey, Object changedValue,
                           CommonCharacter target, SkillObject skillObject) {
        this.name = name;
        this.duration = duration;
        this.maxAdd = maxAdd;
        this.goodBuffer = goodBuffer;
        this.attrKey = attrKey;
        this.changedValue = changedValue;
        this.target = target;
        this.skillObject = skillObject;
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
        Object change = changedValue;
        boolean isNumber = StringChecker.isNumber(changedValue.toString());
        if (isNumber)
            change = goodBuffer ? (float) changedValue * -1 : (float) changedValue * 1;
        if (attrKey.equals("canAttck")) {
            change = true;
        }
        GameCharacterManager.changeStatus(target, attrKey, change);

        Map<String, Set<String>> buffers = target.getBuffers();
        if (buffers.containsKey(name)) {
            buffers.get(name).remove(bufferId);
        }
        target.setBuffers(buffers);
        GameCharacterManager.saveCharacter(target);
    }


}
