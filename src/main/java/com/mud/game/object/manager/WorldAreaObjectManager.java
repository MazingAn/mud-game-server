package com.mud.game.object.manager;

import com.mud.game.object.typeclass.WorldAreaObject;
import com.mud.game.worlddata.db.models.WorldArea;

public class WorldAreaObjectManager {

    /*
    * @ 新建WrodlArea
    * */
    public static WorldAreaObject build(WorldArea template){
        WorldAreaObject obj = new WorldAreaObject();
        obj.setDataKey(template.getDataKey());
        obj.setDescription(template.getDescription());
        obj.setName(template.getName());
        obj.setBackground(template.getBackground());
        obj.setAreaHome(template.getAreaHome());
        return obj;
    }

    /*
     * @更新WorldArea
     */
    public static void update(WorldAreaObject obj, WorldArea template){
        obj.setDescription(template.getDescription());
        obj.setName(template.getName());
        obj.setBackground(template.getBackground());
        obj.setAreaHome(template.getAreaHome());
    }


}
