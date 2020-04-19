package com.mud.game.messages;

import com.mud.game.structs.PuppetInfo;

public class PuppetMessage {

    private PuppetInfo puppet;

    public PuppetMessage() {
    }

    public PuppetMessage(PuppetInfo puppet) {
        this.puppet = puppet;
    }

    public PuppetInfo getPuppet() {
        return puppet;
    }

    public void setPuppet(PuppetInfo puppet) {
        this.puppet = puppet;
    }
}
