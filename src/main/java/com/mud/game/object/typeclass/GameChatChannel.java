package com.mud.game.object.typeclass;

import net.bytebuddy.implementation.bind.MethodDelegationBinder;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Set;

public class GameChatChannel {
    @Id
    private String id;
    @Column(unique = true)
    private String chanelKey;
    private String name;
    private String permission;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChanelKey() {
        return chanelKey;
    }

    public void setChanelKey(String chanelKey) {
        this.chanelKey = chanelKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
