package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.DefaultObjects;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "默认物品维护接口")
public class DefaultObjectsController {
    /**
     * 增加DefaultObjects
     *
     * @param newDefaultObjects 表单提交的DefaultObjects
     *
     * @return 保存后的DefaultObjects信息
     * */
    @PostMapping("/add")
    @ApiOperation("增加默认物品")
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
    @ApiOperation("分页获取默认物品列表")
    public Page<DefaultObjects> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<DefaultObjects> pageResult = DbMapper.defaultObjectsRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改
     *
     * @param updatedDefaultObjects 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    @ApiOperation("编辑默认物品记录")
    @ApiImplicitParam(name="id", value="记录id")
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
    @ApiOperation("删除默认物品记录")
    @ApiImplicitParam(name="id", value="记录id")
    public void deleteDefaultObjects(@Valid @PathVariable Long id){
        DbMapper.defaultObjectsRepository.deleteById(id);
    }

}
