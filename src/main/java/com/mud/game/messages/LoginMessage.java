package com.mud.game.messages;

public class LoginMessage {
    private String dbref;
    private String username;

    public LoginMessage(String dbref, String username) {
        this.dbref = dbref;
        this.username = username;
    }

    public String getDbref() {
        return dbref;
    }

    public void setDbref(String dbref) {
        this.dbref = dbref;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
