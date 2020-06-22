package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.ActionChangeStep;
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
 * 提供ActionChangeStep的增删改查
 * */
@RestController
@RequestMapping("/ActionChangeStep")
@Api(tags = "D:事件管理接口")
public class ActionChangeStepController {
    /**
     * 增加ActionChangeStep
     *
     * @param newActionChangeStep 表单提交的ActionChangeStep
     *
     * @return 保存后的ActionChangeStep信息
     * */
    @PostMapping("/add")
    @ApiOperation("增加接受事件内容")
    public ActionChangeStep addActionChangeStep(@Valid  @RequestBody ActionChangeStep newActionChangeStep) {
        return DbMapper.actionChangeStepRepository.save(newActionChangeStep);
    }

    /**
     * 查询ActionChangeStep
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    @ApiOperation("分页显示修改高度事件")
    public Page<ActionChangeStep> query(@RequestParam(defaultValue="0") int page,
                                         @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<ActionChangeStep> pageResult = DbMapper.actionChangeStepRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedActionChangeStep 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    @ApiOperation("编辑修改高度事件内容")
    public ActionChangeStep editActionChangeStep(@RequestBody ActionChangeStep updatedActionChangeStep, @PathVariable Long id) {
        updatedActionChangeStep.setId(id);
        return DbMapper.actionChangeStepRepository.save(updatedActionChangeStep);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @ApiOperation("删除修改高度事件内容")
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteActionChangeStep(@Valid @PathVariable Long id){
        DbMapper.actionChangeStepRepository.deleteById(id);
    }

}
