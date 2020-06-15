package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.NormalObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供NormalObject的增删改查
 * */
@RestController
@RequestMapping("/NormalObject")
public class NormalObjectController {
    /**
     * 增加NormalObject
     *
     * @param newNormalObject 表单提交的NormalObject
     *
     * @return 保存后的NormalObject信息
     * */
    @PostMapping("/add")
    public NormalObject addNormalObject(@Valid  @RequestBody NormalObject newNormalObject) {
        return DbMapper.normalObjectRepository.save(newNormalObject);
    }

    /**
     * 查询NormalObject
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<NormalObject> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<NormalObject> pageResult = DbMapper.normalObjectRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedNormalObject 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public NormalObject editNormalObject(@RequestBody NormalObject updatedNormalObject, @PathVariable Long id) {
        updatedNormalObject.setId(id);
        return DbMapper.normalObjectRepository.save(updatedNormalObject);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteNormalObject(@Valid @PathVariable Long id){
        DbMapper.normalObjectRepository.deleteById(id);
    }

}
