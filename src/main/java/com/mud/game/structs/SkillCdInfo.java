package com.mud.game.structs;

/**
 * 技能cd
 */
public class SkillCdInfo {
    private double cd;
    private String dbref;
    private String dataKey;

    public SkillCdInfo(double cd, String dbref, String dataKey) {
        this.cd = cd;
        this.dbref = dbref;
        this.dataKey = dataKey;
    }

    public double getCd() {
        return cd;
    }

    public void setCd(double cd) {
        this.cd = cd;
    }

    public String getDbref() {
        return dbref;
    }

    public void setDbref(String dbref) {
        this.dbref = dbref;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }
}
