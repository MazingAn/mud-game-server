package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.DialogueSentence;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供DialogueSentence的增删改查
 * */
@RestController
@RequestMapping("/DialogueSentence")
@Api(tags = "对话管理接口")
public class DialogueSentenceController {
    /**
     * 增加DialogueSentence
     *
     * @param newDialogueSentence 表单提交的DialogueSentence
     *
     * @return 保存后的DialogueSentence信息
     * */
    @PostMapping("/add")
    @ApiOperation("增加句子")
    public DialogueSentence addDialogueSentence(@Valid  @RequestBody DialogueSentence newDialogueSentence) {
        return DbMapper.dialogueSentenceRepository.save(newDialogueSentence);
    }

    /**
     * 查询DialogueSentence
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    @ApiOperation("分页查看句子")
    public Page<DialogueSentence> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<DialogueSentence> pageResult = DbMapper.dialogueSentenceRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedDialogueSentence 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    @ApiOperation("编辑句子")
    @ApiImplicitParam(name = "id", value = "句子id")
    public DialogueSentence editDialogueSentence(@RequestBody DialogueSentence updatedDialogueSentence, @PathVariable Long id) {
        updatedDialogueSentence.setId(id);
        return DbMapper.dialogueSentenceRepository.save(updatedDialogueSentence);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    @ApiOperation("删除句子")
    @ApiImplicitParam(name = "id", value = "句子id")
    public void deleteDialogueSentence(@Valid @PathVariable Long id){
        DbMapper.dialogueSentenceRepository.deleteById(id);
    }

}
