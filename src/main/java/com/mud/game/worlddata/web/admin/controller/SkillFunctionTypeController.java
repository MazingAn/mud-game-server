package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.SkillFunctionType;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供SkillFunctionType的增删改查
 * */
@RestController
@RequestMapping("/SkillFunctionType")
@Api(tags = "技能功能管理接口")
public class SkillFunctionTypeController {
    /**
     * 增加SkillFunctionType
     *
     * @param newSkillFunctionType 表单提交的SkillFunctionType
     *
     * @return 保存后的SkillFunctionType信息
     * */
    @PostMapping("/add")
    public SkillFunctionType addSkillFunctionType(@Valid  @RequestBody SkillFunctionType newSkillFunctionType) {
        return DbMapper.skillFunctionTypeRepository.save(newSkillFunctionType);
    }

    @GetMapping("all")
    public Iterable<SkillFunctionType> all(){
        return DbMapper.skillFunctionTypeRepository.findAll();
    }

    /**
     * 查询SkillFunctionType
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<SkillFunctionType> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<SkillFunctionType> pageResult = DbMapper.skillFunctionTypeRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedSkillFunctionType 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public SkillFunctionType editSkillFunctionType(@RequestBody SkillFunctionType updatedSkillFunctionType, @PathVariable Long id) {
        updatedSkillFunctionType.setId(id);
        return DbMapper.skillFunctionTypeRepository.save(updatedSkillFunctionType);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteSkillFunctionType(@Valid @PathVariable Long id){
        DbMapper.skillFunctionTypeRepository.deleteById(id);
    }

}
