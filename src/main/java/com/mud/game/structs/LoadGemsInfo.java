package com.mud.game.structs;

import com.mongodb.util.JSON;
import com.mud.game.object.typeclass.EquipmentObject;
import com.mud.game.utils.jsonutils.Attr2Map;
import com.mud.game.worlddata.db.models.Gem;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 返回所有适合用在目标装备上的宝石
 */
public class LoadGemsInfo {
    /**
     * 宝石的dbref
     */
    private Long dbref;
    /**
     * 宝石的名称
     */
    private String name;
    /**
     * 宝石可以使用的位置
     */
    private Object positions;
    /**
     * 宝石的描述
     */
    private String desc;
    /**
     * 宝石的图标
     */
    private String icon;
    /**
     * 宝石的属性
     */
    Map<String, Map<String, Object>> attr;

    public LoadGemsInfo(Gem gem) {
        this.dbref = gem.getId();
        this.name = gem.getName();
        this.positions = JSON.parse(gem.getPositions());
        this.attr = Attr2Map.equipmentAttrTrans(gem.getAttrs());
        this.icon = gem.getIcon();
        this.desc = gem.getDescription();
    }


    public Long getDbref() {
        return dbref;
    }

    public void setDbref(Long dbref) {
        this.dbref = dbref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getPositions() {
        return positions;
    }

    public void setPositions(Object positions) {
        this.positions = positions;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Map<String, Map<String, Object>> getAttr() {
        return attr;
    }

    public void setAttr(Map<String, Map<String, Object>> attr) {
        this.attr = attr;
    }
}
