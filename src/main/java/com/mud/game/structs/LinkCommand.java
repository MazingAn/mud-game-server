package com.mud.game.structs;

import com.mud.game.object.supertypeclass.BaseGameObject;
import com.mud.game.object.typeclass.PlayerCharacter;

import java.util.HashMap;
import java.util.Map;

public class LinkCommand {
    Map<String, Object> cmd;

    public LinkCommand(BaseGameObject obj) {
        //  根据对象类型决定给什么命令，这个命令玩家点击之后就可以直接执行
        if(obj.getClass() == PlayerCharacter.class){
            Map<String, Object> cmd = new HashMap<String, Object>();
            cmd.put("cmd", "friend_chat");
            cmd.put("name", obj.getName());
            cmd.put("args", "");
            cmd.put("dbref", obj.getId());
            this.cmd = cmd;
        }
    }

    public Map<String, Object> getCmd() {
        return cmd;
    }

    public void setCmd(Map<String, Object> cmd) {
        this.cmd = cmd;
    }
}
