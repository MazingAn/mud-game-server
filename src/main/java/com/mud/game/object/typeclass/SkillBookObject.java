package com.mud.game.object.typeclass;

import com.mud.game.object.supertypeclass.CommonObject;

public class SkillBookObject extends CommonObject {
    private int max_level;
    private boolean use_potential;
    private String useCondition;
    private String currentSkill;

    public int getMax_level() {
        return max_level;
    }

    public void setMax_level(int max_level) {
        this.max_level = max_level;
    }

    public boolean isUse_potential() {
        return use_potential;
    }

    public void setUse_potential(boolean use_potential) {
        this.use_potential = use_potential;
    }


    public String getUseCondition() {
        return useCondition;
    }

    public void setUseCondition(String useCondition) {
        this.useCondition = useCondition;
    }

    public String getCurrentSkill() {
        return currentSkill;
    }

    public void setCurrentSkill(String currentSkill) {
        this.currentSkill = currentSkill;
    }
}
