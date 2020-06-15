package com.mud.game.object.account;

import com.mud.game.structs.SimpleCharacter;
import com.mud.game.utils.passwordutils.ShaPassword;

import javax.persistence.Id;
import java.util.Set;

public class Player {
    @Id
    private String id;
    private String username;
    private String password;
    // 是否是管理员
    private boolean admin;
    // 是否被锁定（删除）
    private boolean locked;
    // 是否被封禁
    private boolean ban;
    // 封禁结束的时间戳
    private Float banEndTime;
    private Set<SimpleCharacter> playerCharacters;

    public Player() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = ShaPassword.encrypts(password);
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isBan() {
        return ban;
    }

    public void setBan(boolean ban) {
        this.ban = ban;
    }

    public Float getBanEndTime() {
        return banEndTime;
    }

    public void setBanEndTime(Float banEndTime) {
        this.banEndTime = banEndTime;
    }

    public Set<SimpleCharacter> getPlayerCharacters() {
        return playerCharacters;
    }

    public void setPlayerCharacters(Set<SimpleCharacter> playerCharacters) {
        this.playerCharacters = playerCharacters;
    }
}
