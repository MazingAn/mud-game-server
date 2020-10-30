package com.mud.game.combat;

import java.util.Date;
import java.util.Map;

public class NpcBoundItemInfo {
    /**
     * 击杀者id
     */
    private String killerId;
    /**
     * 生成物品
     */
    private Map<String, Integer> npcBoundItemMap;

    public NpcBoundItemInfo(String killerId, Map<String, Integer> npcBoundItemMap) {
        this.killerId = killerId;
        this.npcBoundItemMap = npcBoundItemMap;
    }

    public NpcBoundItemInfo() {
    }

    public String getKillerId() {
        return killerId;
    }

    public void setKillerId(String killerId) {
        this.killerId = killerId;
    }

    public Map<String, Integer> getNpcBoundItemMap() {
        return npcBoundItemMap;
    }

    public void setNpcBoundItemMap(Map<String, Integer> npcBoundItemMap) {
        this.npcBoundItemMap = npcBoundItemMap;
    }
}
