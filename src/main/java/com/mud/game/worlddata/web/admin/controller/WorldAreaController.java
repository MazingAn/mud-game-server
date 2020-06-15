package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.WorldArea;
import com.mud.game.worlddata.db.models.WorldArea;
import org.aspectj.weaver.World;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;


/**
 * 游戏区域Controller
 * 提供WorldArea的增删改查
 * */
@RestController
@RequestMapping("/WorldArea")
public class WorldAreaController {
    /**
     * 增加WorldArea
     *
     * @param newWorldArea 表单提交的WorldArea
     * */
    @PostMapping("/add")
    public WorldArea addWorldArea(@Valid @RequestBody WorldArea newWorldArea) {
        return DbMapper.worldAreaRepository.save(newWorldArea);
    }

    /**
     * 查询WorldArea
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<WorldArea> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<WorldArea> pageResult = DbMapper.worldAreaRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 获取所有worldArea
     * */
    @GetMapping("/all")
    public Iterable<WorldArea> all(){
        return DbMapper.worldAreaRepository.findAll();
    }

    /**
     * 修改游戏设置
     *
     * @param updatedWorldArea 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public WorldArea editWorldArea(@Valid @RequestBody WorldArea updatedWorldArea, @PathVariable Long id) {
        updatedWorldArea.setId(id);
        return DbMapper.worldAreaRepository.save(updatedWorldArea);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteWorldArea(@PathVariable Long id){
        DbMapper.worldAreaRepository.deleteById(id);
    }

}
