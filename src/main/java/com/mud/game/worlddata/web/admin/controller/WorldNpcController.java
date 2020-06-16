package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.WorldNpc;
import com.mud.game.worlddata.db.models.WorldRoom;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供WorldNpc的增删改查
 * */
@RestController
@RequestMapping("/WorldNpc")
@Api(tags = "世界NPC管理接口")
public class WorldNpcController {
    /**
     * 增加WorldNpc
     *
     * @param newWorldNpc 表单提交的WorldNpc
     *
     * @return 保存后的WorldNpc信息
     * */
    @PostMapping("/add")
    public WorldNpc addWorldNpc(@Valid @RequestBody WorldNpc newWorldNpc) {
        return DbMapper.worldNpcRepository.save(newWorldNpc);
    }

    /**
     * 查询WorldNpc
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<WorldNpc> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<WorldNpc> pageResult = DbMapper.worldNpcRepository.findAll(paging);
        return pageResult;
    }

    @GetMapping("/{room}")
    public Iterable<WorldNpc> queryWorldRoomByArea(@PathVariable String room){
        return DbMapper.worldNpcRepository.findWorldNpcsByLocation(room);
    }

    /**
     * 修改游戏设置
     *
     * @param updatedWorldNpc 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public WorldNpc editWorldNpc(@Valid @RequestBody WorldNpc updatedWorldNpc, @PathVariable Long id) {
        updatedWorldNpc.setId(id);
        return DbMapper.worldNpcRepository.save(updatedWorldNpc);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteWorldNpc(@PathVariable Long id){
         DbMapper.worldNpcRepository.deleteById(id);
    }

}
