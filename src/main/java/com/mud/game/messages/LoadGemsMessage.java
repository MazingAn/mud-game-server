package com.mud.game.messages;

import com.mud.game.object.typeclass.GemObject;
import com.mud.game.structs.LoadGemsInfo;
import com.mud.game.worlddata.db.models.Gem;

import java.util.ArrayList;
import java.util.List;

public class LoadGemsMessage {

    private List<LoadGemsInfo> available_gems;

    public LoadGemsMessage(List<GemObject> gemList) {
        if (gemList.size() == 0) {
            this.available_gems = null;
        } else {
            available_gems = new ArrayList<>();
            for (int i = 0; i < gemList.size(); i++) {
                if (!contains(gemList.get(i))) {
                    this.available_gems.add(new LoadGemsInfo(gemList.get(i)));
                }
            }
        }
    }

    public List<LoadGemsInfo> getAvailable_gems() {
        return available_gems;
    }

    public boolean contains(GemObject gem) {
        for (int i = 0; i < this.available_gems.size(); i++) {
            if (this.available_gems.get(i).getDbref().equals(gem.getId())) {
                return true;
            }
        }
        return false;
    }

    public void setAvailable_gems(List<LoadGemsInfo> available_gems) {
        this.available_gems = available_gems;
    }
}
