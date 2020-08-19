package com.mud.game.messages;

import com.mud.game.object.typeclass.GemObject;
import com.mud.game.structs.ImbedGemsInfo;
import com.mud.game.structs.LoadGemsInfo;
import com.mud.game.worlddata.db.models.Gem;

import java.util.ArrayList;
import java.util.List;

public class ImbedGemsMessage {
    private List<ImbedGemsInfo> imbed_gems;

    public ImbedGemsMessage(List<GemObject> gemList) {
        if (gemList.size() == 0) {
            this.imbed_gems = null;
        } else {
            imbed_gems = new ArrayList<>();
            for (int i = 0; i < gemList.size(); i++) {
                this.imbed_gems.add(new ImbedGemsInfo(gemList.get(i)));
            }
        }
    }

    public List<ImbedGemsInfo> getImbed_gems() {
        return imbed_gems;
    }

    public void setImbed_gems(List<ImbedGemsInfo> imbed_gems) {
        this.imbed_gems = imbed_gems;
    }
}
