package com.mud.game.messages;

import com.mud.game.object.supertypeclass.CommonObject;

public class GettingObjectMessage {
    private String msg;

    public GettingObjectMessage(CommonObject commonObject, int number) {
        this.msg =  String.format(
                    "你获得了{g %s %s{n{y%s{n。",
                    number,
                    commonObject.getUnitName(),
                    commonObject.getName()
                );
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
