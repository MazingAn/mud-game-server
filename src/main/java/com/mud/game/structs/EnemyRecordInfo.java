package com.mud.game.structs;

public class EnemyRecordInfo {
    // 仇人Id
    private String enemyName;
    // 击杀时间
    private String createTime;
    // 击杀地点
    private String location;

    public EnemyRecordInfo(String enemyName, String createTime, String location) {
        this.enemyName = enemyName;
        this.createTime = createTime;
        this.location = location;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
