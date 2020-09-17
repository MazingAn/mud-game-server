package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.*;

@Entity
@Mark(name = "NPC的当铺")
public class NpcDangPu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name = "编号")
    private Long id;

    @Mark(name = "npc", link = "npc")
    private String npc;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNpc() {
        return npc;
    }

    public void setNpc(String npc) {
        this.npc = npc;
    }

}
