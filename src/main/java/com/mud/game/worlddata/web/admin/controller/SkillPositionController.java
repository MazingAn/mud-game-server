package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.SkillPosition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供SkillPosition的增删改查
 * */
@RestController
@RequestMapping("/SkillPosition")
public class SkillPositionController {
    /**
     * 增加SkillPosition
     *
     * @param newSkillPosition 表单提交的SkillPosition
     *
     * @return 保存后的SkillPosition信息
     * */
    @PostMapping("/add")
    public SkillPosition addSkillPosition(@Valid  @RequestBody SkillPosition newSkillPosition) {
        return DbMapper.skillPositionRepository.save(newSkillPosition);
    }

    @GetMapping("all")
    public Iterable<SkillPosition> all(){
        return DbMapper.skillPositionRepository.findAll();
    }

    /**
     * 查询SkillPosition
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<SkillPosition> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<SkillPosition> pageResult = DbMapper.skillPositionRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedSkillPosition 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public SkillPosition editSkillPosition(@RequestBody SkillPosition updatedSkillPosition, @PathVariable Long id) {
        updatedSkillPosition.setId(id);
        return DbMapper.skillPositionRepository.save(updatedSkillPosition);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteSkillPosition(@Valid @PathVariable Long id){
        DbMapper.skillPositionRepository.deleteById(id);
    }

}
