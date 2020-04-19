package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.GameSetting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏设置Controller
 * 提供GameSetting的增删改查
 * */
@RestController
@RequestMapping("/GameSetting")
public class GameSettingController {
    /**
     * 增加gameSetting
     *
     * @param newGameSetting 表单提交的GameSetting
     * @return GameSetting
     * */
    @PostMapping("/add")
    public GameSetting addGameSetting(@Valid  @RequestBody GameSetting newGameSetting) {
        return DbMapper.gameSettingRepository.save(newGameSetting);
    }


    @GetMapping("")
    public Page<GameSetting> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size,
                                   UriComponentsBuilder uriBuilder){
        Pageable paging = PageRequest.of(page, size);
        Page<GameSetting> pageResult = DbMapper.gameSettingRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedGameSetting 更新的游戏设置
     * @param id  要更新的行的id
     * @return GameSetting 更新后信息内容
     */
    @PutMapping("/{id}")
    public GameSetting editGameSetting(@Valid @RequestBody GameSetting updatedGameSetting, @PathVariable Long id) {
        updatedGameSetting.setId(id);
        return DbMapper.gameSettingRepository.save(updatedGameSetting);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteGameSetting(@PathVariable Long id){
        DbMapper.gameSettingRepository.deleteById(id);
    }

}
