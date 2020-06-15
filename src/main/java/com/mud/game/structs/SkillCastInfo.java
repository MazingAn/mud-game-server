package com.mud.game.structs;

import com.mud.game.object.manager.SkillObjectManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.structs.CombatStatus;

import java.util.HashMap;
import java.util.Map;

public class SkillCastInfo {
    private String caller;
    private String target;
    private String skill;
    private String cast;
    private Map<String,Object> status;
    private String result;

    public SkillCastInfo() {
    }

    public SkillCastInfo(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String result) {
        this.caller = caller.getName();
        this.target = target.getName();
        this.skill = skillObject.getDataKey();
        this.cast = SkillObjectManager.getCastMessage(caller, target, skillObject);
        this.status = new HashMap<>();
        status.put(caller.getId(), new CombatStatus(caller));
        status.put(target.getId(), new CombatStatus(target));
        this.result = result;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public Map<String, Object> getStatus() {
        return status;
    }

    public void setStatus(Map<String, Object> status) {
        this.status = status;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
