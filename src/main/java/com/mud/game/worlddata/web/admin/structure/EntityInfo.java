package com.mud.game.worlddata.web.admin.structure;

import java.util.ArrayList;
import java.util.List;

public class EntityInfo {
    public String tableName;
    public List<FieldInfo> fieldsInfo;


    public EntityInfo() {
        this.fieldsInfo = new ArrayList<>();
    }

}
