package com.mud.game.utils;

import com.mud.game.object.supertypeclass.CommonCharacter;

import java.util.ArrayList;
import java.util.List;

public class StateConstants {
    /**
     * 不可移动
     */
    public static List<String> CHECK_GOTO_ROOM_STATE = new ArrayList<String>() {
        {
            CHECK_GOTO_ROOM_STATE.add("眩晕");
            CHECK_GOTO_ROOM_STATE.add("倒地");
            CHECK_GOTO_ROOM_STATE.add("忙乱");
        }
    };
    /**
     * 不可逃跑
     */
    public static List<String> CHECK_TAOPAO_STATE = new ArrayList<String>() {
        {
            CHECK_TAOPAO_STATE.add("眩晕");
            CHECK_TAOPAO_STATE.add("倒地");
            CHECK_TAOPAO_STATE.add("忙乱");
        }
    };
    /**
     * 不可攻击
     */
    public static List<String> CHECK_ATTACK_STATE = new ArrayList<String>() {
        {
            CHECK_ATTACK_STATE.add("眩晕");
            CHECK_ATTACK_STATE.add("倒地");
            CHECK_ATTACK_STATE.add("忙乱");
        }
    };
    /**
     * 不可闪避
     */
    public static List<String> CHECK_PRECISE_STATE = new ArrayList<String>() {
        {
            CHECK_PRECISE_STATE.add("眩晕");
            CHECK_PRECISE_STATE.add("倒地");
            CHECK_PRECISE_STATE.add("解牛");
        }
    };
    /**
     * 不可招架
     */
    public static List<String> CHECK_ZHAOJIA_STATE = new ArrayList<String>() {
        {
            CHECK_ZHAOJIA_STATE.add("眩晕");
            CHECK_ZHAOJIA_STATE.add("倒地");
            CHECK_ZHAOJIA_STATE.add("解牛");
            CHECK_ZHAOJIA_STATE.add("无法招架");
        }
    };


    /**
     * 降低攻速
     */
    public static List<String> CHECK_ATTACK_SPEED_REDUCE_STATE = new ArrayList<String>() {
        {
            CHECK_ATTACK_SPEED_REDUCE_STATE.add("呆若木鸡");
        }
    };

    /**
     * 必暴击技能名称
     */
    public static List<String> CRITICAL_SKILL_NAME = new ArrayList<String>() {
        {
            CRITICAL_SKILL_NAME.add("宁氏一剑");
            CRITICAL_SKILL_NAME.add("绝户");
        }
    };

    /**
     * 血魔刀法—吸
     * 增加吸血10%，持续8秒
     */
    public static String CHECK_XUEMODAOFAXI_STATE = "吸血";

    /**
     * 判断该对象是否没有此状态
     *
     * @param commonCharacter 对象
     * @param checkState      状态
     * @return
     */
    public static boolean checkState(CommonCharacter commonCharacter, List<String> checkState) {
        for (String str : checkState) {
            if (!checkBuffer(commonCharacter, str)) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkBuffer(CommonCharacter commonCharacter, String bufferName) {
        if (commonCharacter.getBuffers().containsKey(bufferName)) {
            if (commonCharacter.getBuffers().get(bufferName) != null && commonCharacter.getBuffers().get(bufferName).size() > 0) {
                return false;
            }
        }
        return true;
    }
}
