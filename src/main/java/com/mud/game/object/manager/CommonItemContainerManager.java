package com.mud.game.object.manager;

import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.supertypeclass.CommonItemContainer;
import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.structs.CommonObjectInfo;
import com.mud.game.worlddata.db.models.NormalObject;
import com.mud.game.worlddata.db.models.supermodel.BaseCommonObject;
import com.mud.game.worlddata.db.models.supermodel.BaseObject;


import java.awt.*;
import java.util.Map;
import java.util.UUID;

public class CommonItemContainerManager {
    /*
    * @ 管库游戏中的背包和仓库容器
     */

    public static boolean addItem(CommonItemContainer container, CommonObject commonObj, int number){
        /*
        * 往容器中添加物品
        * */
        // number如果为0，就没有意义了不需要添加
        if(number <= 0){
            return false;
        }
        // 检查物品能否放入背包
        if(checkCanAdd(container, commonObj, number)){
            // 往容器里面内放入物品
            // 先填充没有之前没装满的格子
            String cellId = findUnFullCellKeyByDataKey(container, commonObj.getDataKey());
            if(cellId!=null){
                CommonObjectInfo cellInfo = container.getItems().get(cellId);
                int fillNumber = commonObj.getMaxStack() - cellInfo.getNumber();
                cellInfo.setNumber(fillNumber);
                number = number - fillNumber;
            }
            // 填充完没有装满的格子之后，剩下的按照maxStack的数量循环添加,每填充一次，number就减少maxStack，知道number小于maxStack
            while(number >= commonObj.getMaxStack()){
                String id = commonObj.getId() + "_" +UUID.randomUUID().toString();
                container.getItems().put(id, new CommonObjectInfo(commonObj, commonObj.getMaxStack()));
                number = number - commonObj.getMaxStack();
            }
            // 如果number最终小于maxStack，则直接添加一次，设置数量为number个
            if(number < commonObj.getMaxStack() && number != 0){
                String id = commonObj.getId() + "_" +UUID.randomUUID().toString();
                container.getItems().put(id, new CommonObjectInfo(commonObj, number));
            }
            return true;
        }
        return false;
    }

    public static boolean removeItem(CommonItemContainer container, CommonObject commonObject, int number){
        /*
        * @ 从容器内部移除一定数量的物品
        * */
        // 如果number <=0 则没有意义
        if(number <= 0) return false;
        // 检查能否移除
        if(!checkCanRemove(container, commonObject, number)){
            return false;
        }
        // 先移除没有装满的cell
        while(number > 0){
            String unFullCellId = findUnFullCellKeyByDataKey(container, commonObject.getDataKey());
            if(unFullCellId != null){
                CommonObjectInfo unFullCellInfo = container.getItems().get(unFullCellId);
                int unFullNumber = unFullCellInfo.getNumber();
                if(unFullNumber >= number){
                    // 如果没填充满的格子的数量大于要移除的，直接移除，返回true；搞定！！！
                    unFullCellInfo.setNumber(unFullNumber - number);
                    if(unFullCellInfo.getNumber() == 0){
                        // 如果移除之后，number为零，则这个cell应该被移除
                        container.getItems().remove(unFullCellId);
                        // 包裹的使用数量-1
                        container.setUsedCellNumber(container.getUsedCellNumber() - 1);
                    }
                    number = 0;
                }else{
                    // 如果要移除的数量大于当前的数量，直接移除这个cell,并把要移除的总是减去当前容器的数量
                    container.getItems().remove(unFullCellId);
                    // 包裹的使用数量-1
                    container.setUsedCellNumber(container.getUsedCellNumber() - 1);
                    //更新number
                    number = number - unFullNumber;
                }
            }
            // 处理完没有装满的格子之后继续移除,根据maxStack，一次移除一个单元格
            while(number >= commonObject.getMaxStack()){
                String fullCellId = findFullCellKeyByDataKey(container, commonObject.getDataKey());
                container.getItems().remove(fullCellId);
                container.setUsedCellNumber(container.getUsedCellNumber() - 1);
                number = number - commonObject.getMaxStack();
            }
        }
        return true;
    }

    /**
     * 检查要添加的物品能否被背包放下
     *
     * @param container 目标容器（背包或仓库)
     * @param commonObject 要添加的物品
     * @param number 数量
     *
     * @return boolean 是否能够添加
     * */
    public static boolean checkCanAdd(CommonItemContainer container, CommonObject commonObject, int number){
        // 首先拿到背包空余的格子
        int freeCellNumber = container.getMaxCellNumber() - container.getUsedCellNumber();
        // 计算需要的空间
        int neededCellNumber = Integer.MAX_VALUE;
        // 检查这个物品没装满的格子
        String cellId = findUnFullCellKeyByDataKey(container, commonObject.getDataKey());
        if(cellId != null){ //确实有一个格子没有装满；
            // 计算一下这个格子还能装多少；
            CommonObjectInfo info =  container.getItems().get(cellId);
            int canFillNumber = commonObject.getMaxStack() - info.getNumber();
            // 修改总数；减去能在这个格子装下的；
            number = number - canFillNumber;
        }
        // 计算出来最终还需要多少个空格子(像上取整运算之前需要强制转换成为double，要不然没有意义)
        neededCellNumber =  (int)Math.ceil((double) number / (double) commonObject.getMaxStack());
        return neededCellNumber <= freeCellNumber;
    }

    public static boolean checkCanRemove(CommonItemContainer container, CommonObject commonObject, int number){
        /*
        * @ 检查一定数量的物品能否被移除
        * */
        // 获取这个物品在当前容器里一共有多少个
        int toalNumber = getNumberByDataKey(container, commonObject.getDataKey());
        return number <= toalNumber;
    }

    public static boolean hasObjectByDataKey(CommonItemContainer container, String objectKey, int number){
        /*
        * 检查容器里面是否有某件物品
        * */
        for(Object obj: container.getItems().values()){
            CommonObjectInfo info =(CommonObjectInfo) obj;
            if(info.getDataKey().trim().equals(objectKey.trim()) && info.getNumber() >= number){
                return true;
            }
        }
        return false;
    }

    public static String findUnFullCellKeyByDataKey(CommonItemContainer container, String objectKey){
        /*
        * 返回没有装满的容器的key
        * */
        for(String key : container.getItems().keySet()){
            CommonObjectInfo info = container.getItems().get(key);
            if(info.getDataKey().startsWith(objectKey)){
                if(info.getNumber() < info.getMax_stack()){
                    return key;
                }
            }
        }
        return null;
    }

    public static String findFullCellKeyByDataKey(CommonItemContainer container, String objectKey) {
        /*
        * 查找一个装满这种物品的元素
        * */
        for(String key : container.getItems().keySet()){
            CommonObjectInfo info = container.getItems().get(key);
            if(info.getDataKey().startsWith(objectKey)){
                if(info.getNumber() == info.getMax_stack()){
                    return key;
                }
            }
        }
        return null;
    }

    public static int getNumberByDataKey(CommonItemContainer container, String commonObjectKey){
        /*
        * 根据key计算物品在背包内的数量
        * */
        int toalNumber = 0;
        for(String key: container.getItems().keySet()){
            CommonObjectInfo info = container.getItems().get(key);
            if(info.getDataKey().startsWith(commonObjectKey)){
                toalNumber += info.getNumber();
            }
        }
        return toalNumber;
    }


}
