package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;
import com.mud.game.worlddata.db.models.supermodel.BaseCommonObject;

import javax.persistence.*;

@Entity
@Mark(name = "宝石")
public class Gem extends BaseCommonObject {

    //宝石的品级
    @Mark(name="品级")
    private int quality;

    //宝石可以镶嵌在什么位置的装备上
    @Mark(name = "装备位置", link = "equipmentPosition")
    private String positions;


    //宝石的属性增益
    @Mark(name="属性")
    @Column(length = 2048)
    private String attrs;


    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public String getPositions() {
        return positions;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }

    public String getAttrs() {
        return attrs;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }
}
