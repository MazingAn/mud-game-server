package com.mud.game.structs;

import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.worlddata.db.models.Dialogue;
import com.mud.game.worlddata.db.models.DialogueSentence;

public class Sentence {

    private String dialogue;
    private boolean can_close;
    private int sentence;
    private String npc;
    private String content;
    private String speaker;
    private String icon;

    public Sentence(DialogueSentence dialogueSentence, PlayerCharacter playerCharacter,  WorldNpcObject npc) {
        this.dialogue = dialogueSentence.getDialogue();
        this.content = dialogueSentence.getContent();
        this.can_close = false;
        this.npc = npc.getDataKey();
        this.speaker = dialogueSentence.getSpeaker().equals("%c") ?playerCharacter.getName():npc.getName();
        this.icon = dialogueSentence.getIcon();
        this.sentence = dialogueSentence.getOrdinal();
    }

    public String getDialogue() {
        return dialogue;
    }

    public void setDialogue(String dialogue) {
        this.dialogue = dialogue;
    }

    public boolean isCan_close() {
        return can_close;
    }

    public void setCan_close(boolean can_close) {
        this.can_close = can_close;
    }

    public int getSentence() {
        return sentence;
    }

    public void setSentence(int sentence) {
        this.sentence = sentence;
    }

    public String getNpc() {
        return npc;
    }

    public void setNpc(String npc) {
        this.npc = npc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
