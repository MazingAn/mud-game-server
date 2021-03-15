package com.mud.game.structs;

import java.util.List;

/**
 * 好友信息
 */
public class FriendInfo extends SimpleCharacter {
    private int level;
    private List<EnemyRecordInfo> enemyRecordObjectList;

    public FriendInfo(SimpleCharacter simpleCharacter, int level) {
        super(simpleCharacter);
        this.level = level;
    }
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


}
