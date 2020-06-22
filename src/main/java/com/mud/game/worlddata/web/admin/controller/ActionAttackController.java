package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.ActionAttack;
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
 * 提供ActionAttack的增删改查
 * */
@RestController
@RequestMapping("/ActionAttack")
@Api(tags = "D:事件管理接口")
public class ActionAttackController {
    /**
     * 增加ActionAttack
     *
     * @param newActionAttack 表单提交的ActionAttack
     *
     * @return 保存后的ActionAttack信息
     * */
    @PostMapping("/add")
    @ApiOperation("增加接受事件内容")
    public ActionAttack addActionAttack(@Valid  @RequestBody ActionAttack newActionAttack) {
        return DbMapper.actionAttackRepository.save(newActionAttack);
    }

    /**
     * 查询ActionAttack
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    @ApiOperation("分页显示攻击事件")
    public Page<ActionAttack> query(@RequestParam(defaultValue="0") int page,
                                         @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<ActionAttack> pageResult = DbMapper.actionAttackRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedActionAttack 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    @ApiOperation("编辑攻击事件内容")
    public ActionAttack editActionAttack(@RequestBody ActionAttack updatedActionAttack, @PathVariable Long id) {
        updatedActionAttack.setId(id);
        return DbMapper.actionAttackRepository.save(updatedActionAttack);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @ApiOperation("删除攻击事件内容")
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteActionAttack(@Valid @PathVariable Long id){
        DbMapper.actionAttackRepository.deleteById(id);
    }

}
