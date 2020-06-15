package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.*;

@Entity
@Mark(name="商店")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name="编号")
    private Long id;

    @Column(unique = true)
    @Mark(name="标识")
    private String dataKey;

    @Mark(name="名称")
    private String name;

    @Mark(name="描述")
    private String description;

    @Mark(name="开启条件")
    private String openCondition;

    @Mark(name="是否是系统商店")
    private boolean systemShop;

    @Mark(name="图标")
    private String icon;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOpenCondition() {
        return openCondition;
    }

    public void setOpenCondition(String openCondition) {
        this.openCondition = openCondition;
    }

    public boolean isSystemShop() {
        return systemShop;
    }

    public void setSystemShop(boolean systemShop) {
        this.systemShop = systemShop;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
