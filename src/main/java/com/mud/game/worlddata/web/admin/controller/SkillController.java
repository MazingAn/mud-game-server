package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Skill;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供Skill的增删改查
 * */
@RestController
@RequestMapping("/Skill")
@Api(tags = "技能管理接口")
public class SkillController {
    /**
     * 增加Skill
     *
     * @param newSkill 表单提交的Skill
     *
     * @return 保存后的Skill信息
     * */
    @PostMapping("/add")
    public Skill addSkill(@Valid  @RequestBody Skill newSkill) {
        return DbMapper.skillRepository.save(newSkill);
    }

    /**
     * 获取所有被动Skill
     * */
    @GetMapping("/{school}")
    public Iterable<Skill> allPassiveSkills(@PathVariable String school){
        if(DbMapper.schoolRepository.existsByDataKey(school)){
            return DbMapper.skillRepository.findSkillsByPassiveAndSchool(true, school);
        }else{
            return DbMapper.skillRepository.findSkillsByPassiveAndSchool(true, "");
        }
    }

    @GetMapping("/basicSkill")
    public Iterable<Skill> allBasicSkills(){
        return DbMapper.skillRepository.findSkillsByPassiveAndCategoryType(true, "SCT_JIBEN");
    }

    @GetMapping("/uniqueSkill")
    public Iterable<Skill> allUniqueSkills(){
        return DbMapper.skillRepository.findSkillsByPassiveAndCategoryType(false, "SCT_JUEZHAO");
    }

    /**
     * 查询Skill
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<Skill> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<Skill> pageResult = DbMapper.skillRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedSkill 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public Skill editSkill(@RequestBody Skill updatedSkill, @PathVariable Long id) {
        updatedSkill.setId(id);
        return DbMapper.skillRepository.save(updatedSkill);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteSkill(@Valid @PathVariable Long id){
        DbMapper.skillRepository.deleteById(id);
    }

}
