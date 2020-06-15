package com.mud.game.messages;

import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.structs.BufferInfo;

import java.util.Map;

public class BuffersMessage {
    private Map<String, Map<String, Object>> buffers;

    public BuffersMessage(CommonCharacter character) {
        this.buffers = new BufferInfo(character).getBuffers();
    }

    public Map<String, Map<String, Object>> getBuffers() {
        return buffers;
    }

    public void setBuffers(Map<String, Map<String, Object>> buffers) {
        this.buffers = buffers;
    }
}
