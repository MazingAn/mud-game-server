package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.handler.QuestStatusHandler;
import com.mud.game.structs.QuestStatus;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Quest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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

    @GetMapping("/all")
    public Iterable<Quest> all(){
        return DbMapper.questRepository.findAll();
    }

    /**
     * 获取所有任务分类
     * */
    @GetMapping("/questType")
    public List<String> findAllQuestType(){
        List<String> questTypes = new ArrayList<>();
        questTypes.add("主线任务");
        questTypes.add("支线任务");
        questTypes.add("师门任务");
        questTypes.add("福缘任务");
        return questTypes;
    }

    /**
     * 获取所有任务状态
     * */
    @GetMapping("/questStatus")
    public List<Map<String, String>> findAllQuestStatus(){
        List<Map<String, String>> questStatus = new ArrayList<>();
        Map<String, String> canProvide = new HashedMap<>();
        canProvide.put(QuestStatusHandler.CAN_PROVIDE, "可以获取");
        Map<String, String> notAccomplished = new HashedMap<>();
        notAccomplished.put(QuestStatusHandler.NOT_ACCOMPLISHED, "没有完成");
        Map<String, String> accomplished = new HashedMap<>();
        accomplished.put(QuestStatusHandler.CAN_PROVIDE, "已经完成可以提交");
        questStatus.add(canProvide);
        questStatus.add(notAccomplished);
        questStatus.add(accomplished);
        return questStatus;
    }

    /**
     *  获取所有任务目标类型
     * */
    @GetMapping("/objectiveType")
    @ApiOperation("获取所有任务目标类型")
    public List<Map<String, String>> findAllObjectiveType(){
        List<Map<String, String>> objectiveTypes = new ArrayList<>();
        Map<String, String> objectiveObject = new HashedMap<>();
        objectiveObject.put("OBJECTIVE_OBJECT", "获取物品");

        Map<String, String> objectiveKill = new HashedMap<>();
        objectiveKill.put("OBJECTIVE_KILL", "杀死某人");

        Map<String, String> objectiveArrive = new HashedMap<>();
        objectiveArrive.put("OBJECTIVE_ARRIVE", "到达某个地方");

        Map<String, String> objectiveSentence = new HashedMap<>();
        objectiveSentence.put("OBJECTIVE_SENTENCE", "完成某个对话的句子");
        objectiveTypes.add(objectiveArrive);
        objectiveTypes.add(objectiveSentence);
        objectiveTypes.add(objectiveObject);
        objectiveTypes.add(objectiveKill);
        return objectiveTypes;
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
