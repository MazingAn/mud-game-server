package com.mud.game.messages;

import com.mud.game.object.typeclass.PlayerCharacter;

import java.util.HashMap;
import java.util.Map;

public class AddFriendRequestMessage {
    public Map<String, Object> friend_apply;

    public AddFriendRequestMessage(PlayerCharacter playerCharacter) {
        friend_apply = new HashMap<>();
        friend_apply.put("dbref", playerCharacter.getId());
        friend_apply.put("name", playerCharacter.getName());
    }

    public Map<String, Object> getFriend_apply() {
        return friend_apply;
    }

    public void setFriend_apply(Map<String, Object> friend_apply) {
        this.friend_apply = friend_apply;
    }
}
