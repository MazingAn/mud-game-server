package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.QuestObjective;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供QuestObjective的增删改查
 * */
@RestController
@RequestMapping("/QuestObjective")
@Api(tags = "任务目标管理接口")
public class QuestObjectiveController {
    /**
     * 增加QuestObjective
     *
     * @param newQuestObjective 表单提交的QuestObjective
     *
     * @return 保存后的QuestObjective信息
     * */
    @PostMapping("/add")
    public QuestObjective addQuestObjective(@Valid  @RequestBody QuestObjective newQuestObjective) {
        return DbMapper.questObjectiveRepository.save(newQuestObjective);
    }

    /**
     * 查询QuestObjective
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<QuestObjective> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<QuestObjective> pageResult = DbMapper.questObjectiveRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedQuestObjective 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public QuestObjective editQuestObjective(@RequestBody QuestObjective updatedQuestObjective, @PathVariable Long id) {
        updatedQuestObjective.setId(id);
        return DbMapper.questObjectiveRepository.save(updatedQuestObjective);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteQuestObjective(@Valid @PathVariable Long id){
        DbMapper.questObjectiveRepository.deleteById(id);
    }

}
