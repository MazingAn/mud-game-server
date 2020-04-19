package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.web.admin.service.EntityInfoLoader;
import com.mud.game.worlddata.web.admin.structure.EntityInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取实体类的结构信息
 * */

@RestController
@RequestMapping("entity_info")
public class EntityInfoController {

    @GetMapping("/{tableName}")
    public EntityInfo getEntityInfo(@PathVariable String tableName){
        try{
            Class clazz = Class.forName("com.mud.game.worlddata.db.models." + tableName);
            return EntityInfoLoader.load(clazz);
        }catch (ClassNotFoundException e){
            return new EntityInfo();
        }
    }

}
