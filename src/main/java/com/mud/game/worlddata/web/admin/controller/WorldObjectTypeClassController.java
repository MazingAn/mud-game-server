package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.TypeClassMapper;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.WorldArea;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 游戏区域Controller
 * 提供WorldArea的增删改查
 */
@RestController
@RequestMapping("/WorldObjectTypeClass")
@Api(tags = "类型类管理接口")
public class WorldObjectTypeClassController {

    /**
     * 获取类别类
     */
    @GetMapping("/all")
    public List<Map<String, String>> all() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("name", "世界物品");
        map.put("dataKey", TypeClassMapper.WORLD_OBJECT);
        list.add(map);
        map = new HashMap<>();
        map.put("name", "物品生成器物品");
        map.put("dataKey", TypeClassMapper.WORLD_OBJECT_CREATOR);
        list.add(map);
        return list;
    }

}
