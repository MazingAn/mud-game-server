package com.mud.game.combat;

import java.util.Date;
import java.util.Map;

public class NpcBoundItemInfo {
    /**
     * npc死亡生成战利品时间
     */
    private Date createTime;
    /**
     * 击杀者id
     */
    private String killerId;
    /**
     * 生成物品
     */
    private Map<String, Integer> npcBoundItemMap;

    public NpcBoundItemInfo(Date createTime, String killerId, Map<String, Integer> npcBoundItemMap) {
        this.createTime = createTime;
        this.killerId = killerId;
        this.npcBoundItemMap = npcBoundItemMap;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
