package com.mud.game.handler;

import com.mud.game.structs.SimpleStatus;
import org.apache.commons.collections4.map.HashedMap;

import java.util.Map;

public class StatusHandler {

    public static Map<String, String> attrMapping = new HashedMap<>();

    public static void initAttrMapping(){
        attrMapping.put("level", "等级");
        attrMapping.put("age", "年龄");
        attrMapping.put("exp", "经验");
        attrMapping.put("limit_exp", "经验上限");
        attrMapping.put("limit_hp", "气血上限");
        attrMapping.put("max_hp", "最大气血");
        attrMapping.put("hp", "气血");
        attrMapping.put("limit_mp", "内力上限");
        attrMapping.put("max_mp", "最大内力");
        attrMapping.put("mp", "内力");
        attrMapping.put("gender", "性别");
        attrMapping.put("arm",  "先天膂力");
        attrMapping.put("body", "先天身法");
        attrMapping.put("bone", "先天根骨");
        attrMapping.put("smart", "先天悟性");
        attrMapping.put("looks",  "先天容貌");
        attrMapping.put("lucky",  "先天福缘");
        attrMapping.put("after_arm",  "后天膂力");
        attrMapping.put("after_body", "后天身法");
        attrMapping.put("after_bone", "后天根骨");
        attrMapping.put("after_smart", "后天悟性");
        attrMapping.put("after_lucky", "后天福缘");
        attrMapping.put("after_looks", "后天容貌");
        attrMapping.put("potential", "潜能");
        attrMapping.put("energy", "精力");
        attrMapping.put("school", "门派");
        attrMapping.put("teacher", "师承");
        attrMapping.put("contribution",  "门贡");
        attrMapping.put("jian_xishu", "剑法专精");
        attrMapping.put("dao_xishu", "刀法专精");
        attrMapping.put("quan_xishu", "拳脚专精");
        attrMapping.put("qimen_xishu", "奇门专精");
        attrMapping.put("neili_xishu", "内功专精");
        attrMapping.put("qinggong_xishu", "轻功专精");
        attrMapping.put("du_xishu", "毒功专精");
        attrMapping.put("yi_xishu", "医术专精");
        attrMapping.put("zaxue_xishu", "杂学专精");
        attrMapping.put("dushu_xishu", "学习专精");
        attrMapping.put("family", "天赋");
        attrMapping.put("good_and_evil", "善恶");
        attrMapping.put("tili", "体力");
        attrMapping.put("xiayi", "侠义");
        attrMapping.put("can_double_equip", "能否双持");

    }

}