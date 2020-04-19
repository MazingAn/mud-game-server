package com.mud.game.object.typeclass;

import com.mud.game.object.supertypeclass.BaseGameObject;
import com.mud.game.object.supertypeclass.WorldObject;
import com.mud.game.worlddata.db.models.WorldArea;
import com.mud.game.worldrun.db.repository.WorldAreaObjectRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.net.URL;


public class WorldAreaObject extends WorldObject {

    /*
    * 继承父类的属性
    * */
    private String areaHome;
    private String background;

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getAreaHome() {
        return areaHome;
    }

    public void setAreaHome(String areaHome) {
        this.areaHome = areaHome;
    }

}
