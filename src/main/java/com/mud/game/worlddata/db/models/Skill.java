package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

@Entity
@Mark(name = "技能")
public class Skill {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    @Mark(name="编号")
    private Long id;
    @Column(unique = true)
    @Mark(name="标识")
    private String dataKey;
    @Mark(name="类型类")
    private String typeClass;
    @Mark(name="名称")
    private String name;
    @Mark(name="父技能", link = "basicSkill")
    private String basicSkill;
    @Mark(name="描述")
    @Column(length = 2048)
    private String description;
    @Mark(name="门派", link = "school")
    private String school;
    @Mark(name="执行显示")
    private String message;
    @Mark(name="cd")
    private float cd;
    @Mark(name="执行时间")
    private float actionTime;
    @Mark(name="是否被动")
    private boolean passive;
    @Mark(name="技能函数")
    @Column(length = 4096)
    private String skillFunction;
    @Mark(name="学习条件")
    @Column(length = 2048)
    private String learnCondition;
    @Mark(name="消耗内力")
    private int expandMp;
    @Mark(name="品级")
    private int quality;
    @Mark(name="功能类型", link = "skillFunctionType")
    private String functionType; //功能类型
    @Mark(name="分类类型", link = "skillCategoryType")
    private String categoryType; //分类类型
    @Mark(name="装备位置", link = "skillPosition", multi = true)
    private String positions; //可以装备到的位置
    @Mark(name="子技能", link = "uniqueSkill", multi = true)
    @Column(length = 4096)
    private String subSkills; //包含的子技能
    @Mark(name="武器类型", link = "weaponType")
    @Column(length = 1024)
    private String weaponType; //技能适用于什么武器
    @Mark(name="图标")
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
