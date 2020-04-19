package com.mud.game.algorithm;

import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.SkillObject;

public class CommonAlgorithm {
    /*
    * @ 玩家和npc通用的算法
    * */

    public static void resetInbornAttrs(CommonCharacter character){
        /*
        * 重置角色的先天属性
        * */

        // 臂力影响攻击
        applyInbornAttr(character, "arm", "after_arm", "attack", 1f, 0.2f);
        // 臂力影响招架
        applyInbornAttr(character, "arm", "after_arm", "parry", 0.5f, 1f);

        // 根骨影响最大气血上限
        applyInbornAttr(character, "bone", "after_bone", "limit_hp", 5f, 1f);
        // 根骨影响防御
        applyInbornAttr(character, "bone", "after_bone", "defence", 1f, 0.05f);

        // 身法影响闪避
        applyInbornAttr(character, "body", "after_body", "dodge", 0.5f, 0.05f);
        // 身法影响攻击速度
        applyInbornAttr(character, "body", "after_body", "attack_speed", 0.5f, 0.025f);

        // 悟性影响命中
        applyInbornAttr(character, "smart", "after_smart", "precise", 0.5f, 0.05f);
        // 悟性影响打坐效率
        applyInbornAttr(character, "smart", "after_smart", "meditate_rate", 0.02f, 0.01f);
        // 悟性影响学习效率
        applyInbornAttr(character, "smart", "after_smart", "learn_rate", 0.01f, 0.01f);
        // 悟性影响练习效率
        applyInbornAttr(character, "smart", "after_smart", "practice_rate", 0.01f, 0.01f);

        // 福缘影响暴击几率
        applyInbornAttr(character, "lucky", "after_lucky", "crit_rate", 0.1f, 0.1f);
        // 福缘影响暴击倍率
        applyInbornAttr(character, "lucky", "after_lucky", "crit_scale", 0.1f, 0.1f);

    }

    public static void onAfterBoneChange(CommonCharacter character, int changedValue){
        // 根骨影响最大气血上限
        applyAcquireAttr(character, "bone", changedValue, "limit_hp", 5f, 1f);
        // 根骨影响防御
        applyAcquireAttr(character, "bone", changedValue, "defence", 1f, 0.05f);
    }

    public static void onAfterBodyChange(CommonCharacter character, int changedValue){
        // 身法影响闪避
        applyAcquireAttr(character, "body", changedValue, "dodge", 0.5f, 0.05f);
        // 身法影响攻击速度
        applyAcquireAttr(character, "body", changedValue, "attack_speed", 0.5f, 0.025f);
    }

    public static void onAfterArmChange(CommonCharacter character, int changedValue){
        // 臂力影响攻击
        applyAcquireAttr(character, "arm", changedValue, "attack", 1f, 0.2f);
        // 臂力影响招架
        applyAcquireAttr(character, "arm", changedValue, "parry", 0.5f, 1f);
    }

    public static void onAfterSmartChange(CommonCharacter character, int changedValue){
        // 悟性影响命中
        applyAcquireAttr(character, "smart", changedValue, "precise", 0.5f, 0.05f);
        // 悟性影响打坐效率
        applyAcquireAttr(character, "smart", changedValue, "meditate_rate", 0.02f, 0.01f);
        // 悟性影响学习效率
        applyAcquireAttr(character, "smart", changedValue, "learn_rate", 0.01f, 0.01f);
        // 悟性影响练习效率
        applyAcquireAttr(character, "smart", changedValue, "practice_rate", 0.01f, 0.01f);
    }

    public static void onAfterLuckyChange(CommonCharacter character, int changedValue) {
        // 福缘影响暴击几率
        applyAcquireAttr(character, "lucky", changedValue , "crit_rate", 0.1f, 0.1f);
        // 福缘影响暴击倍率
        applyAcquireAttr(character, "lucky", changedValue , "crit_scale", 0.1f, 0.1f);
    }

    public static void applyInbornAttr(CommonCharacter character, String inbornAttrKey, String acquireAttrKey, String targetAttrKey, float inbornScale, float acquireScale) {
        /*
        * 计算先天属性和后天属性下，角色的属性
        * @ character 角色队形
        * @ inbornAttrKey 先天属性的key
         * @ inbornAttrKey 后天属性的key
        * @ targetKey  受影响的属性的Key
        * @ inbornScale 先天属性影响因子
        * @ acquireScale  后天属性影响因子
        * */
        try{
            // 拿到先天属性值
            int inbornAttr = (int)GameCharacterManager.findAttributeByName(character, inbornAttrKey);
            // 拿到后天属性值
            int acquireAttr = (int)GameCharacterManager.findAttributeByName(character, acquireAttrKey);
            // 运算目标值
            if(targetAttrKey.equals("max_hp") && acquireAttr == 0) acquireAttr = 1;
            float targetAttr = inbornAttr * inbornScale * acquireAttr * acquireScale;
            // 设置目标值
            GameCharacterManager.changeStatus(character, targetAttrKey, targetAttr);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("角色： " + character.getName() +" 在应用属性（" + inbornAttrKey +")的时候发生错误");
        }
    }

    public static void applyAcquireAttr(CommonCharacter character, String inbornAttrKey, int changedValue, String targetAttrKey, float inbornScale, float acquireScale) {
        /*
         * 计算先天属性和后天属性下，角色的属性
         * @ character 角色队形
         * @ inbornAttrKey 先天属性的key
         * @ 后天属性的增加值
         * @ targetKey  受影响的属性的Key
         * @ inbornScale 先天属性影响因子
         * @ acquireScale  后天属性影响因子
         * */
        try{
            // 拿到先天属性值
            int inbornAttr = (int)GameCharacterManager.findAttributeByName(character, inbornAttrKey);
            // 拿到后天属性值
            int acquireAttr = changedValue;
            // 运算目标值
            if(targetAttrKey.equals("max_hp") && acquireAttr == 0) acquireAttr = 1;
            float targetAttr = inbornAttr * inbornScale * acquireAttr * acquireScale;
            // 设置目标值
            GameCharacterManager.changeStatus(character, targetAttrKey, targetAttr);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("角色： " + character.getName() +" 在应用属性（" + inbornAttrKey +")的时候发生错误");
        }
    }

    public static int calculateSkillChargeNumber(PlayerCharacter playerCharacter){
        /*
        * @ 计算充值数量（每次能充多少潜能）
        * @ learnRate 学习效率 取值范围 1-3000
        * @ smartValue 先天悟性 取值范围 15-25
        * @ after_smartVale 后天悟性 取值范围 1-3000
        * @ baseValue  保底充值 200
        * 如果发生异常返回-1
        * */
        int baseValue = 200;
        try{
            // 得到玩家学习效率和悟性
            int learnRate = (int) playerCharacter.getCustomerAttr().get("learn_rate").get("value");
            int smartValue =  playerCharacter.getSmart();
            int after_smartValue = playerCharacter.getAfter_smart();
            // 根据玩家的学习效率和悟性计算玩家每一次可以充值的潜能数量
            baseValue += learnRate * smartValue * 0.5 + after_smartValue;
            return baseValue;
        }catch (Exception e){
            System.out.println("计算玩家充能数的算法失效，请检查玩家属性： " + playerCharacter.getName());
            return -1;
        }
    }

    public static int calculateSkillLevelUpNumber(SkillObject skillObject){
        /*
        * @ 计算当前技能升级所需要到达多少潜能
        * @ 如果发生异常，返回-1
        * */
        int baseValue = 100;
        try{
            int skillLevel = skillObject.getLevel();
            int skillQuality = skillObject.getQuality();
            baseValue += skillLevel * skillQuality * 100;
            return baseValue;
        }catch (Exception e){
            System.out.println("计算技能升级所需要的潜能，算法失效， 对应技能id: " + skillObject.getId());
            return -1;
        }
    }


}
