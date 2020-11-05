package com.mud.game.object.account;

import javax.persistence.Id;

public class WeiXinPlayer {
    @Id
    private String id;
    private String playerId;
    private String unionId;

    public WeiXinPlayer(String playerId, String unionId) {
        this.playerId = playerId;
        this.unionId = unionId;
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

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}
