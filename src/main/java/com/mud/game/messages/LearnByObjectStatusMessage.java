package com.mud.game.messages;

import java.util.HashMap;
import java.util.Map;

public class LearnByObjectStatusMessage {

    /*
    * learn_status
    * 返回玩家通过物品学习的状态（和老版本一样）
    * 有两种情况：
    * 第一种 直接能够学习，返回学习的目标，学习的潜能余额，以及能不能学习
    * 第二种 不能学习，在第一种情况的基础上 追加显示要上交的物品信息
    * */

    private Map<String, Object> learn_status;

    public LearnByObjectStatusMessage(String learnTarget, Integer balance) {
        learn_status = new HashMap<>();
        learn_status.put("learn_target", learnTarget);
        learn_status.put("learn_value", balance);
        learn_status.put("can_learn", balance > 0);
    }

    public LearnByObjectStatusMessage(String learnTarget, Integer balance, String objectKey, String objectName) {
        learn_status = new HashMap<>();
        learn_status.put("learn_target", learnTarget);
        learn_status.put("learn_value", balance);
        learn_status.put("can_learn", balance > 0);
        // 追加需要等价交换物的信息
        Map<String, String> objectInfo = new HashMap<>();
        objectInfo.put("key", objectKey);
        objectInfo.put("name", objectName);
        learn_status.put("object", objectInfo);
    }

    public Map<String, Object> getLearn_status() {
        return learn_status;
    }

    public void setLearn_status(Map<String, Object> learn_status) {
        this.learn_status = learn_status;
    }
}
