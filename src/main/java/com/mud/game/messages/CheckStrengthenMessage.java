package com.mud.game.messages;

import com.mud.game.structs.CheckStrengthenInfo;

/**
 * 装备强化检查的反馈
 */
public class CheckStrengthenMessage {

    private CheckStrengthenInfo strengthen_check;

    public CheckStrengthenMessage(CheckStrengthenInfo info) {
        this.strengthen_check = info;
    }

    public CheckStrengthenInfo getstrengthen_check() {
        return strengthen_check;
    }

    public void setstrengthen_check(CheckStrengthenInfo strengthen_check) {
        this.strengthen_check = strengthen_check;
    }
}
