package com.mud.game.object.typeclass;

import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.utils.modelsutils.Mark;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WorldNpcObject extends CommonCharacter {
    // 绑定的事件
    public Set<String> events;
    // 绑定的对话
    public Set<String> dialogues;
    // 默认对话
    public String defaultDialogue;
    // 绑定的任务
    public Set<String> quests;
    public String showCondition;
    // 称号信息
    public String title;
    public String schoolTitle;
    // 能否教授技能
    public boolean teacher;
    // 是否是通过物品交换的方式教授技能
    public boolean learnByObject;
    // 收徒的条件
    public String teachCondition;
    // 角色复活时间
    public int rebornTime;
    // 角色是否能够被攻击
    public boolean canAttack;
    // npc的商店
    public Set<String> shops;
    // 是否可以传送玩家
    public boolean transfer;
    //犯罪值控制命令是否返回
    private Boolean crimeControlCmd;
    //犯罪值达到阈值攻击
    private Boolean canAttackByCrime;
    //是否游荡
    private Boolean canWanderRoom;

    public Set<String> getEvents() {
        return events;
    }

    public void setEvents(Set<String> events) {
        this.events = events;
    }

    public Set<String> getDialogues() {
        return dialogues;
    }

    public void setDialogues(Set<String> dialogues) {
        this.dialogues = dialogues;
    }

    public String getDefaultDialogue() {
        return defaultDialogue;
    }

    public void setDefaultDialogue(String defaultDialogue) {
        this.defaultDialogue = defaultDialogue;
    }

    public Set<String> getQuests() {
        return quests;
    }

    public void setQuests(Set<String> quests) {
        this.quests = quests;
    }

    public String getShowCondition() {
        return showCondition;
    }

    public void setShowCondition(String showCondition) {
        this.showCondition = showCondition;
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

    public boolean isTeacher() {
        return teacher;
    }

    public void setTeacher(boolean teacher) {
        this.teacher = teacher;
    }

    public boolean isLearnByObject() {
        return learnByObject;
    }

    public void setLearnByObject(boolean learnByObject) {
        this.learnByObject = learnByObject;
    }

    public String getTeachCondition() {
        return teachCondition;
    }

    public void setTeachCondition(String teachCondition) {
        this.teachCondition = teachCondition;
    }

    public int getRebornTime() {
        return rebornTime;
    }

    public void setRebornTime(int rebornTime) {
        this.rebornTime = rebornTime;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public Set<String> getShops() {
        if (shops == null) {
            shops = new HashSet<>();
        }
        return shops;
    }

    public void setShops(Set<String> shops) {
        this.shops = shops;
    }

    public boolean isTransfer() {
        return transfer;
    }

    public void setTransfer(boolean transfer) {
        this.transfer = transfer;
    }

    public Boolean getCrimeControlCmd() {
        return crimeControlCmd;
    }

    public void setCrimeControlCmd(Boolean crimeControlCmd) {
        this.crimeControlCmd = crimeControlCmd;
    }

    public Boolean getCanAttackByCrime() {
        return canAttackByCrime;
    }

    public void setCanAttackByCrime(Boolean canAttackByCrime) {
        this.canAttackByCrime = canAttackByCrime;
    }

    public Boolean getCanWanderRoom() {
        return canWanderRoom;
    }

    public void setCanWanderRoom(Boolean canWanderRoom) {
        this.canWanderRoom = canWanderRoom;
    }
}
