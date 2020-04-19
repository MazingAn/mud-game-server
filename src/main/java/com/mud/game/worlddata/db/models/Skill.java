package com.mud.game.worlddata.db.models;

import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

@Entity
public class Skill {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String dataKey;
    private String typeClass;
    private String name;
    private String basicSkill;
    @Column(length = 2048)
    private String description;
    private String school;
    private String message;
    private float cd;
    private float actionTime;
    private boolean passive;
    @Column(length = 4096)
    private String skillFunction;
    @Column(length = 2048)
    private String learnCondition;
    private int expandMp;
    private int quality;
    private String functionType; //功能类型
    private String categoryType; //分类类型
    private String positions; //可以装备到的位置
    @Column(length = 4096)
    private String subSkills; //包含的子技能
    @Column(length = 1024)
    private String weaponType; //技能适用于什么武器
    private  String icon; //技能图标

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public String getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(String typeClass) {
        this.typeClass = typeClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBasicSkill() {
        return basicSkill;
    }

    public void setBasicSkill(String basicSkill) {
        this.basicSkill = basicSkill;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public int getExpandMp() {
        return expandMp;
    }

    public void setExpandMp(int expandMp) {
        this.expandMp = expandMp;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public String getFunctionType() {
        return functionType;
    }

    public void setFunctionType(String functionType) {
        this.functionType = functionType;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getPositions() {
        return positions;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }

    public String getSubSkills() {
        return subSkills;
    }

    public void setSubSkills(String subSkills) {
        this.subSkills = subSkills;
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
}
