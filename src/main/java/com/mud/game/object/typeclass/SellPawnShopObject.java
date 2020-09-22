package com.mud.game.object.typeclass;

import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.structs.CommonObjectInfo;

import javax.persistence.Id;
import java.util.List;

/**
 * 玩家出售记录数据-可买回
 */
public class SellPawnShopObject {
    @Id
    private String id;
    /**
     * 玩家id
     */
    private String owner;
    /**
     * 出售物品记录
     */
    private List<CommonObjectInfo> commonObjectInfoList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<CommonObjectInfo> getCommonObjectInfoList() {
        return commonObjectInfoList;
    }

    public void setCommonObjectInfoList(List<CommonObjectInfo> commonObjectInfoList) {
        this.commonObjectInfoList = commonObjectInfoList;
    }
}
