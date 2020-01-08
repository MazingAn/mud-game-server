package com.mud.game.structs;


import com.mud.game.object.supertypeclass.BaseGameObject;
import com.mud.game.utils.overflow.OverFlowDecision;
import com.mud.game.utils.overflow.OverFlowDesc;

import java.util.Map;

public class BaseItemList {

    private BaseGameObject caller;
    private int max_size;
    private int size;

    private Map<String, CommonObjectAppearance> items;

    public boolean isFull(){
        return this.size >= this.max_size;
    }

    public boolean isEmpty(){
        return this.size == 0;
    }

    public void remove(String key, int removeNumber){
        /*
        * 删除物品
        * 返回： 成功 True， 失败 False
        */
        if (items.containsKey(key)){
            ;
        }
    }

    public void add(String key, int addedNumber) {
        //背包物品检查，如果有，则检查堆叠，没有则增加
        if(items.containsKey(key)){
            CommonObjectAppearance appearance = items.get(key);
            OverFlowDesc overFlowDesc = OverFlowDecision.make(addedNumber, appearance.getNumber(), appearance.getMaxStack());
            int overFlowNumber = overFlowDesc.getFlowNumber();
            if (overFlowNumber > 0) {
                ;
            }

        }else{
            ;
        }
    }


}
