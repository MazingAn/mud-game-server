package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.SkillBookBind;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供SkillBookBind的增删改查
 * */
@RestController
@RequestMapping("/SkillBookBind")
@Api(tags = "技能书绑定管理接口")
public class SkillBookBindController {
    /**
     * 增加SkillBookBind
     *
     * @param newSkillBookBind 表单提交的SkillBookBind
     *
     * @return 保存后的SkillBookBind信息
     * */
    @PostMapping("/add")
    public SkillBookBind addSkillBookBind(@Valid  @RequestBody SkillBookBind newSkillBookBind) {
        return DbMapper.skillBookBindRepository.save(newSkillBookBind);
    }

    /**
     * 查询SkillBookBind
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<SkillBookBind> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<SkillBookBind> pageResult = DbMapper.skillBookBindRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedSkillBookBind 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public SkillBookBind editSkillBookBind(@RequestBody SkillBookBind updatedSkillBookBind, @PathVariable Long id) {
        updatedSkillBookBind.setId(id);
        return DbMapper.skillBookBindRepository.save(updatedSkillBookBind);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteSkillBookBind(@Valid @PathVariable Long id){
        DbMapper.skillBookBindRepository.deleteById(id);
    }

}
