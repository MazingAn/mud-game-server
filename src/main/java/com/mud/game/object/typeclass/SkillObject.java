package com.mud.game.object.typeclass;

import com.mud.game.structs.SkillEffect;

import javax.persistence.Id;
import java.util.*;

public class SkillObject {
    @Id
    private String id;
    private String dataKey;
    private String name;
    private String description;
    private String message;
    private String basicSkill;
    private int quality;
    private int level;
    private float cd;
    private float actionTime;
    private boolean passive;
    private String skillFunction;
    private String learnCondition;
    private Set<SkillEffect> effects;
    private int expandMp;
    private Set<String> positions;
    private Set<String> subSKills;
    private Set<String> weaponType;
    private String icon;
    private String owner;
    private String categoryType;
    private String functionType;
    private Set<String> equippedPositions;
    private Long cdFinishTime;
    private int currentPotentialBalance; // 当前级别所充值的潜能
    private int chargedPotential; // 累计充值的潜能

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

    public String getBasicSkill() {
        return basicSkill;
    }

    public void setBasicSkill(String basicSkill) {
        this.basicSkill = basicSkill;
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

    public Set<SkillEffect> getEffects() {
        return effects;
    }

    public void setEffects(Set<SkillEffect> effects) {
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

    public Set<String> getSubSKills() {
        if(subSKills == null) return new HashSet<String>();
        return subSKills;
    }

    public void setSubSKills(Set<String> subSKills) {
        this.subSKills = subSKills;
    }

    public Set<String> getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(Set<String> weaponType) {
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getFunctionType() {
        return functionType;
    }

    public void setFunctionType(String functionType) {
        this.functionType = functionType;
    }

    public Set<String> getEquippedPositions() {
        return equippedPositions;
    }

    public void setEquippedPositions(Set<String> equippedPositions) {
        this.equippedPositions = equippedPositions;
    }

    public Long getCdFinishTime() {
        return cdFinishTime;
    }

    public void setCdFinishTime(Long cdFinishTime) {
        this.cdFinishTime = cdFinishTime;
    }

    public int getCurrentPotentialBalance() {
        return currentPotentialBalance;
    }

    public void setCurrentPotentialBalance(int currentPotentialBalance) {
        this.currentPotentialBalance = currentPotentialBalance;
    }

    public int getChargedPotential() {
        return chargedPotential;
    }

    public void setChargedPotential(int chargedPotential) {
        this.chargedPotential = chargedPotential;
    }
}
