package com.mud.game.constant;

import java.util.ArrayList;
import java.util.List;

public class Constant {
    //强化属性增加系数
    public static final Double STRENGTHEN_COEFFICIENT = 1.2;
    //强化等级上限
    public static final int MAX_LEVEL = 10;
    //进阶属性增加系数
    public static final Double QUALITY_COEFFICIENT = 1.5;
    //进阶等级上限
    public static final int MAX_QUALITY = 10;
    //最大开孔数
    public static final int MAX_SLOT = 8;
    //背包格子物品为0不能被清除的dataKey
    public static final List<String> REMOVE_ITEM_FLASE_LIST = new ArrayList<String>() {{
        add("OBJECT_JINZI");
        add("OBJECT_YINLIANG");
        add("OBJECT_JINYEZI");
    }};
}
