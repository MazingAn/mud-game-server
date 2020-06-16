package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.School;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供School的增删改查
 * */
@RestController
@RequestMapping("/School")
@Api(tags = "门派管理接口")
public class SchoolController {
    /**
     * 增加School
     *
     * @param newSchool 表单提交的School
     *
     * @return 保存后的School信息
     * */
    @PostMapping("/add")
    public School addSchool(@Valid  @RequestBody School newSchool) {
        return DbMapper.schoolRepository.save(newSchool);
    }

    @GetMapping("/all")
    public Iterable<School> all(){
        return DbMapper.schoolRepository.findAll();
    }

    /**
     * 查询School
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<School> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<School> pageResult = DbMapper.schoolRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedSchool 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public School editSchool(@RequestBody School updatedSchool, @PathVariable Long id) {
        updatedSchool.setId(id);
        return DbMapper.schoolRepository.save(updatedSchool);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteSchool(@Valid @PathVariable Long id){
        DbMapper.schoolRepository.deleteById(id);
    }

}
