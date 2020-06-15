package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Dialogue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供Dialogue的增删改查
 * */
@RestController
@RequestMapping("/Dialogue")
public class DialogueController {
    /**
     * 增加Dialogue
     *
     * @param newDialogue 表单提交的Dialogue
     *
     * @return 保存后的Dialogue信息
     * */
    @PostMapping("/add")
    public Dialogue addDialogue(@Valid  @RequestBody Dialogue newDialogue) {
        return DbMapper.dialogueRepository.save(newDialogue);
    }

    /**
     * 查询Dialogue
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<Dialogue> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<Dialogue> pageResult = DbMapper.dialogueRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedDialogue 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public Dialogue editDialogue(@RequestBody Dialogue updatedDialogue, @PathVariable Long id) {
        updatedDialogue.setId(id);
        return DbMapper.dialogueRepository.save(updatedDialogue);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteDialogue(@Valid @PathVariable Long id){
        DbMapper.dialogueRepository.deleteById(id);
    }

}
