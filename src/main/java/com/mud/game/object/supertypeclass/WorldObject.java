package com.mud.game.object.supertypeclass;


public class WorldObject extends BaseGameObject {
    private String showCondition;
    private String typeClass;

    public String getShowCondition() {
        return showCondition;
    }

    public void setShowCondition(String showCondition) {
        this.showCondition = showCondition;
    }

    public String getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(String typeClass) {
        this.typeClass = typeClass;
    }
}
