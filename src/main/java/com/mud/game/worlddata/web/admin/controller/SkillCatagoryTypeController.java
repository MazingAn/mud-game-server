package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.School;
import com.mud.game.worlddata.db.models.SkillCategoryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供SkillCategoryType的增删改查
 * */
@RestController
@RequestMapping("/SkillCategoryType")
public class SkillCatagoryTypeController {
    /**
     * 增加SkillCategoryType
     *
     * @param newSkillCategoryType 表单提交的SkillCategoryType
     *
     * @return 保存后的SkillCategoryType信息
     * */
    @PostMapping("/add")
    public SkillCategoryType addSkillCategoryType(@Valid  @RequestBody SkillCategoryType newSkillCategoryType) {
        return DbMapper.skillCategoryTypeRepository.save(newSkillCategoryType);
    }

    @GetMapping("/all")
    public Iterable<SkillCategoryType> all(){
        return DbMapper.skillCategoryTypeRepository.findAll();
    }


    /**
     * 查询SkillCategoryType
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<SkillCategoryType> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<SkillCategoryType> pageResult = DbMapper.skillCategoryTypeRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedSkillCategoryType 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public SkillCategoryType editSkillCategoryType(@RequestBody SkillCategoryType updatedSkillCategoryType, @PathVariable Long id) {
        updatedSkillCategoryType.setId(id);
        return DbMapper.skillCategoryTypeRepository.save(updatedSkillCategoryType);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteSkillCategoryType(@Valid @PathVariable Long id){
        DbMapper.skillCategoryTypeRepository.deleteById(id);
    }

}
