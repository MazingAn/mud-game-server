package com.mud.game.structs;

import java.util.HashMap;
import java.util.Map;

public class EmbeddedCommand {

    /*
    * 嵌入式的命令
    * 玩家可以通过这个结构，在前端通过点击按钮的形式执行命令
    * */

    private String name;
    private String cmd;
    private Object args;

    public EmbeddedCommand(String name, String cmdKey, Object args) {
        this.name = name;
        this.cmd = cmdKey;
        this.args = args;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public Object getArgs() {
        return args;
    }

    public void setArgs(Object args) {
        this.args = args;
    }
}
