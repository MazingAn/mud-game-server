package com.mud.game.messages;

import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.structs.TitlesInfo;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.PlayerTitle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 称号列表返回参数
 */
public class TitlesMessage {
    private List<TitlesInfo> titles;

    public TitlesMessage(PlayerCharacter character) {
        character.setTitleKeySet(new HashSet<String>() {{
            add("QUANZHEN_THREE_BAGS_DISCIPLE");
        }});
        List<PlayerTitle> playerTitleList = DbMapper.playerTitleRepository.findPlayerTitleByDataKeyIn(character.getTitleKeySet());
        titles = new ArrayList<>();
        for (int i = 0; i < playerTitleList.size(); i++) {
            titles.add(new TitlesInfo(playerTitleList.get(i), character));
        }
    }

    public List<TitlesInfo> getTitles() {
        return titles;
    }

    public void setTitles(List<TitlesInfo> titles) {
        this.titles = titles;
    }
}
