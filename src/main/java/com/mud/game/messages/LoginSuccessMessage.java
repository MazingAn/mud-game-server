package com.mud.game.messages;

import com.mud.game.object.account.Player;
import com.mud.game.object.typeclass.PlayerCharacter;

import java.util.HashMap;
import java.util.Map;

public class LoginSuccessMessage {
    private Map<String, String> login;

    public LoginSuccessMessage(Player player) {
        Map<String, String> object = new HashMap<>();
        object.put("dbref", player.getId());
        object.put("name", player.getUsername());
        login = object;
    }

    public Map<String, String> getLogin() {
        return login;
    }

    public void setLogin(Map<String, String> login) {
        this.login = login;
    }
}
