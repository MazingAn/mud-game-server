package com.mud.game.structs;

import com.mud.game.handler.QuestObjectiveTypeHandler;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.object.typeclass.WorldRoomObject;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.NormalObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;

public class ObjectiveShowStatus {

    private int accomplished;

    private String object;

    private String target;

    private int toal;

    public ObjectiveShowStatus(ObjectiveStatus status) {
        this.accomplished = status.getAccomplished();
        this.target = QuestObjectiveTypeHandler.objectiveTypeMapper.get(status.getObjectiveType());
        try{
            switch (status.getObjectiveType()){
                case QuestObjectiveTypeHandler.OBJECTIVE_KILL:
                    CommonCharacter character = GameCharacterManager.getCharacterObject(status.getObject());
                    this.object = character.getName();
                case QuestObjectiveTypeHandler.OBJECTIVE_OBJECT:
                    NormalObject normalObject = DbMapper.normalObjectRepository.findNormalObjectByDataKey(status.getObject());
                    this.object = normalObject.getName();
                case QuestObjectiveTypeHandler.OBJECTIVE_ARRIVE:
                    WorldRoomObject worldRoomObject = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(status.getObject());
                    this.object = worldRoomObject.getName();
                case QuestObjectiveTypeHandler.OBJECTIVE_TALK:
                    WorldNpcObject npc = MongoMapper.worldNpcObjectRepository.findWorldNpcObjectByDataKey(status.getObject());
                    this.object = npc.getName();
            }
        }catch (Exception e){
            this.object = "";
        }
        this.toal = status.getTotal();
    }

    public int getAccomplished() {
        return accomplished;
    }

    public void setAccomplished(int accomplished) {
        this.accomplished = accomplished;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getToal() {
        return toal;
    }

    public void setToal(int toal) {
        this.toal = toal;
    }
}
