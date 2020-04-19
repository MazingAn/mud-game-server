package com.mud.game.object.typeclass;

import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.structs.SimpleCharacter;

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
    public float daoXiShu;
    public float jianXishu;
    public float quanXishu;
    public float qiMenXishu;
    public float neigongXishu;
    public float qingGongXishu;
    public float duXiShu;
    public float yiXiShu;
    public float zaXueXiShu;
    public float xueXiXishu;
    public String family;
    public int xiayi;
    public float tili;
    public int goodAndEvil;
    public boolean doubleEquip;
    // 玩家的好友列表
    public Map<String, SimpleCharacter> friends;
    // 玩家接受到的好友请求列表
    public Map<String, SimpleCharacter> friendRequests;
    // 玩家通过物品或付费学习技能的记录
    public Map<String, Integer> learnByObjectRecord;
    // 玩家的背包
    public String bagpack;
    // 玩家的仓库
    public String wareHouse;

    public PlayerCharacter() {
        super();
        this.revealedMap = new HashMap<>();
        this.unlockedExit = new HashSet<>();
        this.friends = new HashMap<>();
        this.friendRequests = new HashMap<>();
        this.learnByObjectRecord = new HashMap<>();
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
        return daoXiShu;
    }

    public void setDaoXiShu(float daoXiShu) {
        this.daoXiShu = daoXiShu;
    }

    public float getJianXishu() {
        return jianXishu;
    }

    public void setJianXishu(float jianXishu) {
        this.jianXishu = jianXishu;
    }

    public float getQuanXishu() {
        return quanXishu;
    }

    public void setQuanXishu(float quanXishu) {
        this.quanXishu = quanXishu;
    }

    public float getQiMenXishu() {
        return qiMenXishu;
    }

    public void setQiMenXishu(float qiMenXishu) {
        this.qiMenXishu = qiMenXishu;
    }

    public float getNeigongXishu() {
        return neigongXishu;
    }

    public void setNeigongXishu(float neigongXishu) {
        this.neigongXishu = neigongXishu;
    }

    public float getQingGongXishu() {
        return qingGongXishu;
    }

    public void setQingGongXishu(float qingGongXishu) {
        this.qingGongXishu = qingGongXishu;
    }

    public float getDuXiShu() {
        return duXiShu;
    }

    public void setDuXiShu(float duXiShu) {
        this.duXiShu = duXiShu;
    }

    public float getYiXiShu() {
        return yiXiShu;
    }

    public void setYiXiShu(float yiXiShu) {
        this.yiXiShu = yiXiShu;
    }

    public float getZaXueXiShu() {
        return zaXueXiShu;
    }

    public void setZaXueXiShu(float zaXueXiShu) {
        this.zaXueXiShu = zaXueXiShu;
    }

    public float getXueXiXishu() {
        return xueXiXishu;
    }

    public void setXueXiXishu(float xueXiXishu) {
        this.xueXiXishu = xueXiXishu;
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

    public Map<String, SimpleCharacter> getFriends() {
        return friends;
    }

    public void setFriends(Map<String, SimpleCharacter> friends) {
        this.friends = friends;
    }

    public Map<String, SimpleCharacter> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(Map<String, SimpleCharacter> friendRequests) {
        this.friendRequests = friendRequests;
    }

    public Map<String, Integer> getLearnByObjectRecord() {
        return learnByObjectRecord;
    }

    public void setLearnByObjectRecord(Map<String, Integer> learnByObjectRecord) {
        this.learnByObjectRecord = learnByObjectRecord;
    }

    public String getBagpack() {
        return bagpack;
    }

    public void setBagpack(String bagpack) {
        this.bagpack = bagpack;
    }

    public String getWareHouse() {
        return wareHouse;
    }

    public void setWareHouse(String wareHouse) {
        this.wareHouse = wareHouse;
    }
}
