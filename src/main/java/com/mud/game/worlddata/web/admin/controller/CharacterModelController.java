package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.CharacterModel;
import io.swagger.annotations.*;
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
@Api(tags = "角色模板维护接口")
public class CharacterModelController {
    /**
     * 增加CharacterModel
     *
     * @param newCharacterModel 表单提交的CharacterModel
     *
     * @return 保存后的CharacterModel信息
     * */
    @PostMapping("/add")
    @ApiOperation("增加一个角色模板")
    public CharacterModel addCharacterModel(@Valid  @RequestBody CharacterModel newCharacterModel) {
        return DbMapper.characterModelRepository.save(newCharacterModel);
    }

    @GetMapping("/all")
    @ApiOperation("获取所有角色模板")
    public Iterable<CharacterModel> all(){
        return DbMapper.characterModelRepository.findAll();
    }

    /**
     * 查询CharacterModel
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    @ApiOperation("分页查看角色模板列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", defaultValue = "0", required = false, value = "页数"),
            @ApiImplicitParam(name = "size", defaultValue = "20", required = false, value = "每页数量")
    })
    @ApiImplicitParam(name = "size", defaultValue = "20", required = false, value = "页数")
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
    @ApiOperation("修改一个角色模板")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "角色模板id"),
    })
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
    @ApiOperation("删除一个角色模板")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "角色模板id"),
    })
    public void deleteCharacterModel(@Valid @PathVariable Long id){
        DbMapper.characterModelRepository.deleteById(id);
    }

}
