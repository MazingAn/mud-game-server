package com.mud.game.statements;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public abstract class BasePassiveSkillStatement {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    private CommonCharacter caller;
    private CommonCharacter target;
    private SkillObject skillObject;
    private String key;
    private String[] args;

    public BasePassiveSkillStatement(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        this.caller = caller;
        this.target = target;
        this.key = key;
        this.args = args;
        this.skillObject = skillObject;
        try{
            calculus();
        }catch (Exception e){
            logger.warn(String.format("%s 在执行技能 %s 的时候出现了错误", caller.getName(), key ));
        }
    }

    public abstract void calculus() throws JSONException, JsonProcessingException;

    public CommonCharacter getCaller() {
        return caller;
    }

    public void setCaller(CommonCharacter caller) {
        this.caller = caller;
    }

    public CommonCharacter getTarget() {
        return target;
    }

    public void setTarget(CommonCharacter target) {
        this.target = target;
    }

    public SkillObject getSkillObject() {
        return skillObject;
    }

    public void setSkillObject(SkillObject skillObject) {
        this.skillObject = skillObject;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
