package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.WorldObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


/**
 * 游戏区域Controller
 * 提供WorldObject的增删改查
 * */
@RestController
@RequestMapping("/world_object")
public class WorldObjectController {
    /**
     * 增加WorldObject
     *
     * @param newWorldObject 表单提交的WorldObject
     *
     * @return 保存后的WorldObject信息
     * */
    @PostMapping("/add")
    public WorldObject addWorldObject(@RequestBody WorldObject newWorldObject) {
        return DbMapper.worldObjectRepository.save(newWorldObject);
    }

    /**
     * 查询WorldObject
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<WorldObject> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<WorldObject> pageResult = DbMapper.worldObjectRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedWorldObject 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public WorldObject editWorldObject(@RequestBody WorldObject updatedWorldObject, @PathVariable Long id) {
        updatedWorldObject.setId(id);
        return DbMapper.worldObjectRepository.save(updatedWorldObject);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    public WorldObject deleteWorldObject(@PathVariable Long id){
        return DbMapper.worldObjectRepository.deleteWorldObjectById(id);
    }

}
