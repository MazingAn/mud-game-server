package com.mud.game.messages;

import com.mud.game.object.typeclass.PlayerCharacter;

import java.util.HashMap;
import java.util.Map;

public class FriendMessage {

    private Map<String, Object> friend_message;

    public FriendMessage(String messages, PlayerCharacter caller, PlayerCharacter receiver, boolean type) {
        friend_message = new HashMap<>();
        friend_message.put("send", caller.getId());
        friend_message.put("accept", receiver.getId());
        friend_message.put("msg", messages);
    }

    public Map<String, Object> getFriend_message() {
        return friend_message;
    }

    public void setFriend_message(Map<String, Object> friend_message) {
        this.friend_message = friend_message;
    }
}
