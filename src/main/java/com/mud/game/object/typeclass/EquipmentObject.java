package com.mud.game.object.typeclass;

import com.mud.game.object.supertypeclass.CommonObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class EquipmentObject extends CommonObject {
    // 可装备的位置
    private Set<String> positions;
    // 装备对应的套装
    private String suite;
    // 如果装备是武器，则设置武器类别
    private String weaponType;
    //装备最大开孔数量
    private int maxSlot;
    //装备开孔数量
    private int opendSlot;
    //装备的属性
    private Map<String, Map<String, Object>> attrs;
    //镶嵌的宝石
    private List<GemObject> gems;
    // 是否被装备
    private boolean equipped;
    // 被装备到的位置
    private String equippedPosition;

    public EquipmentObject() {
    }

    public Set<String> getPositions() {
        return positions;
    }

    public void setPositions(Set<String> positions) {
        this.positions = positions;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(String weaponType) {
        this.weaponType = weaponType;
    }

    public int getMaxSlot() {
        return maxSlot;
    }

    public void setMaxSlot(int maxSlot) {
        this.maxSlot = maxSlot;
    }

    public Map<String, Map<String, Object>> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, Map<String, Object>> attrs) {
        this.attrs = attrs;
    }

    public List<GemObject> getGems() {
        return gems;
    }

    public void setGems(List<GemObject> gems) {
        this.gems = gems;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }

    public String getEquippedPosition() {
        return equippedPosition;
    }

    public void setEquippedPosition(String equippedPosition) {
        this.equippedPosition = equippedPosition;
    }

    public int getOpendSlot() {
        return opendSlot;
    }

    public void setOpendSlot(int opendSlot) {
        this.opendSlot = opendSlot;
    }
}
