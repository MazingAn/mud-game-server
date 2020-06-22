package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.ActionMove;
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
 * 提供ActionMove的增删改查
 * */
@RestController
@RequestMapping("/ActionMove")
@Api(tags = "D:事件管理接口")
public class ActionMoveController {
    /**
     * 增加ActionMove
     *
     * @param newActionMove 表单提交的ActionMove
     *
     * @return 保存后的ActionMove信息
     * */
    @PostMapping("/add")
    @ApiOperation("增加接受事件内容")
    public ActionMove addActionMove(@Valid  @RequestBody ActionMove newActionMove) {
        return DbMapper.actionMoveRepository.save(newActionMove);
    }

    /**
     * 查询ActionMove
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    @ApiOperation("分页显示移动事件")
    public Page<ActionMove> query(@RequestParam(defaultValue="0") int page,
                                         @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<ActionMove> pageResult = DbMapper.actionMoveRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedActionMove 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    @ApiOperation("编辑移动事件内容")
    public ActionMove editActionMove(@RequestBody ActionMove updatedActionMove, @PathVariable Long id) {
        updatedActionMove.setId(id);
        return DbMapper.actionMoveRepository.save(updatedActionMove);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @ApiOperation("删除移动事件内容")
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteActionMove(@Valid @PathVariable Long id){
        DbMapper.actionMoveRepository.deleteById(id);
    }

}
