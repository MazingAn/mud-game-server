package com.mud.game.messages;

import com.mud.game.structs.CheckQualityInfo;

public class CheckQualityMessage {
    private CheckQualityInfo advanced_check;

    public CheckQualityMessage(CheckQualityInfo advanced_check) {
        this.advanced_check = advanced_check;
    }

    public CheckQualityInfo getAdvanced_check() {
        return advanced_check;
    }

    public void setAdvanced_check(CheckQualityInfo advanced_check) {
        this.advanced_check = advanced_check;
    }
}
