package com.mud.game.object.builder;

import com.mud.game.object.typeclass.MailObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.structs.AttachmentInfo;
import com.mud.game.worlddata.db.models.supermodel.BaseCommonObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MailObjectBuilder {

    public static void save(PlayerCharacter caller, PlayerCharacter target, String content, List<BaseCommonObject> commonObjectList, int number) {
        MailObject mailObject = new MailObject();
        mailObject.setInitiatorId(caller.getId());
        mailObject.setInitiatorName(caller.getName());
        mailObject.setRecipientId(target.getId());
        mailObject.setRecipientName(target.getName());
        mailObject.setContent(content);
        mailObject.setReaded(false);
        SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm:ss");//设置日期格式
        mailObject.setStarteTime(df.format(new Date()));
        //附件
        List<AttachmentInfo> attachmentInfoList = new ArrayList<>();
        for (int i = 0; i < commonObjectList.size(); i++) {
            attachmentInfoList.add(MailObjectBuilder.getMailObjectInfo(commonObjectList.get(i), number));
        }
        mailObject.setAttachments(attachmentInfoList);
        MongoMapper.mailObjectRepository.save(mailObject);
    }

    public static AttachmentInfo getMailObjectInfo(BaseCommonObject baseCommonObject, int number) {
        AttachmentInfo attachmentInfo = new AttachmentInfo();
        attachmentInfo.setAttachmentName(baseCommonObject.getName());
        attachmentInfo.setIcon(baseCommonObject.getIcon());
        attachmentInfo.setObjectId(String.valueOf(baseCommonObject.getId()));
        attachmentInfo.setNumber(number);
        attachmentInfo.setDataKey(baseCommonObject.getDataKey());
        return attachmentInfo;
    }
}
