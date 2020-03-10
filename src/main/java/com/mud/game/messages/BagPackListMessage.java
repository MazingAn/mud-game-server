package com.mud.game.messages;

import java.util.ArrayList;


/**
 *  返回信息类： 背包信息消息
 *  <pre>
 *  返回示例：
 *  {
 *      "inventory": [
 *          物品信息 -> {@link com.mud.game.structs.CommonObjectInfo},
 *          ..............
 *      ]
 *  }
 *  </pre>
 *
 * */

public class BagPackListMessage {

    private ArrayList<Object> inventory;

    public BagPackListMessage(ArrayList<Object> inventory) {
        this.inventory = inventory;
    }

    public ArrayList<Object> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Object> inventory) {
        this.inventory = inventory;
    }
}
