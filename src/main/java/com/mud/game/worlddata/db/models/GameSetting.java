package com.mud.game.worlddata.db.models;

import javax.persistence.*;

@Entity
public class GameSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String gameName;
    private String connectionScreen;
    private float globalCD;
    private float AutoCastSkillCD;
    private byte maxGemLevel;
    private byte maxEquipmentLevel;
    @Column(nullable = true)
    private String defaultHome;
    @Column(nullable = true)
    private String startLocation;
    @Column(nullable = true)
    private String defaultPlayerHome;
    @Column(nullable = true)
    private String defaultPlayerTemplate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getConnectionScreen() {
        return connectionScreen;
    }

    public void setConnectionScreen(String connectionScreen) {
        this.connectionScreen = connectionScreen;
    }

    public float getGlobalCD() {
        return globalCD;
    }

    public void setGlobalCD(float globalCD) {
        this.globalCD = globalCD;
    }

    public float getAutoCastSkillCD() {
        return AutoCastSkillCD;
    }

    public void setAutoCastSkillCD(float autoCastSkillCD) {
        AutoCastSkillCD = autoCastSkillCD;
    }

    public byte getMaxGemLevel() {
        return maxGemLevel;
    }

    public void setMaxGemLevel(byte maxGemLevel) {
        this.maxGemLevel = maxGemLevel;
    }

    public byte getMaxEquipmentLevel() {
        return maxEquipmentLevel;
    }

    public void setMaxEquipmentLevel(byte maxEquipmentLevel) {
        this.maxEquipmentLevel = maxEquipmentLevel;
    }

    public String getDefaultHome() {
        return defaultHome;
    }

    public void setDefaultHome(String defaultHome) {
        this.defaultHome = defaultHome;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getDefaultPlayerHome() {
        return defaultPlayerHome;
    }

    public void setDefaultPlayerHome(String defaultPlayerHome) {
        this.defaultPlayerHome = defaultPlayerHome;
    }

    public String getDefaultPlayerTemplate() {
        return defaultPlayerTemplate;
    }

    public void setDefaultPlayerTemplate(String defaultPlayerTemplate) {
        this.defaultPlayerTemplate = defaultPlayerTemplate;
    }
}
