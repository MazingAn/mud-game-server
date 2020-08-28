package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;
import com.mud.game.worlddata.db.models.supermodel.BaseObject;

import javax.persistence.*;

@Entity
@Mark(name = "世界出口")
public class WorldExit extends BaseObject {

    @Column(length = 32)
    //通过出口的时候的动作
    @Mark(name = "动作")
    private String verb;

    //出口的原地址
    @Mark(name = "出发地", link = "worldRoom")
    private String location;

    //出口的目的地
    @Mark(name = "目的地", link = "worldRoom")
    private String destination;

    //是否是双向出口
    @Mark(name = "双向出口")
    @Column(columnDefinition = "bool default true")
    private boolean twoWay;

    //出口是否锁定
    @Column(columnDefinition = "bool default false")
    @Mark(name = "是否带锁")
    private boolean locked;

    //出口锁定显示的信息
    @Mark(name = "锁定描述")
    private String lockDescription;

    //解锁出口的时候提示的信息
    @Mark(name = "解锁描述")
    private String unlockDescription;

    //出口解锁的条件
    @Column(length = 1024)
    @Mark(name = "解锁条件")
    private String unlockCondition;

    //出口显示的条件
    @Column(length = 1024)
    @Mark(name = "显示条件")
    private String showCondition;



    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getShowCondition() {
        return showCondition;
    }

    public void setShowCondition(String condition) {
        this.showCondition = condition;
    }

    public boolean isTwoWay() {
        return twoWay;
    }

    public void setTwoWay(boolean twoWay) {
        this.twoWay = twoWay;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getLockDescription() {
        return lockDescription;
    }

    public void setLockDescription(String lockDescription) {
        this.lockDescription = lockDescription;
    }

    public String getUnlockDescription() {
        return unlockDescription;
    }

    public void setUnlockDescription(String unlockDescription) {
        this.unlockDescription = unlockDescription;
    }

    public String getUnlockCondition() {
        return unlockCondition;
    }

    public void setUnlockCondition(String unlockCondition) {
        this.unlockCondition = unlockCondition;
    }
}
