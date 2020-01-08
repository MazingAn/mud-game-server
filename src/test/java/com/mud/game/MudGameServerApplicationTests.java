package com.mud.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.condition.ConditionHandler;
import com.mud.game.utils.jsonutils.Attr2Map;
import com.mud.game.utils.passwordutils.ShaPassword;
import com.mud.game.utils.regxutils.InputFieldCheck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Objects;

class MudGameServerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void checkUsernameValidator() {
        String username = "1994andy";
        Assertions.assertTrue(InputFieldCheck.isValidUserName(username));
    }

    @Test
    void checkPasswordValidator(){
        String password = "test.password@90#$";
        Assertions.assertTrue(InputFieldCheck.isValidPassword(password));
    }

    @Test
    void checkPasswordShaEncryption(){
        String passwordOne = "test_password";
        String passwordTwo = "test_password";
        Assertions.assertEquals(Objects.requireNonNull(ShaPassword.encrypts(passwordOne)).length(), 32);
        Assertions.assertEquals(ShaPassword.encrypts(passwordOne), ShaPassword.encrypts(passwordTwo));
    }

    @Test
    void attr2Map() throws JsonProcessingException {
        String jsonAttr = "{\"attack\":{\"name\":\"攻击\",\"value\":20},\"defence\":{\"name\":\"防御\",\"value\":10},\"precise\":{\"name\":\"命中\",\"value\":10},\"dodge\":{\"name\":\"躲闪\",\"value\":0},\"parry\":{\"name\":\"招架\",\"value\":0},\"crit_rate\":{\"name\":\"暴击几率\",\"value\":0.1},\"attack_speed\":{\"name\":\"攻击速度\",\"value\":1},\"final_damage\":{\"name\":\"最终伤害\",\"value\":0},\"ignore_defence\":{\"name\":\"忽略防御\",\"value\":0},\"crit_damage\":{\"name\":\"暴击伤害\",\"value\":1.1},\"crit_scale\":{\"name\":\"暴击倍率\",\"value\":1},\"dagage_reduction\":{\"name\":\"伤害减免\",\"value\":0},\"resist_crit\":{\"name\":\"暴击抵抗\",\"value\":0},\"resist_xuanyuan\":{\"name\":\"眩晕抵抗\",\"value\":0},\"resist_busy\":{\"name\":\"忙乱抵抗\",\"value\":0},\"resist_dingshen\":{\"name\":\"定身抵抗\",\"value\":0},\"resist_daodo\":{\"name\":\"倒地抵抗\",\"value\":0},\"resist_zhongdu\":{\"name\":\"中毒抵抗\",\"value\":0},\"meditate_rate\":{\"name\":\"打坐效率\",\"value\":0},\"learn_rate\":{\"name\":\"学习效率\",\"value\":0},\"practice_rate\":{\"name\":\"练习效率\",\"value\":0},\"mining_rate\":{\"name\":\"挖矿效率\",\"value\":0},\"collect_rate\":{\"name\":\"采药效率\",\"value\":0},\"fishing_rate\":{\"name\":\"钓鱼效率\",\"value\":0}}";
        System.out.println(Attr2Map.transform(jsonAttr));
    }

    @Test
    void str2int(){
        System.out.println(Integer.parseInt("-3"));
    }

    @Test
    void splitStr() {System.out.println(Arrays.toString("is_male(\"男\");kal(1,2,3,4)".split(";")));}

}
