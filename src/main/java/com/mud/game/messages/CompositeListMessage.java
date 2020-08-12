package com.mud.game.messages;

import com.mud.game.object.typeclass.CompositeMaterialObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worlddata.db.models.ConsignmentInformation;

import java.util.List;

public class CompositeListMessage {
    private List<CompositeMaterialObject> compositeList;

    public CompositeListMessage(List<CompositeMaterialObject> compositeList) {
        this.compositeList = compositeList;
    }

    public List<CompositeMaterialObject> getCompositeList() {
        return compositeList;
    }

    public void setCompositeList(List<CompositeMaterialObject> compositeList) {
        this.compositeList = compositeList;
    }
}
