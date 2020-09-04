package com.mud.game.structs;

import com.mud.game.object.typeclass.MailObject;

import java.util.List;

public class MailObjectInfo {
    /**
     * 发送者
     */
    private String sendPlayerName;
    /**
     * 邮件id
     */
    private String mailObjectId;
    /**
     * 内容
     */
    private String content;

    /**
     * 附件
     */
    private List<AttachmentInfo> attachment;
    /**
     * 发起时间
     */
    private String starteTime;
    /**
     * 状态
     */
    private Boolean isReaded;

    public MailObjectInfo(MailObject mailObject) {
        this.mailObjectId = mailObject.getId();
        this.attachment = mailObject.getAttachments();
        this.content = mailObject.getContent();
        this.starteTime = mailObject.getStarteTime();
        this.sendPlayerName = mailObject.getInitiatorName();
        this.isReaded = mailObject.getReaded();
    }

    public String getSendPlayerName() {
        return sendPlayerName;
    }

    public void setSendPlayerName(String sendPlayerName) {
        this.sendPlayerName = sendPlayerName;
    }

    public String getMailObjectId() {
        return mailObjectId;
    }

    public void setMailObjectId(String mailObjectId) {
        this.mailObjectId = mailObjectId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<AttachmentInfo> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<AttachmentInfo> attachment) {
        this.attachment = attachment;
    }

    public String getStarteTime() {
        return starteTime;
    }

    public void setStarteTime(String starteTime) {
        this.starteTime = starteTime;
    }

    public Boolean getReaded() {
        return isReaded;
    }

    public void setReaded(Boolean readed) {
        isReaded = readed;
    }
}
