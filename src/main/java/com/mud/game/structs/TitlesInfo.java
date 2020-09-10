package com.mud.game.structs;

import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worlddata.db.models.PlayerTitle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TitlesInfo {
    private String name;
    private Boolean using;
    private List<EmbeddedCommand> cmds;

    public TitlesInfo(PlayerTitle playerTitle, PlayerCharacter character) {
        this.name = playerTitle.getName();
        this.cmds = new ArrayList<>();
        Map<String, Object> args = new HashMap<>();
        if (playerTitle.getDataKey() != null && playerTitle.getDataKey().equals(character.getTitle())) {
            this.using = true;
            args.put("dataKey", playerTitle.getDataKey());
            this.cmds.add(new EmbeddedCommand("卸掉", "take_off_title", args));
        } else {
            this.using = false;
            args.put("dataKey", playerTitle.getDataKey());
            this.cmds.add(new EmbeddedCommand("装备", "equipment_title", args));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getUsing() {
        return using;
    }

    public void setUsing(Boolean using) {
        this.using = using;
    }

    public List<EmbeddedCommand> getCmds() {
        return cmds;
    }

    public void setCmds(List<EmbeddedCommand> cmds) {
        this.cmds = cmds;
    }
}
