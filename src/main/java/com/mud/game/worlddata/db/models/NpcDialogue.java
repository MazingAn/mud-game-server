package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"npc", "dialogue"}))
@Mark(name="npc的对话")
public class NpcDialogue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name="编号")
    private Long id;

    @Mark(name="npc", link="npc")
    private String npc;

    @Mark(name="对话", link="dialogue")
    private String dialogue;

    @Mark(name="默认对话", link="dialogue")
    private boolean defaultDialogue;

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

    public String getDialogue() {
        return dialogue;
    }

    public void setDialogue(String dialogue) {
        this.dialogue = dialogue;
    }

    public boolean isDefaultDialogue() {
        return defaultDialogue;
    }

    public void setDefaultDialogue(boolean defaultDialogue) {
        this.defaultDialogue = defaultDialogue;
    }
}
