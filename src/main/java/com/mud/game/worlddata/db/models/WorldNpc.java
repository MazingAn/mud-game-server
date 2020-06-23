package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;
import com.mud.game.worlddata.db.models.supermodel.BaseObject;

import javax.persistence.*;

@Entity
@Mark(name="世界NPC")
public class WorldNpc extends BaseObject {
    /**世界npc； 包含Monster， Npc*/
    //所处位置（房间）
    @Mark(name="所在房间", link = "worldRoom")
    private String location;

    //模型类型 Monster, Npc
    @Mark(name="模版")
    private String model;

    //能否攻击
    @Mark(name="能否攻击")
    private boolean canAttack;

    // 性别 （男，女，公公， 动物）
    @Mark(name="性别")
    private String gender;

    // 等级
    @Column(columnDefinition = "int default 1")
    @Mark(name="等级")
    private int level;

    // 所属门派
    @Mark(name="门派")
    private String school;

    //是否能够教学(是否能够在这里拜师）
    @Mark(name="是否收徒")
    private boolean isTeacher;

    @Mark(name = "是否是传送NPC")
    private boolean transfer;

    //NPC的称号
    @Mark(name="称号")
    private String title;

    //NPC的门派称号
    @Mark(name="门派称号")
    private String schoolTitle;

    //NPC是否能够通过物品交换换取技能
    @Mark(name="通过物品学习")
    private boolean canLearnByObject;

    //Npc收徒条件
    @Mark(name="收徒条件")
    @Column(length = 512)
    private String teachCondition;

    // 杀死NPC得到的经验
    @Mark(name="提供经验")
    private int giveExp;

    // 杀死NPC得到的善恶值
    @Mark(name="提供善恶值")
    private int giveEvil;

    // NPc的重生时间
    @Mark(name="复活时间")
    private int rebornTime;

    //NPC出现的条件
    @Mark(name="显示条件")
    private String showCondition;

    //NPC的图标资源
    @Mark(name="图标")
    private String icon;


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public boolean isTeacher() {
        return isTeacher;
    }

    public void setTeacher(boolean teacher) {
        isTeacher = teacher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSchoolTitle() {
        return schoolTitle;
    }

    public void setSchoolTitle(String schoolTitle) {
        this.schoolTitle = schoolTitle;
    }

    public boolean isCanLearnByObject() {
        return canLearnByObject;
    }

    public void setCanLearnByObject(boolean canLearnByObject) {
        this.canLearnByObject = canLearnByObject;
    }

    public String getTeachCondition() {
        return teachCondition;
    }

    public void setTeachCondition(String teachCondition) {
        this.teachCondition = teachCondition;
    }

    public int getGiveExp() {
        return giveExp;
    }

    public void setGiveExp(int giveExp) {
        this.giveExp = giveExp;
    }

    public int getGiveEvil() {
        return giveEvil;
    }

    public void setGiveEvil(int giveEvil) {
        this.giveEvil = giveEvil;
    }

    public int getRebornTime() {
        return rebornTime;
    }

    public void setRebornTime(int rebornTime) {
        this.rebornTime = rebornTime;
    }

    public String getShowCondition() {
        return showCondition;
    }

    public void setShowCondition(String showCondition) {
        this.showCondition = showCondition;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isTransfer() {
        return transfer;
    }

    public void setTransfer(boolean transfer) {
        this.transfer = transfer;
    }
}
