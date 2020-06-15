package com.mud.game.messages;

import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.structs.EmbeddedCommand;

import java.util.ArrayList;
import java.util.List;

public class RebornCommandsMessage {

    private List<EmbeddedCommand> reborn_commands;

    public RebornCommandsMessage() {
    }

    public RebornCommandsMessage(PlayerCharacter playerCharacter) {
        this.reborn_commands = new ArrayList<>();
        this.reborn_commands.add(new EmbeddedCommand("原地复活", "reborn_here", ""));
        this.reborn_commands.add(new EmbeddedCommand("回城复活", "reborn_home", ""));
    }

    public List<EmbeddedCommand> getReborn_commands() {
        return reborn_commands;
    }

    public void setReborn_commands(List<EmbeddedCommand> reborn_commands) {
        this.reborn_commands = reborn_commands;
    }
}
