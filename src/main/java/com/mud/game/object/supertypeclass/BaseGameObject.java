package com.mud.game.object.supertypeclass;

import com.mud.game.net.session.GameSessionService;
import com.mud.game.structs.Message;
import com.mud.game.utils.jsonutils.JsonResponse;
import org.springframework.data.annotation.Id;
import org.yeauty.pojo.Session;

import java.util.List;


public class BaseGameObject {

    @Id
    private String id;
    private String dataKey;
    private String name;
    private String description;
    private String location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDataKey() {
        return dataKey;
    }

    public String getDescription() {
        return description;
    }

    public void msg(Object messageObject){
        Session session = GameSessionService.getSessionByCallerId(this.id);
        if(session != null){
            session.sendText(JsonResponse.JsonStringResponse(messageObject));
        }
    }

}