package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Family;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供Family的增删改查
 * */
@RestController
@RequestMapping("/Family")
@Api(tags = "天赋管理接口")
public class FamilyController {
    /**
     * 增加Family
     *
     * @param newFamily 表单提交的Family
     *
     * @return 保存后的Family信息
     * */
    @PostMapping("/add")
    public Family addFamily(@Valid  @RequestBody Family newFamily) {
        return DbMapper.familyRepository.save(newFamily);
    }

    /**
     * 查询Family
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<Family> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<Family> pageResult = DbMapper.familyRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedFamily 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public Family editFamily(@RequestBody Family updatedFamily, @PathVariable Long id) {
        updatedFamily.setId(id);
        return DbMapper.familyRepository.save(updatedFamily);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteFamily(@Valid @PathVariable Long id){
        DbMapper.familyRepository.deleteById(id);
    }

}
