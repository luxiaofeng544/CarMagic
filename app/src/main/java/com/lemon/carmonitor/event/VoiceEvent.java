package com.lemon.carmonitor.event;

import com.lemon.carmonitor.db.Alarm;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.event]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2015/12/16 15:04]
 * 修改人:    [xflu]
 * 修改时间:  [2015/12/16 15:04]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class VoiceEvent {
    private String type;//play
    private Alarm alarm;

    public VoiceEvent(String type, Alarm alarm) {
        this.type = type;
        this.alarm = alarm;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }
}
