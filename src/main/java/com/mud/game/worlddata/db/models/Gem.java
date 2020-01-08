package com.mud.game.worlddata.db.models;

import com.mud.game.worlddata.db.models.supermodel.BaseObject;
import com.mud.game.worlddata.db.models.supermodel.CommonObject;

import javax.persistence.*;

@Entity
public class Gem extends CommonObject {

    //宝石的品级
    private int quality;
    //宝石可以镶嵌在什么位置的装备上
    private String positions;
    //宝石的属性增益
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
