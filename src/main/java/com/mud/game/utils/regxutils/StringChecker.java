package com.mud.game.utils.regxutils;

public class StringChecker {

    /**
     * 判断一个字符串是不是数组，包含浮点类型
     */
    public static boolean isNumber(String str) {
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }

    /**
     * 判断一个object是不是boolean
     */
    public static boolean isBoolean(Object object) {
        return object instanceof Boolean;
    }

}
