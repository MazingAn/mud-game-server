package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.QuestDependency;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供QuestDependency的增删改查
 * */
@RestController
@RequestMapping("/QuestDependency")
@Api(tags = "任务依赖关系管理接口")
public class QuestDependencyController {
    /**
     * 增加QuestDependency
     *
     * @param newQuestDependency 表单提交的QuestDependency
     *
     * @return 保存后的QuestDependency信息
     * */
    @PostMapping("/add")
    public QuestDependency addQuestDependency(@Valid  @RequestBody QuestDependency newQuestDependency) {
        return DbMapper.questDependencyRepository.save(newQuestDependency);
    }

    /**
     * 查询QuestDependency
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<QuestDependency> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<QuestDependency> pageResult = DbMapper.questDependencyRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedQuestDependency 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public QuestDependency editQuestDependency(@RequestBody QuestDependency updatedQuestDependency, @PathVariable Long id) {
        updatedQuestDependency.setId(id);
        return DbMapper.questDependencyRepository.save(updatedQuestDependency);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteQuestDependency(@Valid @PathVariable Long id){
        DbMapper.questDependencyRepository.deleteById(id);
    }

}
