package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.QuestDialogueDependency;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供QuestDialogueDependency的增删改查
 * */
@RestController
@RequestMapping("/QuestDialogueDependency")
@Api(tags = "任务对话关系管理接口")
public class QuestDialogueDependencyController {
    /**
     * 增加QuestDialogueDependency
     *
     * @param newQuestDialogueDependency 表单提交的QuestDialogueDependency
     *
     * @return 保存后的QuestDialogueDependency信息
     * */
    @PostMapping("/add")
    public QuestDialogueDependency addQuestDialogueDependency(@Valid  @RequestBody QuestDialogueDependency newQuestDialogueDependency) {
        return DbMapper.questDialogueDependencyRepository.save(newQuestDialogueDependency);
    }

    /**
     * 查询QuestDialogueDependency
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<QuestDialogueDependency> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<QuestDialogueDependency> pageResult = DbMapper.questDialogueDependencyRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedQuestDialogueDependency 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public QuestDialogueDependency editQuestDialogueDependency(@RequestBody QuestDialogueDependency updatedQuestDialogueDependency, @PathVariable Long id) {
        updatedQuestDialogueDependency.setId(id);
        return DbMapper.questDialogueDependencyRepository.save(updatedQuestDialogueDependency);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteQuestDialogueDependency(@Valid @PathVariable Long id){
        DbMapper.questDialogueDependencyRepository.deleteById(id);
    }

}
