package com.mud.game.structs;

import com.mud.game.utils.modelsutils.Mark;

import java.io.Serializable;

public class AttachmentInfo implements Serializable {
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
    private String dbref;
    /**
     * 数量
     */
    private int number;
    /**
     * dataKey
     */
    private String dataKey;

    //装备的品级
    private int quality;
    // 物品等级
    private int level;

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

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDbref() {
        return dbref;
    }

    public void setDbref(String dbref) {
        this.dbref = dbref;
    }
}
