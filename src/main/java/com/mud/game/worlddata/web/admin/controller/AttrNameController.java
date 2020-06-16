package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.handler.StatusHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/AttrName")
@Api(tags = "用户属性接口")
public class AttrNameController {

    @GetMapping("/all")
    @ApiOperation("获取所有用户属性的接口")
    public Map<String, String> findAll(){
        return StatusHandler.attrMapping;
    }
}
