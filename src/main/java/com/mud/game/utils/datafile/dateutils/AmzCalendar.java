package com.mud.game.utils.datafile.dateutils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/*
* 日历处理工具
* */
public class AmzCalendar {

    /*
    * 返回当前时间的字符串，格式化为 指定格式
    * */
    public static String dateNow(String fomart){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(fomart);
        return sdf.format(calendar.getTime());
    }

}
