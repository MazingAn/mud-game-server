package com.mud.game.structs;

import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.EnemyRecordObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldAreaObject;
import com.mud.game.object.typeclass.WorldRoomObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 仇人信息
 */
public class EnemyInfo extends SimpleCharacter {
    private int level;
    private List<EnemyRecordInfo> enemyRecordObjectList;

    public EnemyInfo(SimpleCharacter simpleCharacter, int level, List<EnemyRecordObject> enemyRecordObjectList) {
        super(simpleCharacter);
        this.level = level;

        this.enemyRecordObjectList = new ArrayList<EnemyRecordInfo>();
        PlayerCharacter playerCharacter = null;
        WorldRoomObject worldRoomObject = null;
        WorldAreaObject worldAreaObject = null;
        for (EnemyRecordObject enemyRecordObject : enemyRecordObjectList) {
            playerCharacter = MongoMapper.playerCharacterRepository.findPlayerCharacterById(enemyRecordObject.getEnemyId());
            worldRoomObject = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(enemyRecordObject.getLocation());
            worldAreaObject = MongoMapper.worldAreaObjectRepository.findWorldAreaObjectByDataKey(worldRoomObject.getLocation());
            this.enemyRecordObjectList.add(new EnemyRecordInfo(playerCharacter.getName(), enemyRecordObject.getCreateTime(), worldAreaObject.getName() + "-" + worldRoomObject.getName()));
        }
    }

    public List<EnemyRecordInfo> getEnemyRecordObjectList() {
        return enemyRecordObjectList;
    }

    public void setEnemyRecordObjectList(List<EnemyRecordInfo> enemyRecordObjectList) {
        this.enemyRecordObjectList = enemyRecordObjectList;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
