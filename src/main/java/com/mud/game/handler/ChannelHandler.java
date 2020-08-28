package com.mud.game.handler;

import com.mud.game.object.manager.GameChannelManager;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.School;

public class ChannelHandler {

    public static void initChannels() {
        // 创建公共频带
        GameChannelManager.create("Public", "公共", "all");
        // 创建谣言频道
        GameChannelManager.create("rumor", "谣言", "all");
        // 创建门派频道
        for (School school : DbMapper.schoolRepository.findAll()) {
            GameChannelManager.create(school.getDataKey() + "_channel", school.getName(), school.getDataKey());
        }
    }

}
