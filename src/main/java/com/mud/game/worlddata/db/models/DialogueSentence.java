package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Mark(name = "对话句子")
public class DialogueSentence {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name="编号")
    private Long id;

    @Column(unique = true)
    @Mark(name="标识")
    @NotBlank(message = "数据标识不能为空")
    @Size(min=2, max=64, message = "标识长度必须在2到64之间")
    private String dataKey;

    @Mark(name = "描述名称")
    private String name;

    @Mark(name="对话", link = "dialogue")
    @NotBlank(message = "数据标识不能为空")
    @Size(min=2, max=64, message = "标识长度必须在2到64之间")
    private String dialogue;

    @Mark(name="顺序")
    private int ordinal;

    @Mark(name="说话方")
    private String speaker;

    @Mark(name="内容")
    private String content;

    @Mark(name="图标")
    private String icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public String getDialogue() {
        return dialogue;
    }

    public void setDialogue(String dialogue) {
        this.dialogue = dialogue;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
