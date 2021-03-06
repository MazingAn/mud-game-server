package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.SkillBook;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供SkillBook的增删改查
 * */
@RestController
@RequestMapping("/SkillBook")
@Api(tags = "技能书管理接口")
public class SkillBookController {
    /**
     * 增加SkillBook
     *
     * @param newSkillBook 表单提交的SkillBook
     *
     * @return 保存后的SkillBook信息
     * */
    @PostMapping("/add")
    public SkillBook addSkillBook(@Valid  @RequestBody SkillBook newSkillBook) {
        return DbMapper.skillBookRepository.save(newSkillBook);
    }

    /**
     * 查询SkillBook
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<SkillBook> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<SkillBook> pageResult = DbMapper.skillBookRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedSkillBook 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public SkillBook editSkillBook(@RequestBody SkillBook updatedSkillBook, @PathVariable Long id) {
        updatedSkillBook.setId(id);
        return DbMapper.skillBookRepository.save(updatedSkillBook);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteSkillBook(@Valid @PathVariable Long id){
        DbMapper.skillBookRepository.deleteById(id);
    }

}
