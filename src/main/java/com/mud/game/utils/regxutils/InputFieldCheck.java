package com.mud.game.utils.regxutils;

import org.springframework.beans.factory.annotation.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputFieldCheck {

    public static boolean isValidUserName(String username){
        /*
        * @ username 验证，要求必须为字母和数字的做和，长度介于4到12
        * */
        Pattern pattern = Pattern.compile("[A-Za-z0-9]{4,12}");
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public static boolean isValidPassword(String password){
        /*
        * @ password 验证，要求必须为数字和字母的组合且包含特殊符号
        * */
        Pattern pattern = Pattern.compile("^(?:\\d+|[a-zA-Z]+|[!@#$%^&*().+=|`~]+){6,32}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
