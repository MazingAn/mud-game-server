package com.mud.game.structs;

import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.statements.buffers.BufferManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BufferInfo {
    private Map<String, Map<String, Object>> buffers;

    public BufferInfo(CommonCharacter character) {
        buffers = new HashMap<>();
        Map<String, Set<String>> characterBuffers = character.getBuffers();
        for(String name : characterBuffers.keySet()){
            if(! characterBuffers.get(name).isEmpty()){
                Map<String, Object> currentBufferInfo = new HashMap<>();
                int added = characterBuffers.get(name).size();
                boolean debuffer = !BufferManager.getBuffer(characterBuffers.get(name).toArray()[0].toString()).goodBuffer;
                currentBufferInfo.put("added", added);
                currentBufferInfo.put("debuffer", debuffer);
                buffers.put(name, currentBufferInfo);
            }
        }
    }

    public Map<String, Map<String, Object>> getBuffers() {
        return buffers;
    }

    public void setBuffers(Map<String, Map<String, Object>> buffers) {
        this.buffers = buffers;
    }
}
