package com.mud.game.structs;

import com.mud.game.messages.ObjectMoveIn;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Family;
import com.mud.game.worlddata.db.models.PlayerTitle;
import com.mud.game.worlddata.db.models.School;
import com.mud.game.worldrun.db.mappings.MongoMapper;

import java.util.HashMap;
import java.util.Map;

public class PlayerCharacterStatus {
    private Map<String, SimpleStatus> status;

    public PlayerCharacterStatus(PlayerCharacter playerCharacter) {
        setAllStatus(playerCharacter);
    }

    public Map<String, SimpleStatus> getStatus() {
        return status;
    }

    public void setStatus(Map<String, SimpleStatus> status) {
        this.status = status;
    }

    private void setAllStatus(PlayerCharacter playerCharacter){
        status = new HashMap<>();
        int order = 0;
        status.put("level", new SimpleStatus("level", "等级", playerCharacter.getLevel(), ++order));
        status.put("age", new SimpleStatus("age", "年龄", playerCharacter.getAge(), ++order));
        status.put("exp", new SimpleStatus("exp", "经验", playerCharacter.getExp(), ++order));
        status.put("limit_exp", new SimpleStatus("exp", "经验上限", playerCharacter.getLimitExp(), ++order));
        status.put("limit_hp", new SimpleStatus("limit_hp", "气血上限", playerCharacter.getLimit_hp(), ++order));
        status.put("max_hp", new SimpleStatus("max_hp", "最大气血", playerCharacter.getMax_hp(), ++order));
        status.put("hp", new SimpleStatus("hp", "气血", playerCharacter.getHp(), ++order));
        status.put("limit_mp", new SimpleStatus("limit_mp", "内力上限", playerCharacter.getLimit_mp(), ++order));
        status.put("max_mp", new SimpleStatus("max_mp", "最大内力", playerCharacter.getMax_mp(), ++order));
        status.put("mp", new SimpleStatus("mp", "内力", playerCharacter.getMp(), ++order));
        status.put("gender", new SimpleStatus("gender", "性别", playerCharacter.getGender(), ++order));
        status.put("arm", new SimpleStatus("arm", "先天膂力", playerCharacter.getArm(), ++order));
        status.put("body", new SimpleStatus("body", "先天身法", playerCharacter.getBody(), ++order));
        status.put("bone", new SimpleStatus("bone", "先天根骨", playerCharacter.getBone(), ++order));
        status.put("smart", new SimpleStatus("smart", "先天悟性", playerCharacter.getSmart(), ++order));
        status.put("looks", new SimpleStatus("looks", "先天容貌", playerCharacter.getLooks(), ++order));
        status.put("lucky", new SimpleStatus("lucky", "先天福缘", playerCharacter.getLucky(), ++order));
        status.put("after_arm", new SimpleStatus("after_arm", "后天膂力", playerCharacter.getAfterArm(), ++order));
        status.put("after_body", new SimpleStatus("after_body", "后天身法", playerCharacter.getAfterBody(), ++order));
        status.put("after_bone", new SimpleStatus("after_bone", "后天根骨", playerCharacter.getAfterBone(), ++order));
        status.put("after_smart", new SimpleStatus("after_smart", "后天悟性", playerCharacter.getAfterSmart(), ++order));
        status.put("after_lucky", new SimpleStatus("after_lucky", "后天福缘", playerCharacter.getAfterLucky(), ++order));
        status.put("after_looks", new SimpleStatus("after_looks", "后天容貌", playerCharacter.getAfterLooks(), ++order));
        status.put("potential", new SimpleStatus("potential", "潜能", playerCharacter.getPotential(), ++order));
        status.put("energy", new SimpleStatus("energy", "精力", playerCharacter.getEnnergy(), ++order));
        status.put("school", new SimpleStatus("school", "门派", getSchoolDisplay(playerCharacter.getSchool()), ++order));
        status.put("teacher", new SimpleStatus("teacher", "师承", getTeacherDisplay(playerCharacter.getTeacher()), ++order));
        status.put("contribution", new SimpleStatus("contribution", "门贡", playerCharacter.getContribution(), ++order));
        status.put("jian_xishu", new SimpleStatus("jian_xishu", "剑法专精", playerCharacter.getJianXishu(), ++order));
        status.put("dao_xishu", new SimpleStatus("dao_xishu", "刀法专精", playerCharacter.getDaoXiShu(), ++order));
        status.put("quan_xishu", new SimpleStatus("quan_xishu", "拳脚专精", playerCharacter.getQuanXishu(), ++order));
        status.put("qimen_xishu", new SimpleStatus("qimen_xishu", "奇门专精", playerCharacter.getQiMenXishu(), ++order));
        status.put("neili_xishu", new SimpleStatus("neili_xishu", "内功专精", playerCharacter.getNeigongXishu(), ++order));
        status.put("qinggong_xishu", new SimpleStatus("qinggong_xishu", "轻功专精", playerCharacter.getQingGongXishu(), ++order));
        status.put("du_xishu", new SimpleStatus("du_xishu", "毒功专精", playerCharacter.getDuXiShu(), ++order));
        status.put("yi_xishu", new SimpleStatus("yi_xishu", "医术专精", playerCharacter.getYiXiShu(), ++order));
        status.put("zaxue_xishu", new SimpleStatus("zaxue_xishu", "杂学专精", playerCharacter.getZaXueXiShu(), ++order));
        status.put("dushu_xishu", new SimpleStatus("dushu_xishu", "学习专精", playerCharacter.getXueXiXishu(), ++order));
        status.put("family", new SimpleStatus("family", "天赋", getFamilyDisplay(playerCharacter.getFamily()), ++order));
        status.put("good_and_evil", new SimpleStatus("good_and_evil", "善恶", playerCharacter.getGoodAndEvil(), ++order));
        status.put("tili", new SimpleStatus("tili", "体力", playerCharacter.getTili(), ++order));
        status.put("xiayi", new SimpleStatus("xiayi", "侠义", playerCharacter.getXiayi(), ++order));
        status.put("can_double_equip", new SimpleStatus("can_double_equip", "能否双持", playerCharacter.canDoubleEquip(), ++order));

        Map<String,Map<String, Object>> customAttr = playerCharacter.getCustomerAttr();
        // 加载自定义属性
        for(String attrKey : customAttr.keySet()){
            Map content = customAttr.get(attrKey);
            Object value = content.get("value");
            String name = (String)content.get("name");
            status.put(attrKey, new SimpleStatus(attrKey, name, value, ++order));
        }
    }

    private String getSchoolDisplay(String schoolKey) {
        if(schoolKey!= null && !(schoolKey.trim().equals(""))){
            School school = DbMapper.schoolRepository.findSchoolByDataKey(schoolKey);
            return school.getName();
        }
        return "无门无派";
    }


    private String getFamilyDisplay(String familyKey){
        if(familyKey!=null && !(familyKey.trim().equals(""))){
            Family family = DbMapper.familyRepository.findFamilyByDataKey(familyKey);
            return family.getName();
        }
        return "无";
    }

    private String getTeacherDisplay(String teacherKey) {
        if(teacherKey!=null && !(teacherKey.trim().equals(""))){
            WorldNpcObject npc = MongoMapper.worldNpcObjectRepository.findWorldNpcObjectByDataKey(teacherKey);
            return npc.getName();
        }
        return "无";
    }

}
