package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.ActionLearnSkill;
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
 * 提供ActionLearnSkill的增删改查
 * */
@RestController
@RequestMapping("/ActionLearnSkill")
@Api(tags = "D:事件管理接口")
public class ActionLearnSkillController {
    /**
     * 增加ActionLearnSkill
     *
     * @param newActionLearnSkill 表单提交的ActionLearnSkill
     *
     * @return 保存后的ActionLearnSkill信息
     * */
    @PostMapping("/add")
    @ApiOperation("增加接受事件内容")
    public ActionLearnSkill addActionLearnSkill(@Valid  @RequestBody ActionLearnSkill newActionLearnSkill) {
        return DbMapper.actionLearnSkillRepository.save(newActionLearnSkill);
    }

    /**
     * 查询ActionLearnSkill
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    @ApiOperation("分页显示学习技能事件")
    public Page<ActionLearnSkill> query(@RequestParam(defaultValue="0") int page,
                                         @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<ActionLearnSkill> pageResult = DbMapper.actionLearnSkillRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedActionLearnSkill 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    @ApiOperation("编辑学习技能事件内容")
    public ActionLearnSkill editActionLearnSkill(@RequestBody ActionLearnSkill updatedActionLearnSkill, @PathVariable Long id) {
        updatedActionLearnSkill.setId(id);
        return DbMapper.actionLearnSkillRepository.save(updatedActionLearnSkill);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @ApiOperation("删除学习技能事件内容")
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteActionLearnSkill(@Valid @PathVariable Long id){
        DbMapper.actionLearnSkillRepository.deleteById(id);
    }

}
