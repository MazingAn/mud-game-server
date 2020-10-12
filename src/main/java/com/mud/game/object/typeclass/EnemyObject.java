package com.mud.game.object.typeclass;

import com.mud.game.structs.SimpleCharacter;

import javax.persistence.Id;
import java.util.Map;

/**
 * 玩家仇人关系
 */
public class EnemyObject {
    @Id
    private String id;
    // 玩家Id
    private String playerId;
    // 仇人Id
    private String enemyId;
    // 玩家的信息
    public SimpleCharacter enemyInfo;

    public EnemyObject(String playerId, String enemyId, SimpleCharacter enemyInfo) {
        this.playerId = playerId;
        this.enemyId = enemyId;
        this.enemyInfo = enemyInfo;
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

    public SimpleCharacter getEnemyInfo() {
        return enemyInfo;
    }

    public void setEnemyInfo(SimpleCharacter enemyInfo) {
        this.enemyInfo = enemyInfo;
    }
}
