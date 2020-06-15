package com.mud.game.messages;

import com.mud.game.structs.SimpleSkill;

import java.util.Map;
import java.util.Set;

public class TeachersSkillMessage {
    private Map<String, Set<SimpleSkill>> teacher_skills;

    public TeachersSkillMessage(Map<String, Set<SimpleSkill>> teacher_skills) {
        this.teacher_skills = teacher_skills;
    }

    public Map<String, Set<SimpleSkill>> getTeacher_skills() {
        return teacher_skills;
    }

    public void setTeacher_skills(Map<String, Set<SimpleSkill>> teacher_skills) {
        this.teacher_skills = teacher_skills;
    }
}
