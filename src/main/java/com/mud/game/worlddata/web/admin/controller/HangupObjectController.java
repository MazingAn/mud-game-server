package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.HangupObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供HangupObject的增删改查
 * */
@RestController
@RequestMapping("/HangupObject")
public class HangupObjectController {
    /**
     * 增加HangupObject
     *
     * @param newHangupObject 表单提交的HangupObject
     *
     * @return 保存后的HangupObject信息
     * */
    @PostMapping("/add")
    public HangupObject addHangupObject(@Valid  @RequestBody HangupObject newHangupObject) {
        return DbMapper.hangupObjectRepository.save(newHangupObject);
    }

    /**
     * 查询HangupObject
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<HangupObject> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<HangupObject> pageResult = DbMapper.hangupObjectRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedHangupObject 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public HangupObject editHangupObject(@RequestBody HangupObject updatedHangupObject, @PathVariable Long id) {
        updatedHangupObject.setId(id);
        return DbMapper.hangupObjectRepository.save(updatedHangupObject);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteHangupObject(@Valid @PathVariable Long id){
        DbMapper.hangupObjectRepository.deleteById(id);
    }

}
