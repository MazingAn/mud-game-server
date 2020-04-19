package com.mud.game.object.typeclass;

import com.mud.game.object.supertypeclass.CommonItemContainer;

public class WareHouseObject extends CommonItemContainer {
    /*
    * 玩家的仓库
    * */
    private boolean locked;
    private String password;

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
