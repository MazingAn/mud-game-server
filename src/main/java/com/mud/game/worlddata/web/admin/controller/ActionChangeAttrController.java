package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.ActionChangeAttr;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供ActionChangeAttr的增删改查
 * */
@RestController
@RequestMapping("/ActionChangeAttr")
@Api(tags = "D:事件管理接口")
public class ActionChangeAttrController {
    /**
     * 增加ActionChangeAttr
     *
     * @param newActionChangeAttr 表单提交的ActionChangeAttr
     *
     * @return 保存后的ActionChangeAttr信息
     * */
    @PostMapping("/add")
    @ApiOperation("增加接受事件内容")
    public ActionChangeAttr addActionChangeAttr(@Valid  @RequestBody ActionChangeAttr newActionChangeAttr) {
        return DbMapper.actionChangeAttrRepository.save(newActionChangeAttr);
    }

    /**
     * 查询ActionChangeAttr
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    @ApiOperation("分页显示修改属性事件")
    public Page<ActionChangeAttr> query(@RequestParam(defaultValue="0") int page,
                                         @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<ActionChangeAttr> pageResult = DbMapper.actionChangeAttrRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedActionChangeAttr 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    @ApiOperation("编辑修改属性事件内容")
    public ActionChangeAttr editActionChangeAttr(@RequestBody ActionChangeAttr updatedActionChangeAttr, @PathVariable Long id) {
        updatedActionChangeAttr.setId(id);
        return DbMapper.actionChangeAttrRepository.save(updatedActionChangeAttr);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @ApiOperation("删除修改属性事件内容")
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteActionChangeAttr(@Valid @PathVariable Long id){
        DbMapper.actionChangeAttrRepository.deleteById(id);
    }

}
