package com.mud.game.object.typeclass;

import com.mud.game.object.supertypeclass.WorldObject;

import java.net.URL;


public class WorldExitObject extends WorldObject {
    //通过出口的时候的动作
    private String verb;
    //出口的目的地
    private String destination;
    //是否是双向出口
    private boolean twoWay;
    //出口是否锁定
    private boolean locked;
    //出口锁定显示的信息
    private String lockDescription;
    //解锁出口的时候提示的信息
    private String unlockDescription;
    //出口解锁的条件
    private String unlockCondition;

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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
