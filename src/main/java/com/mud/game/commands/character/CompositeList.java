package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.CompositeListMessage;
import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.typeclass.CompositeMaterialObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.CompositeMaterial;
import com.mud.game.worlddata.db.models.Equipment;
import com.mud.game.worlddata.db.models.SkillBook;
import com.mud.game.worlddata.db.models.supermodel.BaseCommonObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.yeauty.pojo.Session;

import java.util.*;


/**
 * 合成列表
 * <p>
 * {
 * cmd: "compositeList",   //命令
 * composite_list 返回
 * }
 */
public class CompositeList extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public CompositeList(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        //用户信息
        PlayerCharacter playerCharacter = (PlayerCharacter) getCaller();
        //参数
        Map<String, List<CompositeMaterialObject>> commonObjectMap = new HashMap<>();
        List<CompositeMaterialObject> commonObjectList = new ArrayList<CompositeMaterialObject>();
        CompositeMaterialObject compositeMaterialObject = null;
        //装备列表
        Iterable<Equipment> equipmentIterable = DbMapper.equipmentRepository.findAll();
        Iterator<Equipment> equipEquipmentIterator = equipmentIterable.iterator();
        Equipment equipment = null;
        while (equipEquipmentIterator.hasNext()) {
            compositeMaterialObject = new CompositeMaterialObject();
            equipment = equipEquipmentIterator.next();
            BeanUtils.copyProperties(equipment, compositeMaterialObject);
            //合成配方信息
            getCompositeMaterialObject(commonObjectList, compositeMaterialObject, equipment.getDataKey());
        }
        commonObjectMap.put("武器", commonObjectList);
        commonObjectList = new ArrayList<CompositeMaterialObject>();
        //技能书列表
        Iterable<SkillBook> skillBookRepositoryAll = DbMapper.skillBookRepository.findAll();
        Iterator<SkillBook> skillBookIterator = skillBookRepositoryAll.iterator();
        SkillBook skillBook = null;
        while (skillBookIterator.hasNext()) {
            skillBook = skillBookIterator.next();
            compositeMaterialObject = new CompositeMaterialObject();
            BeanUtils.copyProperties(skillBook, compositeMaterialObject);
            //合成配方信息
            getCompositeMaterialObject(commonObjectList, compositeMaterialObject, skillBook.getDataKey());
        }
        commonObjectMap.put("技能书", commonObjectList);
        playerCharacter.msg(new CompositeListMessage(commonObjectMap));
    }

    public List<CompositeMaterialObject> getCompositeMaterialObject(List<CompositeMaterialObject> compositeMaterialObjectList, CompositeMaterialObject compositeMaterialObject, String datakey) {
        BaseCommonObject baseCommonObject = null;
        CompositeMaterial compositeMaterial = null;
        List<CompositeMaterial> compositeMaterialList = DbMapper.compositeMaterialRepository.findCompositeMaterialByDataKey(datakey);
        Iterator<CompositeMaterial> compositeMaterialIterator = compositeMaterialList.iterator();
        while (compositeMaterialIterator.hasNext()) {
            compositeMaterial = compositeMaterialIterator.next();
            //查询材料信息
            baseCommonObject = CommonObjectBuilder.findObjectTemplateByDataKey(compositeMaterial.getDependency());
            compositeMaterial.setName(baseCommonObject.getName());
            compositeMaterial.setDescription(baseCommonObject.getDescription());
            compositeMaterial.setIcon(baseCommonObject.getIcon());
            compositeMaterial.setCategory(baseCommonObject.getCategory());
        }
        compositeMaterialObject.setCompositeMaterialList(compositeMaterialList);
        compositeMaterialObjectList.add(compositeMaterialObject);
        return compositeMaterialObjectList;
    }
}
