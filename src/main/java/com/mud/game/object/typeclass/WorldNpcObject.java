package com.mud.game.object.typeclass;

import com.mud.game.object.supertypeclass.Character;

import java.util.Set;

public class WorldNpcObject extends Character {
    // 绑定的事件
    public Set<String> events;
    // 绑定的对话
    public Set<String> dialogues;
    // 默认对话
    public String defaultDialogue;
    // 绑定的任务
    public Set<String> quests;
    public String showCondition;
    // 称号信息
    public String title;
    public String schoolTitle;

    public Set<String> getEvents() {
        return events;
    }

    public void setEvents(Set<String> events) {
        this.events = events;
    }

    public Set<String> getDialogues() {
        return dialogues;
    }

    public void setDialogues(Set<String> dialogues) {
        this.dialogues = dialogues;
    }

    public String getDefaultDialogue() {
        return defaultDialogue;
    }

    public void setDefaultDialogue(String defaultDialogue) {
        this.defaultDialogue = defaultDialogue;
    }

    public Set<String> getQuests() {
        return quests;
    }

    public void setQuests(Set<String> quests) {
        this.quests = quests;
    }

    public String getShowCondition() {
        return showCondition;
    }

    public void setShowCondition(String showCondition) {
        this.showCondition = showCondition;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSchoolTitle() {
        return schoolTitle;
    }

    public void setSchoolTitle(String schoolTitle) {
        this.schoolTitle = schoolTitle;
    }

}
