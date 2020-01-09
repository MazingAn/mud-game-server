package com.mud.game.object.typeclass;

import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.structs.SimplePlayerCharacter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PlayerCharacter extends CommonCharacter {

    public String player;
    // 已经解锁的地图
    public Map<String, Set<String>> revealedMap;
    // 已经解锁的带锁出口
    public Set<String> unlockedExit;
    // 玩家随身携带用于事件随机选择分支的随机数
    public double randomNumber;
    // 玩家称号
    public Set<String> titleKeySet;
    public int level;
    public int exp;
    public int limitExp;
    public int potential;
    public int ennergy;
    public String teacher;
    public int contribution;
    public float DaoXiShu;
    public float JianXishu;
    public float QuanXishu;
    public float QiMenXishu;
    public float NeigongXishu;
    public float QingGongXishu;
    public float DuXiShu;
    public float YiXiShu;
    public float ZaXueXiShu;
    public float XueXiXishu;
    public String family;
    public int xiayi;
    public float tili;
    public int goodAndEvil;
    public boolean doubleEquip;
    // 玩家的好友列表
    public Map<String, SimplePlayerCharacter> friends;
    // 玩家接受到的好友请求列表
    public Map<String, SimplePlayerCharacter> friendRequests;

    public PlayerCharacter() {
        this.revealedMap = new HashMap<>();
        this.unlockedExit = new HashSet<>();
        this.friends = new HashMap<>();
        this.friendRequests = new HashMap<>();
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Map<String, Set<String>> getRevealedMap() {
        return revealedMap;
    }

    public void setRevealedMap(Map<String, Set<String>> revealedMap) {
        this.revealedMap = revealedMap;
    }

    public Set<String> getUnlockedExit() {
        return unlockedExit;
    }

    public void setUnlockedExit(Set<String> unlockedExit) {
        this.unlockedExit = unlockedExit;
    }

    public double getRandomNumber() {
        return randomNumber;
    }

    public void setRandomNumber(double randomNumber) {
        this.randomNumber = randomNumber;
    }

    public Set<String> getTitleKeySet() {
        return titleKeySet;
    }

    public void setTitleKeySet(Set<String> titleKeySet) {
        this.titleKeySet = titleKeySet;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getLimitExp() {
        return limitExp;
    }

    public void setLimitExp(int limitExp) {
        this.limitExp = limitExp;
    }

    public int getPotential() {
        return potential;
    }

    public void setPotential(int potential) {
        this.potential = potential;
    }

    public int getEnnergy() {
        return ennergy;
    }

    public void setEnnergy(int ennergy) {
        this.ennergy = ennergy;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getContribution() {
        return contribution;
    }

    public void setContribution(int contribution) {
        this.contribution = contribution;
    }

    public float getDaoXiShu() {
        return DaoXiShu;
    }

    public void setDaoXiShu(float daoXiShu) {
        DaoXiShu = daoXiShu;
    }

    public float getJianXishu() {
        return JianXishu;
    }

    public void setJianXishu(float jianXishu) {
        JianXishu = jianXishu;
    }

    public float getQuanXishu() {
        return QuanXishu;
    }

    public void setQuanXishu(float quanXishu) {
        QuanXishu = quanXishu;
    }

    public float getQiMenXishu() {
        return QiMenXishu;
    }

    public void setQiMenXishu(float qiMenXishu) {
        QiMenXishu = qiMenXishu;
    }

    public float getNeigongXishu() {
        return NeigongXishu;
    }

    public void setNeigongXishu(float neigongXishu) {
        NeigongXishu = neigongXishu;
    }

    public float getQingGongXishu() {
        return QingGongXishu;
    }

    public void setQingGongXishu(float qingGongXishu) {
        QingGongXishu = qingGongXishu;
    }

    public float getDuXiShu() {
        return DuXiShu;
    }

    public void setDuXiShu(float duXiShu) {
        DuXiShu = duXiShu;
    }

    public float getYiXiShu() {
        return YiXiShu;
    }

    public void setYiXiShu(float yiXiShu) {
        YiXiShu = yiXiShu;
    }

    public float getZaXueXiShu() {
        return ZaXueXiShu;
    }

    public void setZaXueXiShu(float zaXueXiShu) {
        ZaXueXiShu = zaXueXiShu;
    }

    public float getXueXiXishu() {
        return XueXiXishu;
    }

    public void setXueXiXishu(float xueXiXishu) {
        XueXiXishu = xueXiXishu;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public int getXiayi() {
        return xiayi;
    }

    public void setXiayi(int xiayi) {
        this.xiayi = xiayi;
    }

    public float getTili() {
        return tili;
    }

    public void setTili(float tili) {
        this.tili = tili;
    }

    public int getGoodAndEvil() {
        return goodAndEvil;
    }

    public void setGoodAndEvil(int goodAndEvil) {
        this.goodAndEvil = goodAndEvil;
    }

    public boolean canDoubleEquip() {
        return doubleEquip;
    }

    public void setDoubleEquip(boolean doubleEquip) {
        this.doubleEquip = doubleEquip;
    }

    public boolean isDoubleEquip() {
        return doubleEquip;
    }

    public Map<String, SimplePlayerCharacter> getFriends() {
        return friends;
    }

    public void setFriends(Map<String, SimplePlayerCharacter> friends) {
        this.friends = friends;
    }

    public Map<String, SimplePlayerCharacter> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(Map<String, SimplePlayerCharacter> friendRequests) {
        this.friendRequests = friendRequests;
    }
}
