package com.mud.game.messages;

import com.mud.game.object.typeclass.PlayerCharacter;

import java.util.HashMap;
import java.util.Map;

public class SayMessage {

    private Map<String, Object> chat_message;

    public SayMessage(String message, PlayerCharacter caller, PlayerCharacter receiver, boolean isSender) {
        chat_message = new HashMap<>();
        String key = null;
        String name = null;
        String dbref = null;
        if (isSender) {
            key = "from";
            name = caller.getName();
            dbref = caller.getId();
        } else {
            key = "to";
            name = receiver.getName();
            dbref = receiver.getId();
        }
        String finalDbref = dbref;
        String finalName = name;
        chat_message.put(key, new HashMap<String, String>() {{
            put("name", finalName);
            put("dbref", finalDbref);
        }});
        chat_message.put("content", message);

    }

    public Map<String, Object> getChat_message() {
        return chat_message;
    }

    public void setChat_message(Map<String, Object> chat_message) {
        this.chat_message = chat_message;
    }
}
