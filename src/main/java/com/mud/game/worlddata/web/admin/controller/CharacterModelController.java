package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.CharacterModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供CharacterModel的增删改查
 * */
@RestController
@RequestMapping("/CharacterModel")
public class CharacterModelController {
    /**
     * 增加CharacterModel
     *
     * @param newCharacterModel 表单提交的CharacterModel
     *
     * @return 保存后的CharacterModel信息
     * */
    @PostMapping("/add")
    public CharacterModel addCharacterModel(@Valid  @RequestBody CharacterModel newCharacterModel) {
        return DbMapper.characterModelRepository.save(newCharacterModel);
    }

    /**
     * 查询CharacterModel
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<CharacterModel> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<CharacterModel> pageResult = DbMapper.characterModelRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedCharacterModel 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public CharacterModel editCharacterModel(@RequestBody CharacterModel updatedCharacterModel, @PathVariable Long id) {
        updatedCharacterModel.setId(id);
        return DbMapper.characterModelRepository.save(updatedCharacterModel);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteCharacterModel(@Valid @PathVariable Long id){
        DbMapper.characterModelRepository.deleteById(id);
    }

}
