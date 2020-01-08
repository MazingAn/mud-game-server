package com.mud.game.object.typeclass;

import com.mud.game.object.supertypeclass.WorldObject;

import java.util.Set;

public class WorldObjectObject extends WorldObject {
    private String icon;
    private Set<String> events;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Set<String> getEvents() {
        return events;
    }

    public void setEvents(Set<String> events) {
        this.events = events;
    }
}
