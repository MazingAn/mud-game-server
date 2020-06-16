package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Quest;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供Quest的增删改查
 * */
@RestController
@RequestMapping("/Quest")
@Api(tags = "任务管理接口")
public class QuestController {
    /**
     * 增加Quest
     *
     * @param newQuest 表单提交的Quest
     *
     * @return 保存后的Quest信息
     * */
    @PostMapping("/add")
    public Quest addQuest(@Valid  @RequestBody Quest newQuest) {
        return DbMapper.questRepository.save(newQuest);
    }

    /**
     * 查询Quest
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<Quest> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<Quest> pageResult = DbMapper.questRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedQuest 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public Quest editQuest(@RequestBody Quest updatedQuest, @PathVariable Long id) {
        updatedQuest.setId(id);
        return DbMapper.questRepository.save(updatedQuest);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteQuest(@Valid @PathVariable Long id){
        DbMapper.questRepository.deleteById(id);
    }

}
