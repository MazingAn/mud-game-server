package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.web.admin.service.EntityInfoLoader;
import com.mud.game.worlddata.web.admin.structure.EntityInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取实体类的结构信息
 * */

@RestController
@RequestMapping("entity_info")
@Api(tags = "A：获取模型信息接口")
public class EntityInfoController {

    @GetMapping("/{tableName}")
    @ApiOperation("获取模型信息")
    @ApiImplicitParam(name="tableName", value = "模型名称")
    public EntityInfo getEntityInfo(@PathVariable String tableName){
        try{
            Class clazz = Class.forName("com.mud.game.worlddata.db.models." + tableName);
            return EntityInfoLoader.load(clazz);
        }catch (ClassNotFoundException e){
            return new EntityInfo();
        }
    }

}
