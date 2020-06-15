package com.mud.game.messages;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MsgMessage {
    private String msg;

    public MsgMessage(String message){
        this.msg = message;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
