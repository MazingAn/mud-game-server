package com.mud.game.object.supertypeclass;

import com.mud.game.commands.BaseCommand;
import com.mud.game.object.typeclass.WorldExitObject;
import com.mud.game.object.typeclass.WorldRoomObject;
import com.mud.game.worlddata.db.models.supermodel.BaseObject;
import org.json.JSONObject;

import javax.persistence.Id;
import java.util.*;

public class CommonCharacter extends BaseGameObject {
    //基本信息
    public String gender;
    public float age;
    public String school;
    //气血内力信息
    public int hp;
    public int max_hp;
    public int limit_hp;
    public int mp;
    public int max_mp;
    public int limit_mp;
    //先天后天属性
    public int arm; //先天膂力
    public int bone; //先天根骨
    public int body; //先天身法
    public int smart; //先天悟性
    public int looks; //先天容貌
    public int lucky; //先天福缘
    public int afterArm; //后天膂力
    public int afterBone; //先天根骨
    public int afterBody; //先天身法
    public int afterSmart; //先天悟性
    public int afterLooks; //先天容貌
    public int afterLucky; //先天福缘
    //装备信息
    public List<String> equipments;
    public Map<String, String> equippedEquipments;
    //技能信息
    public Set<String> skills;
    public Map<String, String[]> equippedSkills;
    // 角色的技能位置
    public Set<String> skillPositions;
    // 自定义属性
    public Map<String,Map<String, Object>> customerAttr;



    public CommonCharacter() {
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public float getAge() {
        return age;
    }

    public void setAge(float age) {
        this.age = age;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMax_hp() {
        return max_hp;
    }

    public void setMax_hp(int max_hp) {
        this.max_hp = max_hp;
    }

    public int getLimit_hp() {
        return limit_hp;
    }

    public void setLimit_hp(int limit_hp) {
        this.limit_hp = limit_hp;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getMax_mp() {
        return max_mp;
    }

    public void setMax_mp(int max_mp) {
        this.max_mp = max_mp;
    }

    public int getLimit_mp() {
        return limit_mp;
    }

    public void setLimit_mp(int limit_mp) {
        this.limit_mp = limit_mp;
    }

    public int getArm() {
        return arm;
    }

    public void setArm(int arm) {
        this.arm = arm;
    }

    public int getBone() {
        return bone;
    }

    public void setBone(int bone) {
        this.bone = bone;
    }

    public int getBody() {
        return body;
    }

    public void setBody(int body) {
        this.body = body;
    }

    public int getSmart() {
        return smart;
    }

    public void setSmart(int smart) {
        this.smart = smart;
    }

    public int getLooks() {
        return looks;
    }

    public void setLooks(int looks) {
        this.looks = looks;
    }

    public int getLucky() {
        return lucky;
    }

    public void setLucky(int lucky) {
        this.lucky = lucky;
    }



    public List<String> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<String> equipments) {
        this.equipments = equipments;
    }

    public int getAfterArm() {
        return afterArm;
    }

    public void setAfterArm(int afterArm) {
        this.afterArm = afterArm;
    }

    public int getAfterBone() {
        return afterBone;
    }

    public void setAfterBone(int afterBone) {
        this.afterBone = afterBone;
    }

    public int getAfterBody() {
        return afterBody;
    }

    public void setAfterBody(int afterBody) {
        this.afterBody = afterBody;
    }

    public int getAfterSmart() {
        return afterSmart;
    }

    public void setAfterSmart(int afterSmart) {
        this.afterSmart = afterSmart;
    }

    public int getAfterLooks() {
        return afterLooks;
    }

    public void setAfterLooks(int afterLooks) {
        this.afterLooks = afterLooks;
    }

    public int getAfterLucky() {
        return afterLucky;
    }

    public void setAfterLucky(int afterLucky) {
        this.afterLucky = afterLucky;
    }

    public Map<String, String> getEquippedEquipments() {
        return equippedEquipments;
    }

    public void setEquippedEquipments(Map<String, String> equippedEquipments) {
        this.equippedEquipments = equippedEquipments;
    }

    public void setSkills(Set<String> skills) {
        this.skills = skills;
    }

    public Set<String> getSkills() {
        return skills;
    }

    public Map<String, String[]> getEquippedSkills() {
        return equippedSkills;
    }

    public void setEquippedSkills(Map<String, String[]> equippedSkills) {
        this.equippedSkills = equippedSkills;
    }

    public Set<String> getSkillPositions() {
        return skillPositions;
    }

    public void setSkillPositions(Set<String> skillPositions) {
        this.skillPositions = skillPositions;
    }

    public Map<String, Map<String, Object>> getCustomerAttr() {
        return customerAttr;
    }

    public void setCustomerAttr(Map<String, Map<String, Object>> customerAttr) {
        this.customerAttr = customerAttr;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
