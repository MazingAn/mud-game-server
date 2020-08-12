package com.mud.game.messages;

import com.mud.game.object.typeclass.CompositeMaterialObject;

import java.util.List;
import java.util.Map;

public class CompositeListMessage {
    private Map<String, List<CompositeMaterialObject>> compositeList;

    public CompositeListMessage(Map<String, List<CompositeMaterialObject>> compositeList) {
        this.compositeList = compositeList;
    }

    public Map<String, List<CompositeMaterialObject>> getCompositeList() {
        return compositeList;
    }

    public void setCompositeList(Map<String, List<CompositeMaterialObject>> compositeList) {
        this.compositeList = compositeList;
    }
}
