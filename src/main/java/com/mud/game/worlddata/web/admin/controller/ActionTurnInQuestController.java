package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.commands.character.CheckAdvanced;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.ActionTurnInQuest;
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
 * 提供ActionTurnInQuest的增删改查
 */
@RestController
@RequestMapping("/ActionTurnInQuest")
@Api(tags = "D:事件管理接口")
public class ActionTurnInQuestController {
    /**
     * 增加ActionTurnInQuest
     *
     * @param newActionTurnInQuest 表单提交的ActionTurnInQuest
     * @return 保存后的ActionTurnInQuest信息
     */
    @PostMapping("/add")
    @ApiOperation("增加接受事件内容")
    public ActionTurnInQuest addActionTurnInQuest(@Valid @RequestBody ActionTurnInQuest newActionTurnInQuest) {
        return DbMapper.actionTurnInQuestRepository.save(newActionTurnInQuest);
    }

    @PostMapping("/xxxxxxxxxx")
    @ApiOperation("xxxxxxxxx")
    public void xxxxxxxxxxxxxx(String id) throws JSONException {
        //5f2b7dbaa6897a3a9a060712
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("args", id);
        new CheckAdvanced(null, null, jsonObject, null);
    }


    /**
     * 查询ActionTurnInQuest
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     */
    @GetMapping("")
    @ApiOperation("分页显示学习技能事件")
    public Page<ActionTurnInQuest> query(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "20") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<ActionTurnInQuest> pageResult = DbMapper.actionTurnInQuestRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedActionTurnInQuest 更新的游戏设置
     * @param id                       要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    @ApiOperation("编辑学习技能事件内容")
    public ActionTurnInQuest editActionTurnInQuest(@RequestBody ActionTurnInQuest updatedActionTurnInQuest, @PathVariable Long id) {
        updatedActionTurnInQuest.setId(id);
        return DbMapper.actionTurnInQuestRepository.save(updatedActionTurnInQuest);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     */
    @ApiOperation("删除学习技能事件内容")
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteActionTurnInQuest(@Valid @PathVariable Long id) {
        DbMapper.actionTurnInQuestRepository.deleteById(id);
    }

}
