package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.WorldRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供WorldRoom的增删改查
 * */
@RestController
@RequestMapping("/WorldRoom")
public class WorldRoomController {
    /**
     * 增加WorldRoom
     *
     * @param newWorldRoom 表单提交的WorldRoom
     *
     * @return 保存后的WorldRoom信息
     * */
    @PostMapping("/add")
    public WorldRoom addWorldRoom(@Valid  @RequestBody WorldRoom newWorldRoom) {
        return DbMapper.worldRoomRepository.save(newWorldRoom);
    }

    /**
     * 查询WorldRoom
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<WorldRoom> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<WorldRoom> pageResult = DbMapper.worldRoomRepository.findAll(paging);
        return pageResult;
    }

    @GetMapping("/{area}")
    public Iterable<WorldRoom> queryWorldRoomByArea(@PathVariable String area){
        return DbMapper.worldRoomRepository.findWorldRoomsByLocation(area);
    }

    /**
     * 修改游戏设置
     *
     * @param updatedWorldRoom 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public WorldRoom editWorldRoom(@RequestBody WorldRoom updatedWorldRoom, @PathVariable Long id) {
        updatedWorldRoom.setId(id);
        return DbMapper.worldRoomRepository.save(updatedWorldRoom);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteWorldRoom(@Valid @PathVariable Long id){
        DbMapper.worldRoomRepository.deleteById(id);
    }

}
