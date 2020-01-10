package com.mud.game.object.typeclass;

import javax.persistence.Id;
import java.util.Map;
import java.util.Set;

public class SkillObject {
    @Id
    private String id;
    private String dataKey;
    private String name;
    private String description;
    private String message;
    private int quality;
    private int level;
    private float cd;
    private float actionTime;
    private boolean passive;
    private String skillFunction;
    private String learnCondition;
    private Map<String, Object> effects;
    private int expandMp;
    private Set<String> positions;
    private Set<SkillObject> subSKills;
    private String weaponType;
    private String icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public float getCd() {
        return cd;
    }

    public void setCd(float cd) {
        this.cd = cd;
    }

    public float getActionTime() {
        return actionTime;
    }

    public void setActionTime(float actionTime) {
        this.actionTime = actionTime;
    }

    public boolean isPassive() {
        return passive;
    }

    public void setPassive(boolean passive) {
        this.passive = passive;
    }

    public String getSkillFunction() {
        return skillFunction;
    }

    public void setSkillFunction(String skillFunction) {
        this.skillFunction = skillFunction;
    }

    public String getLearnCondition() {
        return learnCondition;
    }

    public void setLearnCondition(String learnCondition) {
        this.learnCondition = learnCondition;
    }

    public Map<String, Object> getEffects() {
        return effects;
    }

    public void setEffects(Map<String, Object> effects) {
        this.effects = effects;
    }

    public int getExpandMp() {
        return expandMp;
    }

    public void setExpandMp(int expandMp) {
        this.expandMp = expandMp;
    }

    public Set<String> getPositions() {
        return positions;
    }

    public void setPositions(Set<String> positions) {
        this.positions = positions;
    }

    public Set<SkillObject> getSubSKills() {
        return subSKills;
    }

    public void setSubSKills(Set<SkillObject> subSKills) {
        this.subSKills = subSKills;
    }

    public String getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(String weaponType) {
        this.weaponType = weaponType;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}