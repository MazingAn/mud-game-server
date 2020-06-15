package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.handler.StatusHandler;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/AttrName")
public class AttrNameController {

    @RequestMapping("/all")
    public Map<String, String> findAll(){
        return StatusHandler.attrMapping;
    }
}
