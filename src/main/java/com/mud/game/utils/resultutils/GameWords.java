package com.mud.game.utils.resultutils;

public class GameWords {
    public static final String PLAYER_LEFT_ROOM = "{r%s{n离开了{b%s{n，往{b%s{n方向去了。";
    public static final String PLAYER_JOIN_ROOM = "{r%s{n从{b%s{n来到了{b%s{n。";
    public static final String PLAYER_OFFLINE = "{r%s{n离开了游戏";
    public static final String PLAYER_ONLINE = "{r%s{n进入了游戏";

    // 好友结交过程
    public static final String PLAYER_REPEAT_REQUEST_FRIEND = "{r%s{n已经知道了你的结交之心，还是暂时不要打扰他为好！";  //重复发送好友请求
    public static final String PLAYER_REQUEST_FRIEND = "你拱起双手走了上去，想要和{r%s{n结交一番！"; //发送好友请求成功
    public static final String PLAYER_RECEIVE_FRIEND_REQUEST = "{r%s{n一脸虔诚的走过来对你说：\"这位朋友可否赏个面子，结交一番？\""; //收到好友请求
    public static final String PLAYER_REFUSE_FRIEND_REQUEST = "你拒绝了{r%s{n的结交"; //拒绝好友请求
    public static final String PLAYER_BE_REFUSED_FRIEND_REQUEST = "{r%s{n看上去脸色并不太好，没有理你！"; //好友请求被拒绝
    public static final String PLAYER_APPLY_FRIEND_REQUEST = "你同意了{r%s{n的请求，对他说：\"如此甚好，真实荣幸之至！\""; //同意好友请求
    public static final String PLAYER_BE_APPLIED_FRIEND_REQUEST = "{r%s{n同意了你的请求，并对你说：\"如此甚好，真实荣幸之至！\""; //好友请求被同意


    // 拜师
    public static  final String PLAYER_MUST_LEAVE_OLD_TEACHER = "{r%s{n对你说:\"阁下既然已经有了师门，又何必来这里打趣本人？\"";
    public static  final String TEACHER_NOT_FOUND = "好像并没有找到{r%s{n这个人，你到底要拜谁为师？";
    public static  final String PLAYER_FINDED_TEACHER = "你走上前去对{r%s{n行了拜师礼，并奉上了{c一盏茶{n？{{ %s满地的对你点了点头：\"从今以后，你就是我{b%s{n的{c徒弟{n了\"";

    // 学习技能
    public static final String NPC_NOT_TEACH = "{r%s{n没有什么惊人的才能，也不收徒！" ;
}
