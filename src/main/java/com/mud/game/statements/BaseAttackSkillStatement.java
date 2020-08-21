package com.mud.game.statements;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.combat.FighterManager;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.server.ServerManager;
import com.mud.game.worlddata.db.models.GameSetting;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**基本攻击函数
 * 所有的攻击函数必须继承这个类
 * */
public abstract class BaseAttackSkillStatement {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private CommonCharacter caller;
    private CommonCharacter target;
    private SkillObject skillObject;
    private String key;
    private String[] args;

    /**
     * 攻击技能基类的构造函数
     * @param  caller  调用者
     * @param target 受攻击者
     * @param skillObject 使用的技能
     * @param key 技能函数的名称
     * @param args 技能函数的参数列表
     * */
    public BaseAttackSkillStatement(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        this.caller = caller;
        this.target = target;
        this.key = key;
        this.args = args;
        this.skillObject = skillObject;
        try{
            if(caller.isCanCombat())
                attack();
            else
                caller.msg(new ToastMessage("你现在的状态，无法进行战斗！"));
        }catch (Exception e){
            logger.warn(String.format("%s 在执行技能 %s 的时候出现了错误", caller.getName(), key ));
        }
    }

    /**
     * 攻击函数
     * */
    public abstract void attack() throws JSONException, JsonProcessingException;


    /**
     * 技能释放之前执行的操作
     * */
    public void beforeAttack(){
        if(!skillObject.getDataKey().equals(ServerManager.gameSetting.getDefaultSkill())){
            caller.autoCombatPause = true;
            GameCharacterManager.saveCharacter(caller);
        }
    }

    /**
     * 技能释放之后执行的操作
     * */
    public void afterAttack(){
        caller.autoCombatPause = false;
        GameCharacterManager.saveCharacter(caller);
    }

    /** Getter Setter 方法 */
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
