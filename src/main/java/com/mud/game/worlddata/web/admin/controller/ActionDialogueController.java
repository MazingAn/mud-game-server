package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.ActionDialogue;
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
 * 提供ActionDialogue的增删改查
 * */
@RestController
@RequestMapping("/ActionDialogue")
@Api(tags = "D:事件管理接口")
public class ActionDialogueController {
    /**
     * 增加ActionDialogue
     *
     * @param newActionDialogue 表单提交的ActionDialogue
     *
     * @return 保存后的ActionDialogue信息
     * */
    @PostMapping("/add")
    @ApiOperation("增加接受事件内容")
    public ActionDialogue addActionDialogue(@Valid  @RequestBody ActionDialogue newActionDialogue) {
        return DbMapper.actionDialogueRepository.save(newActionDialogue);
    }

    /**
     * 查询ActionDialogue
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    @ApiOperation("分页显示触发对话事件")
    public Page<ActionDialogue> query(@RequestParam(defaultValue="0") int page,
                                         @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<ActionDialogue> pageResult = DbMapper.actionDialogueRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedActionDialogue 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    @ApiOperation("编辑触发对话事件内容")
    public ActionDialogue editActionDialogue(@RequestBody ActionDialogue updatedActionDialogue, @PathVariable Long id) {
        updatedActionDialogue.setId(id);
        return DbMapper.actionDialogueRepository.save(updatedActionDialogue);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @ApiOperation("删除触发对话事件内容")
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteActionDialogue(@Valid @PathVariable Long id){
        DbMapper.actionDialogueRepository.deleteById(id);
    }

}
