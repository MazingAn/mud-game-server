package com.mud.game.messages;

import com.mud.game.object.typeclass.MailObject;

import java.util.HashMap;
import java.util.Map;

public class DeteleMailMessage {
    private Map<String, String> delete_mail;

    public DeteleMailMessage(MailObject mailObject) {

        this.delete_mail = new HashMap<String, String>() {{
            put("mailObjectId", mailObject.getId());
        }};
    }

    public Map<String, String> getDelete_mail() {
        return delete_mail;
    }

    public void setDelete_mail(Map<String, String> delete_mail) {
        this.delete_mail = delete_mail;
    }
}
