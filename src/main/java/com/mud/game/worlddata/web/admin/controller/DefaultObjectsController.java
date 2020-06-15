package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.DefaultObjects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供DefaultObjects的增删改查
 * */
@RestController
@RequestMapping("/DefaultObjects")
public class DefaultObjectsController {
    /**
     * 增加DefaultObjects
     *
     * @param newDefaultObjects 表单提交的DefaultObjects
     *
     * @return 保存后的DefaultObjects信息
     * */
    @PostMapping("/add")
    public DefaultObjects addDefaultObjects(@Valid  @RequestBody DefaultObjects newDefaultObjects) {
        return DbMapper.defaultObjectsRepository.save(newDefaultObjects);
    }

    /**
     * 查询DefaultObjects
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<DefaultObjects> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<DefaultObjects> pageResult = DbMapper.defaultObjectsRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedDefaultObjects 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public DefaultObjects editDefaultObjects(@RequestBody DefaultObjects updatedDefaultObjects, @PathVariable Long id) {
        updatedDefaultObjects.setId(id);
        return DbMapper.defaultObjectsRepository.save(updatedDefaultObjects);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteDefaultObjects(@Valid @PathVariable Long id){
        DbMapper.defaultObjectsRepository.deleteById(id);
    }

}
