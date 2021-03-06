package com.mud.game.structs;

import com.mud.game.handler.SchoolHandler;
import com.mud.game.object.account.Player;
import com.mud.game.object.manager.WorldNpcObjectManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldNpcObject;

public class SimpleCharacter {
    private String dbref;
    private String name;
    private String gender;
    private float age;
    private String school;
    private boolean provide_quest;
    private boolean complete_quest;

    public SimpleCharacter(){}

    public SimpleCharacter(CommonCharacter character) {
        this.dbref = character.getId();
        this.name = character.getName();
        this.gender = character.getGender();
        this.age = character.getAge();
        this.school = character.getSchool();
        this.provide_quest = false;
        this.complete_quest = false;
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

    public boolean isProvide_quest() {
        return provide_quest;
    }

    public void setProvide_quest(boolean provide_quest) {
        this.provide_quest = provide_quest;
    }

    public boolean isComplete_quest() {
        return complete_quest;
    }

    public void setComplete_quest(boolean complete_quest) {
        this.complete_quest = complete_quest;
    }
}
