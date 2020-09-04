package com.mud.game.structs;

import java.io.Serializable;

public class AttachmentInfo  implements Serializable {
    /**
     * 名称
     */
    private String attachmentName;
    /**
     * 图片
     */
    private String icon;
    /**
     * objectId
     */
    private String objectId;
    /**
     * 数量
     */
    private int number;
    /**
     * dataKey
     */
    private String dataKey;

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }
}
