package com.mud.game.structs;

import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.School;

public class FriendInfo {
    private String dbref;
    private String gender;
    private String name;
    private String school;

    public FriendInfo(PlayerCharacter playerCharacter) {
        this.dbref = playerCharacter.getId();
        this.gender = playerCharacter.getGender();
        this.name = playerCharacter.getName();
        this.school = playerCharacter.getSchool();
    }

    public String getDbref() {
        return dbref;
    }

    public void setDbref(String dbref) {
        this.dbref = dbref;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        if(school!=null && !(school.trim().equals(""))){
            School schoolRecord = DbMapper.schoolRepository.findSchoolByDataKey(school);
            return schoolRecord.getName();
        }
        return "无门无派";
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
