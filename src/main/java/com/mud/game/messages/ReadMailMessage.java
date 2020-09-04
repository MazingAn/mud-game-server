package com.mud.game.messages;

import com.mud.game.object.typeclass.MailObject;

import java.util.HashMap;
import java.util.Map;

public class ReadMailMessage {
    private Map<String, String> replace_mail_status;

    public ReadMailMessage(MailObject mailObject) {

        this.replace_mail_status = new HashMap<String, String>() {{
            put("mailObjectId", mailObject.getId());
        }};
    }

    public Map<String, String> getReplace_mail_status() {
        return replace_mail_status;
    }

    public void setReplace_mail_status(Map<String, String> replace_mail_status) {
        this.replace_mail_status = replace_mail_status;
    }
}
