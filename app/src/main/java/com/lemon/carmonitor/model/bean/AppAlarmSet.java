package com.lemon.carmonitor.model.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.bean]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/1/20 21:25]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/1/20 21:25]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppAlarmSet {

    private boolean enableAlarm;
    private boolean enableRingtone;
    private boolean enableVibrate;
    private boolean enableSms;

    public boolean isEnableAlarm() {
        return enableAlarm;
    }

    public void setEnableAlarm(boolean enableAlarm) {
        this.enableAlarm = enableAlarm;
    }

    public boolean isEnableRingtone() {
        return enableRingtone;
    }

    public void setEnableRingtone(boolean enableRingtone) {
        this.enableRingtone = enableRingtone;
    }

    public boolean isEnableVibrate() {
        return enableVibrate;
    }

    public void setEnableVibrate(boolean enableVibrate) {
        this.enableVibrate = enableVibrate;
    }

    public boolean isEnableSms() {
        return enableSms;
    }

    public void setEnableSms(boolean enableSms) {
        this.enableSms = enableSms;
    }
}
