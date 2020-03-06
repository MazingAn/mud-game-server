package com.mud.game.structs;

import com.mud.game.object.manager.SkillObjectManager;
import com.mud.game.object.typeclass.SkillObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SimpleSkill {
    private String dbref;
    private String icon;
    private String name;
    private String desc;
    private Set<String> sub_skill;
    private boolean passive;
    private int level;
    private String function_type;
    private String category_type;
    private boolean is_equipped;
    private float action_time;
    private Set<SkillEffect> effects;
    private Set<String> weapons;
    private float cd_remain;
    private List<EmbeddedCommand> cmds;

    public SimpleSkill(SkillObject skillObject) {
        this.dbref = skillObject.getId();
        this.icon = skillObject.getIcon();
        this.name = skillObject.getName();
        this.sub_skill = skillObject.getSubSKills();
        this.passive = skillObject.isPassive();
        this.level = skillObject.getLevel();
        this.function_type = skillObject.getFunctionType();
        this.action_time = skillObject.getActionTime();
        this.effects = skillObject.getEffects();
        this.weapons = skillObject.getWeaponType();
        this.category_type = skillObject.getCategoryType();
        this.cd_remain = SkillObjectManager.calculusRemainCd(skillObject);
        this.cmds = new ArrayList<>();
    }

    public String getDbref() {
        return dbref;
    }

    public void setDbref(String dbref) {
        this.dbref = dbref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getSub_skill() {
        return sub_skill;
    }

    public void setSub_skill(Set<String> sub_skill) {
        this.sub_skill = sub_skill;
    }

    public boolean isPassive() {
        return passive;
    }

    public void setPassive(boolean passive) {
        this.passive = passive;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getFunction_type() {
        return function_type;
    }

    public void setFunction_type(String function_type) {
        this.function_type = function_type;
    }

    public boolean isIs_equipped() {
        return is_equipped;
    }

    public void setIs_equipped(boolean is_equipped) {
        this.is_equipped = is_equipped;
    }

    public float getAction_time() {
        return action_time;
    }

    public void setAction_time(float action_time) {
        this.action_time = action_time;
    }

    public Set<SkillEffect> getEffects() {
        return effects;
    }

    public void setEffects(Set<SkillEffect> effects) {
        this.effects = effects;
    }

    public Set<String> getWeapons() {
        return weapons;
    }

    public void setWeapons(Set<String> weapons) {
        this.weapons = weapons;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCategory_type() {
        return category_type;
    }

    public void setCategory_type(String category_type) {
        this.category_type = category_type;
    }

    public float getCd_remain() {
        return cd_remain;
    }

    public void setCd_remain(float cd_remain) {
        this.cd_remain = cd_remain;
    }

    public List<EmbeddedCommand> getCmds() {
        return cmds;
    }

    public void setCmds(List<EmbeddedCommand> cmds) {
        this.cmds = cmds;
    }
}
