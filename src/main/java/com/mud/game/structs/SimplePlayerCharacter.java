package com.mud.game.structs;

import com.mud.game.object.supertypeclass.Character;
import com.mud.game.object.typeclass.PlayerCharacter;

public class SimplePlayerCharacter {
    private String dbref;
    private String name;
    private String gender;
    private float age;

    public SimplePlayerCharacter(){};

    public SimplePlayerCharacter(Character character) {
        this.dbref = character.getId();
        this.name = character.getName();
        this.gender = character.getGender();
        this.age = character.getAge();
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
}
