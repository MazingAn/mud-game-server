package com.mud.game.messages;

import java.util.HashMap;
import java.util.Map;

public class LearnByObjectStatusMessage {
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
