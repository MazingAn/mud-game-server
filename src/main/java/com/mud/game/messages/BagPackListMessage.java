package com.mud.game.messages;

import com.mud.game.structs.CommonObjectInfo;

import java.util.ArrayList;
import java.util.Map;


/**
 * 返回信息类： 背包信息消息
 * <pre>
 *  返回示例：
 *  {
 *      "inventory": [
 *          {
 *              category: "TYPE_OBJECTTYPE_EQUIPMENT",
 *              dataKey: "dahuandao",
 *              dbref: "5e6844604cd95e36a09db953",
 *              desc: "这是一把大环刀，透着寒光，一看就是好东西！",
 *              equipped: false,
 *              icon: "",
 *              max_stack: 1,
 *              name: "大环刀",
 *              number: 1,
 *              position: ["LEFT_HAND","RIGHT_HAND"],
 *              quality: 0,
 *              strength_level: 0
 *          }
 *      ]
 *  }
 *  </pre>
 * 返回背包数据是一个数组，数组元素为一个 CommonObjectInfo的json序列化字符串。<br>
 * 关于CommonObjectInfo 参见 : {@link CommonObjectInfo}
 */

public class BagPackListMessage {

    private ArrayList<CommonObjectInfo> inventory;

    public BagPackListMessage(ArrayList<CommonObjectInfo> inventory) {
        this.inventory = inventory;
    }

    public BagPackListMessage(Map<String, CommonObjectInfo> valuess) {
        ArrayList<CommonObjectInfo> commonObjectInfoArrayList = new ArrayList<>();
        CommonObjectInfo commonObjectInfo = new CommonObjectInfo();
        for (String itemId : valuess.keySet()) {
            commonObjectInfo = valuess.get(itemId);
            commonObjectInfo.setItemId(itemId);
            commonObjectInfoArrayList.add(commonObjectInfo);
        }
        this.inventory = commonObjectInfoArrayList;
    }

    public ArrayList<CommonObjectInfo> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<CommonObjectInfo> inventory) {
        this.inventory = inventory;
    }
}
