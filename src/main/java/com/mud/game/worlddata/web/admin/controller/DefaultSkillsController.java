package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.DefaultSkills;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供DefaultSkills的增删改查
 * */
@RestController
@RequestMapping("/DefaultSkills")
public class DefaultSkillsController {
    /**
     * 增加DefaultSkills
     *
     * @param newDefaultSkills 表单提交的DefaultSkills
     *
     * @return 保存后的DefaultSkills信息
     * */
    @PostMapping("/add")
    public DefaultSkills addDefaultSkills(@Valid  @RequestBody DefaultSkills newDefaultSkills) {
        return DbMapper.defaultSkillsRepository.save(newDefaultSkills);
    }

    /**
     * 查询DefaultSkills
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<DefaultSkills> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<DefaultSkills> pageResult = DbMapper.defaultSkillsRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedDefaultSkills 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public DefaultSkills editDefaultSkills(@RequestBody DefaultSkills updatedDefaultSkills, @PathVariable Long id) {
        updatedDefaultSkills.setId(id);
        return DbMapper.defaultSkillsRepository.save(updatedDefaultSkills);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteDefaultSkills(@Valid @PathVariable Long id){
        DbMapper.defaultSkillsRepository.deleteById(id);
    }

}
