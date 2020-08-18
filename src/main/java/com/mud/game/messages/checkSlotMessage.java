package com.mud.game.messages;

import com.mud.game.structs.checkSlotInfo;

/**
 * 返回装备强化检查的反馈
 */
public class checkSlotMessage {
    private checkSlotInfo slot_open_check;

    public checkSlotMessage(checkSlotInfo slot_open_check) {
        this.slot_open_check = slot_open_check;
    }

    public checkSlotInfo getSlot_open_check() {
        return slot_open_check;
    }

    public void setSlot_open_check(checkSlotInfo slot_open_check) {
        this.slot_open_check = slot_open_check;
    }
}
