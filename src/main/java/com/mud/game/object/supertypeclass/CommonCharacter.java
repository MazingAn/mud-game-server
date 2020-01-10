package com.mud.game.object.supertypeclass;

import com.mud.game.commands.BaseCommand;
import com.mud.game.object.typeclass.WorldExitObject;
import com.mud.game.object.typeclass.WorldRoomObject;
import com.mud.game.worlddata.db.models.supermodel.BaseObject;
import org.json.JSONObject;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public int after_arm; //后天膂力
    public int after_bone; //先天根骨
    public int after_body; //先天身法
    public int after_smart; //先天悟性
    public int after_looks; //先天容貌
    public int after_lucky; //先天福缘
    //装备信息
    public List<String> equipments;
    public Map<String, String> equipped_equipments;
    //技能信息
    public List<String> skills;
    public Map<String, String[]> equipped_skills;
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

    public int getAfter_arm() {
        return after_arm;
    }

    public void setAfter_arm(int after_arm) {
        this.after_arm = after_arm;
    }

    public int getAfter_bone() {
        return after_bone;
    }

    public void setAfter_bone(int after_bone) {
        this.after_bone = after_bone;
    }

    public int getAfter_body() {
        return after_body;
    }

    public void setAfter_body(int after_body) {
        this.after_body = after_body;
    }

    public int getAfter_smart() {
        return after_smart;
    }

    public void setAfter_smart(int after_smart) {
        this.after_smart = after_smart;
    }

    public int getAfter_looks() {
        return after_looks;
    }

    public void setAfter_looks(int after_looks) {
        this.after_looks = after_looks;
    }

    public int getAfter_lucky() {
        return after_lucky;
    }

    public void setAfter_lucky(int after_lucky) {
        this.after_lucky = after_lucky;
    }

    public List<String> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<String> equipments) {
        this.equipments = equipments;
    }

    public Map<String, String> getEquipped_equipments() {
        return equipped_equipments;
    }

    public void setEquipped_equipments(Map<String, String> equipped_equipments) {
        this.equipped_equipments = equipped_equipments;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public Map<String, String[]> getEquipped_skills() {
        return equipped_skills;
    }

    public void setEquipped_skills(Map<String, String[]> equipped_skills) {
        this.equipped_skills = equipped_skills;
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