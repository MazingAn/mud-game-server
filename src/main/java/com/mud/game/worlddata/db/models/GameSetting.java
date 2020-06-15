package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.*;

@Entity
@Mark(name="游戏设置")
public class GameSetting {

    /** id编号 唯一标识 只读 */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name="编号")
    private Long id;

    /** 游戏名称 */
    @Mark(name = "欢迎信息")
    private String gameName;

    /** 欢迎屏幕信息 */
    @Mark(name = "连接信息")
    private String connectionScreen;

    /** 全局CD */
    @Mark(name = "全局CD")
    private float globalCD;

    /** 自动释放技能CD */
    @Mark(name = "自动释放技能CD")
    private float AutoCastSkillCD;

    /** 最大宝石等级 */
    @Mark(name = "最大宝石等级")
    private byte maxGemLevel;

    /** 最大装备等级 */
    @Mark(name = "最大装备等级")
    private byte maxEquipmentLevel;

    /** 默认房间 */
    @Column(nullable = true)
    @Mark(name = "默认房间", link = "worldRoom")
    private String defaultHome;

    /**起始房间*/
    @Column(nullable = true)
    @Mark(name = "起始房间", link = "worldRoom")
    private String startLocation;

    /**默认玩家房间*/
    @Column(nullable = true)
    @Mark(name = "默认玩家房间", link = "worldRoom")
    private String defaultPlayerHome;

    /**默认玩家模板*/
    @Column(nullable = true)
    @Mark(name = "默认玩家模板", link = "characterModel")
    private String defaultPlayerTemplate;

    /**默认玩家技能*/
    @Column(nullable = false)
    @Mark(name = "默认攻击技能", link = "skill")
    private String defaultSkill;

    /**默认复活道具*/
    @Column(nullable = false)
    @Mark(name = "默认复活道具", link = "allObjects")
    private String defaultRebornObject;

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

    public String getDefaultSkill() {
        return defaultSkill;
    }

    public void setDefaultSkill(String defaultSkill) {
        this.defaultSkill = defaultSkill;
    }

    public String getDefaultRebornObject() {
        return defaultRebornObject;
    }

    public void setDefaultRebornObject(String defaultRebornObject) {
        this.defaultRebornObject = defaultRebornObject;
    }
}
