package com.mud.game.messages;

import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.structs.LinkCommand;

import java.util.HashMap;
import java.util.Map;

public class SayMessage {

    private Map<String, Object> chat_msg;

    public SayMessage(String message, PlayerCharacter caller, PlayerCharacter receiver){
        chat_msg = new HashMap<>();
        chat_msg.put("caller", new LinkCommand(caller));
        chat_msg.put("receiver", new LinkCommand(receiver));
        chat_msg.put("message",message);
    }

    public Map<String, Object> getChat_msg() {
        return chat_msg;
    }

    public void setChat_msg(Map<String, Object> chat_msg) {
        this.chat_msg = chat_msg;
    }
}
