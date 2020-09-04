package com.mud.game.object.typeclass;

import com.mud.game.structs.AttachmentInfo;

import javax.persistence.Id;
import java.util.List;

public class MailObject {
    @Id
    private String id;
    /**
     * 发起者信息
     */
    private String initiatorId;
    private String initiatorName;
    /**
     * 接收者信息
     */
    private String recipientId;
    private String recipientName;
    /**
     * 内容
     */
    private String content;

    /**
     * 附件
     */
    private List<AttachmentInfo> attachments;
    /**
     * 发起时间
     */
    private String starteTime;
    /**
     * 状态
     */
    private Boolean isReaded;
    /**
     * 标识
     */
    private Boolean dataKey;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(String initiatorId) {
        this.initiatorId = initiatorId;
    }

    public String getInitiatorName() {
        return initiatorName;
    }

    public void setInitiatorName(String initiatorName) {
        this.initiatorName = initiatorName;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public List<AttachmentInfo> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentInfo> attachments) {
        this.attachments = attachments;
    }

    public Boolean getDataKey() {
        return dataKey;
    }

    public void setDataKey(Boolean dataKey) {
        this.dataKey = dataKey;
    }
}
