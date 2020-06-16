package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.WorldExit;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供WorldExit的增删改查
 * */
@RestController
@RequestMapping("/WorldExit")
@Api(tags = "世界出口管理接口")
public class WorldExitController {
    /**
     * 增加WorldExit
     *
     * @param newWorldExit 表单提交的WorldExit
     *
     * @return 保存后的WorldExit信息
     * */
    @PostMapping("/add")
    public WorldExit addWorldExit(@Valid  @RequestBody WorldExit newWorldExit) {
        return DbMapper.worldExitRepository.save(newWorldExit);
    }

    /**
     * 查询WorldExit
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<WorldExit> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<WorldExit> pageResult = DbMapper.worldExitRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedWorldExit 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public WorldExit editWorldExit(@Valid @RequestBody WorldExit updatedWorldExit, @PathVariable Long id) {
        updatedWorldExit.setId(id);
        return DbMapper.worldExitRepository.save(updatedWorldExit);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteWorldExit(@PathVariable Long id){
        DbMapper.worldExitRepository.deleteById(id);
    }

}
