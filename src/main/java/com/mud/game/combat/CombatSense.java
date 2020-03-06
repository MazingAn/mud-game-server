package com.mud.game.combat;

import com.mud.game.object.supertypeclass.CommonCharacter;

import java.util.ArrayList;

public class CombatSense {

    private ArrayList<CommonCharacter> redTeam;
    private ArrayList<CommonCharacter> blueTeam;


    public CombatSense(ArrayList<CommonCharacter> redTeam, ArrayList<CommonCharacter> blueTeam) {
        this.redTeam = redTeam;
        this.blueTeam = blueTeam;
    }

    public int getAliveNumberInTeam(ArrayList<CommonCharacter> team, int minHp) {
        int aliveNumber = 0;
        for(CommonCharacter character : team){
            if(character.getHp() <= minHp){
                aliveNumber ++;
            }
        }
        return aliveNumber;
    }

    public ArrayList<CommonCharacter> getRedTeam() {
        return redTeam;
    }

    public void setRedTeam(ArrayList<CommonCharacter> redTeam) {
        this.redTeam = redTeam;
    }

    public ArrayList<CommonCharacter> getBlueTeam() {
        return blueTeam;
    }

    public void setBlueTeam(ArrayList<CommonCharacter> blueTeam) {
        this.blueTeam = blueTeam;
    }
}
