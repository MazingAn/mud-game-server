package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.NpcDialogue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供NpcDialogue的增删改查
 * */
@RestController
@RequestMapping("/NpcDialogue")
@Api(tags = "NPC对话绑定管理接口")
public class NpcDialogueController {
    /**
     * 增加NpcDialogue
     *
     * @param newNpcDialogue 表单提交的NpcDialogue
     *
     * @return 保存后的NpcDialogue信息
     * */
    @PostMapping("/add")
    @ApiOperation("增加NPC对话绑定")
    public NpcDialogue addNpcDialogue(@Valid  @RequestBody NpcDialogue newNpcDialogue) {
        return DbMapper.npcDialogueRepository.save(newNpcDialogue);
    }

    /**
     * 查询NpcDialogue
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    @ApiOperation("查看NPC对话绑定")
    public Page<NpcDialogue> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<NpcDialogue> pageResult = DbMapper.npcDialogueRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedNpcDialogue 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    @ApiOperation("修改NPC对话绑定")
    public NpcDialogue editNpcDialogue(@RequestBody NpcDialogue updatedNpcDialogue, @PathVariable Long id) {
        updatedNpcDialogue.setId(id);
        return DbMapper.npcDialogueRepository.save(updatedNpcDialogue);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    @ApiOperation("删除NPC对话绑定")
    public void deleteNpcDialogue(@Valid @PathVariable Long id){
        DbMapper.npcDialogueRepository.deleteById(id);
    }

}
