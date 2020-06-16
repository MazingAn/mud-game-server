package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.QuestRewardList;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供QuestRewardList的增删改查
 * */
@RestController
@RequestMapping("/QuestRewardList")
@Api(tags = "任务奖励管理接口")
public class QuestRewardListController {
    /**
     * 增加QuestRewardList
     *
     * @param newQuestRewardList 表单提交的QuestRewardList
     *
     * @return 保存后的QuestRewardList信息
     * */
    @PostMapping("/add")
    public QuestRewardList addQuestRewardList(@Valid  @RequestBody QuestRewardList newQuestRewardList) {
        return DbMapper.questRewardListRepository.save(newQuestRewardList);
    }

    /**
     * 查询QuestRewardList
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<QuestRewardList> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<QuestRewardList> pageResult = DbMapper.questRewardListRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedQuestRewardList 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public QuestRewardList editQuestRewardList(@RequestBody QuestRewardList updatedQuestRewardList, @PathVariable Long id) {
        updatedQuestRewardList.setId(id);
        return DbMapper.questRewardListRepository.save(updatedQuestRewardList);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteQuestRewardList(@Valid @PathVariable Long id){
        DbMapper.questRewardListRepository.deleteById(id);
    }

}
