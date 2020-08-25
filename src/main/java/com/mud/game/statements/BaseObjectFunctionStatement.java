package com.mud.game.statements;

import com.mud.game.messages.ToastMessage;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.NormalObjectObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseObjectFunctionStatement {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private CommonCharacter caller;
    private CommonCharacter target;
    private NormalObjectObject normalObjectObject;
    private String key; //goto("XIAN_ZHONGLOU"); key : goto
    private String[] args; //goto("XIAN_ZHONGLOU", 10); args  : ["XIAN_ZHONGLOU", 10]

    public BaseObjectFunctionStatement(CommonCharacter caller, CommonCharacter target, NormalObjectObject normalObjectObject, String key, String[] args) {
        this.caller = caller;
        this.target = target;
        this.normalObjectObject = normalObjectObject;
        this.key = key;
        this.args = args;
        try {
            execute();
        } catch (Exception e) {
            logger.warn(String.format("%s 在使用物品%s 的时候出现了错误", caller.getName(), key));
        }
    }

    public abstract void execute();

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

    public NormalObjectObject getNormalObjectObject() {
        return normalObjectObject;
    }

    public void setNormalObjectObject(NormalObjectObject normalObjectObject) {
        this.normalObjectObject = normalObjectObject;
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
