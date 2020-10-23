package com.mud.game.object.typeclass;

import org.springframework.data.annotation.Id;

/**
 * 仇人击杀记录
 */
public class EnemyRecordObject {
    @Id
    private String id;
    // 玩家Id
    private String playerId;
    // 仇人Id
    private String enemyId;
    // 击杀时间
    private String createTime;
    // 击杀地点
    private String location;

    public EnemyRecordObject(String playerId, String enemyId, String createTime, String location) {
        this.playerId = playerId;
        this.enemyId = enemyId;
        this.createTime = createTime;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getEnemyId() {
        return enemyId;
    }

    public void setEnemyId(String enemyId) {
        this.enemyId = enemyId;
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
