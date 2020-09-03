package com.mud.game.messages;

import com.mud.game.object.typeclass.MailObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.structs.MailObjectInfo;

import java.util.ArrayList;
import java.util.List;

public class MailObjectMessage {
    private List<MailObjectInfo> check_mail;

    public MailObjectMessage(PlayerCharacter character, List<MailObject> check_mail) {
        List<MailObjectInfo> checkMailList = new ArrayList<>();
        for (int i = 0; i < check_mail.size(); i++) {
            checkMailList.add(new MailObjectInfo(check_mail.get(i)));

        }
        this.check_mail = checkMailList;
    }

    public List<MailObjectInfo> getCheck_mail() {
        return check_mail;
    }

    public void setCheck_mail(List<MailObjectInfo> check_mail) {
        this.check_mail = check_mail;
    }
}
