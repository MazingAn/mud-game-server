package com.mud.game.structs;

import com.mud.game.object.typeclass.WorldExitObject;

public class ExitInfo {
    private String from;
    private String to;

    public ExitInfo(WorldExitObject exit) {
        this.from = exit.getLocation();
        this.to = exit.getDestination();
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
