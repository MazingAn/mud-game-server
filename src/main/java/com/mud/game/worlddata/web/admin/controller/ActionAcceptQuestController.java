package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.commands.character.SellObjects;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.ActionAcceptQuest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供ActionAcceptQuest的增删改查
 */
@RestController
@RequestMapping("/ActionAcceptQuest")
@Api(tags = "D:事件管理接口")
public class ActionAcceptQuestController {
    /**
     * 增加ActionAcceptQuest
     *
     * @param newActionAcceptQuest 表单提交的ActionAcceptQuest
     * @return 保存后的ActionAcceptQuest信息
     */
    @PostMapping("/add")
    @ApiOperation("增加接受事件内容")
    public ActionAcceptQuest addActionAcceptQuest(@Valid @RequestBody ActionAcceptQuest newActionAcceptQuest) {
        return DbMapper.actionAcceptQuestRepository.save(newActionAcceptQuest);
    }

    /**
     * 查询ActionAcceptQuest
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     */
    @GetMapping("")
    @ApiOperation("分页显示接受任务事件")
    public Page<ActionAcceptQuest> query(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "20") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<ActionAcceptQuest> pageResult = DbMapper.actionAcceptQuestRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedActionAcceptQuest 更新的游戏设置
     * @param id                       要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    @ApiOperation("编辑接受任务事件内容")
    public ActionAcceptQuest editActionAcceptQuest(@RequestBody ActionAcceptQuest updatedActionAcceptQuest, @PathVariable Long id) {
        updatedActionAcceptQuest.setId(id);
        return DbMapper.actionAcceptQuestRepository.save(updatedActionAcceptQuest);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     */
    @ApiOperation("删除接受任务事件内容")
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteActionAcceptQuest(@Valid @PathVariable Long id) {
        DbMapper.actionAcceptQuestRepository.deleteById(id);
    }


}
