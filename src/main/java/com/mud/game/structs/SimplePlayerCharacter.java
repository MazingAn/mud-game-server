package com.mud.game.structs;

import com.mud.game.handler.SchoolHandler;
import com.mud.game.object.supertypeclass.CommonCharacter;

public class SimplePlayerCharacter {
    private String dbref;
    private String name;
    private String gender;
    private float age;
    private String school;

    public SimplePlayerCharacter(){}

    public SimplePlayerCharacter(CommonCharacter character) {
        this.dbref = character.getId();
        this.name = character.getName();
        this.gender = character.getGender();
        this.age = character.getAge();
        this.school = character.getSchool();
    }

    public String getDbref() {
        return dbref;
    }

    public void setDbref(String dbref) {
        this.dbref = dbref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public float getAge() {
        return age;
    }

    public void setAge(float age) {
        this.age = age;
    }

    public String getSchool() {
        if(SchoolHandler.mapping.get(school) == null){
            return "无门无派";
        }else{
            return SchoolHandler.mapping.get(school);
        }
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
