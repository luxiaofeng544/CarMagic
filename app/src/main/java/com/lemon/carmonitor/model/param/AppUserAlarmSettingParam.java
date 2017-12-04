package com.lemon.carmonitor.model.param;

import com.lemon.annotation.Module;
import com.lemon.model.BaseParam;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.param]
 * 类描述:    []
 * 创建人:    [xflu]
 * 创建时间:  [2016/1/6 17:19]
 * 修改人:    [xflu]
 * 修改时间:  [2016/1/6 17:19]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Module(server = "dev_server", name = "apiApp" )
public class AppUserAlarmSettingParam extends BaseParam {

    private String loginToken;
    private boolean enableAlarm;
    private boolean enableRingtone;
    private boolean enableVibrate;
    private boolean enableSms;

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getEnableAlarm() {
        return Boolean.toString(enableAlarm);
    }

    public void setEnableAlarm(boolean enableAlarm) {
        this.enableAlarm = enableAlarm;
    }

    public String getEnableRingtone() {
        return Boolean.toString(enableRingtone);
    }

    public void setEnableRingtone(boolean enableRingtone) {
        this.enableRingtone = enableRingtone;
    }

    public String getEnableVibrate() {
        return Boolean.toString(enableVibrate);
    }

    public void setEnableVibrate(boolean enableVibrate) {
        this.enableVibrate = enableVibrate;
    }

    public String getEnableSms() {
        return Boolean.toString(enableSms);
    }

    public void setEnableSms(boolean enableSms) {
        this.enableSms = enableSms;
    }

    @Override
    public String toString() {
        return "AppUserAlarmSettingParam{" +
                "loginToken='" + loginToken + '\'' +
                ", enableAlarm=" + enableAlarm +
                ", enableRingtone=" + enableRingtone +
                ", enableVibrate=" + enableVibrate +
                ", enableSms=" + enableSms +
                '}';
    }
}
