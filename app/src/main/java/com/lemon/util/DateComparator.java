package com.lemon.util;

import com.lemon.carmonitor.db.Alarm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.util]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2017/4/24 17:29]
 * 修改人:    [xflu]
 * 修改时间:  [2017/4/24 17:29]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */


public class DateComparator implements Comparator {
    public int compare(Object arg0, Object arg1){
        Alarm alarm1 = (Alarm)arg0;
        Alarm alarm2 = (Alarm)arg1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = sdf.parse(alarm1.getAlarmTime());
            Date d2 = sdf.parse(alarm2.getAlarmTime());
            return d1.getTime()> d2.getTime()?-1:1;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return -1;
    }
}