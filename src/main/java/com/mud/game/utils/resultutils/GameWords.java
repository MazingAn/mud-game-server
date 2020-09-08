package com.mud.game.utils.resultutils;

public class GameWords {
    public static final String PLAYER_LEFT_ROOM = "{r%s{n离开了{b%s{n，往{b%s{n方向去了。";
    public static final String PLAYER_JOIN_ROOM = "{r%s{n从{b%s{n来到了{b%s{n。";
    public static final String PLAYER_OFFLINE = "{r%s{n离开了游戏";
    public static final String PLAYER_ONLINE = "{r%s{n进入了游戏";

    // 玩家状态
    public static final String PLAYER_DIED = "{y你已经死了！{n";
    public static final String PLAYER_RECOVER_HP = "你回复了{g%s{n点气血";
    public static final String PLAYER_RECOVER_MAX_HP = "你回复了{g%s{n点最大气血";
    public static final String NEED_NOT_CURE = "你的{g气血充盈{n，不需要{r疗伤{n！";
    public static final String PLAYER_CURE_END = "{g你已经恢复到最佳状态！{n";

    public static final String PLAYER_RECOVER_MP = "你回复了{g%s{n点最气血";
    public static final String PLAYER_RECOVER_MAX_MP = "你回复了{g%s{n点最大气血";
    public static final String NEED_NOT_MEDITATE = "你的{g内力充沛{n，不需要{r打坐{n！";
    public static final String PLAYER_MEDITATE_END = "{g你已经恢复到最佳状态！{n";

    // 没有权限使用物品
    public static final String NO_PREMISSION_USE = "你无法使用%s,因为他的归属不是你！";

    // 好友结交过程
    public static final String PLAYER_REPEAT_REQUEST_FRIEND = "{r%s{n已经知道了你的结交之心，还是暂时不要打扰他为好！";  //重复发送好友请求
    public static final String PLAYER_REQUEST_FRIEND = "你拱起双手走了上去，想要和{r%s{n结交一番！"; //发送好友请求成功
    public static final String PLAYER_RECEIVE_FRIEND_REQUEST = "{r%s{n一脸虔诚的走过来对你说：\"这位朋友可否赏个面子，结交一番？\""; //收到好友请求
    public static final String PLAYER_REFUSE_FRIEND_REQUEST = "你拒绝了{r%s{n的结交"; //拒绝好友请求
    public static final String PLAYER_BE_REFUSED_FRIEND_REQUEST = "{r%s{n看上去脸色并不太好，没有理你！"; //好友请求被拒绝
    public static final String PLAYER_APPLY_FRIEND_REQUEST = "你同意了{r%s{n的请求，对他说：\"如此甚好，真实荣幸之至！\""; //同意好友请求
    public static final String PLAYER_BE_APPLIED_FRIEND_REQUEST = "{r%s{n同意了你的请求，并对你说：\"如此甚好，真实荣幸之至！\""; //好友请求被同意


    // 拜师
    public static final String PLAYER_MUST_LEAVE_OLD_TEACHER = "{r%s{n对你说:\"阁下既然已经有了师门，又何必来这里打趣本人？\"";
    public static final String TEACHER_NOT_FOUND = "好像并没有找到{r%s{n这个人，你到底要拜谁为师？";
    public static final String PLAYER_FINDED_TEACHER = "你走上前去对{r%s{n行了拜师礼，并奉上了{c一盏茶{n？{{ %s满地的对你点了点头：\"从今以后，你就是我{b%s{n的{c徒弟{n了\"";
    public static final String NPC_NOT_TEACH = "{r%s{n没有什么惊人的才能，也不收徒！";

    // 学习技能
    public static final String ERROR_TEACHER = "{g%s{n脸疑惑的问到：\"在下好像并不记得有你这么个徒弟，何来请教一说\"";
    public static final String TEACHER_HAS_NO_SKILL = "{g%s{n有点生气的对你说:\"这门功法为师谁也不传！\"";
    public static final String CAN_NOT_LEARN_SKILL = "{g%s{n不紧不慢的对你说:\"以你当前的{g功力{n，这门功{r暂时还修习不了{n！\"";
    public static final String NO_ENOUGH_POTENTIAL = "你的{g潜能{n已经用尽了！";
    public static final String NO_ENOUGH_POTENTIAL_BALANCE = "你在{g%s{n这里换取的潜能已经耗尽";
    public static final String SKILL_LEVEL_UP = "你的{g%s{n等级提升到了{g%s{n级。";
    public static final String LEARNED_SKILL = "你学会了技能{g%s{n。";
    public static final String START_LEARNED_SKILL = "你开始向{g%s{n请教{g%s{n。";
    public static final String START_LEARNED_SKILL_BOOK = "你开始学习{g%s{n。";

    // 挂机
    public static final String ROOM_CANNOT_MINING = "你无法在当前位置挖矿，请前往矿厂";
    public static final String NO_MINING_SKILL = "你还没有学会{g挖矿{n, 先找个{y矿工{n请教请教！";
    public static final String NO_MINING_EQUIPMENT = "你没有装备{g铁镐{n, 先找{y铁匠{n购买！";
    public static final String NO_FISHING_YUGAN_EQUIPMENT = "你没有装备{g钓鱼竿{n, 先找{y铁匠{n购买！";
    public static final String NO_FISHING_SKILL = "你还没有学会{g钓鱼{n, 先找个{y垂钓者{n请教请教！";
    public static final String NO_COLLECT_SKILL = "你还没有学会{g采药{n, 先找个{y采药人{n请教请教！";
    public static final String NO_COLLECT_EQUIPMENT = "你没有装备{g药镰{n, 先找{y铁匠{n购买！";
    public static final String PLAYER_GET_RANDOM_POTENTIAL = "你获得%s经验，%s点潜能";
    public static final String NO_ENOUGH_TILI = "你的{g体力{n已经透支，无法继续挂机";
    public static final String START_MINING = "你挥着铁镐开始认真挖矿。";
    public static final String STOP_MINING = "你擦了擦额头上的汗，收起铁镐停止了挖掘。";
    public static final String START_COLLECT = "你一头钻进树林，开始认真寻找药材。";
    public static final String STOP_COLLECT = "你擦了擦额头上的汗，停止了寻找。";
    public static final String START_FISHING = "你拿起手中鱼竿，挂上鱼饵，洒入水中，开始耐心垂钓。";
    public static final String STOP_FISHING = "你收起手中的鱼竿，停止了垂钓。";
    public static final String START_MEDITATE = "你坐下来运气用功，一股内息开始在体内流动。";
    public static final String STOP_MEDITATE = "你运功完毕，深深吸了口气，站了起来。";
    public static final String START_CURE = "你坐下来运气用功，催动内力试图修复伤势。";
    public static final String STOP_CURE = "{y你疗伤完毕，深深吸了口气，脸色看起来好了很多。{n";
    public static final String START_LEARN_SKILL = "你静下心来，开始了对武学的探索。";
    public static final String STOP_LEARN_SKILL = "{y你深吸一口气，暂时先放弃了对武学的探索。{n";
    public static final String PLAYER_DO_OTHER_THING = "你正在{g%s{n,没有多余的功夫来{r%s{n";
    public static final String ALREADY_DOING = "{y你已经在%s了{n";
    public static final String HANGUP_PLACE_ERROR = "你不能在这里{r%s{n";

    // 装备技能
    public static final String NOT_EQUIP_SKILL = "你并没有装备这个技能";
    // 学习技能条件
    public static final String NEED_SKILL_LEVEL_GT = "需要技能%s等级达到%s级";
    // 子技能等级不够
    public static final String BASIC_SKILL_LEVEL_GT = "学习%s技能失败，%s等级不够";
    // 技能达到npc的技能等级
    public static final String NPC_SKILL_LEVEL_GT = "你的技能已经不弱于你师傅";
    // 装备装备
    public static final String EQUIPMENT_ALREADY_EQUIPPED = "{c%s{n已经被装备。";

    // 物品获取
    public static final String CAN_NOT_GET_OBJECT = "你的背包装不下那么多的{g%s{n。";

    // 背包移除相关
    public static final String CAN_NOT_REMOVE_FROM_BAGPACK = "无法从背包移除{r%s %s%s{n";

    // 玩家更改高度
    public static final String ROOM_STEP_CHANGED = "你突然感觉眼前一花，再睁开眼睛，眼前的一切都变了样子。";

    // 无法创建任务
    public static final String CANNOT_CREATE_QUEST = "{r无法创建任务{n";
    // 使用道具
    public static final String USE_OBJECT = "你使用了{g%s{n";
}
