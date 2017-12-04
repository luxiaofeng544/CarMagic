package com.lemon.carmonitor.model.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.bean]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/1/6 23:47]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/1/6 23:47]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLoginBean {
    private String token;
    private String phoneNum;
    private String serviceId;
    private boolean enableAlarm;
    private boolean enableRingtone;
    private boolean enableVibrate;
    private boolean enableSms;
    private boolean yingYanService = true;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

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

    public boolean isYingYanService() {
        return yingYanService;
    }

    public void setYingYanService(boolean yingYanService) {
        this.yingYanService = yingYanService;
    }

    @Override
    public String toString() {
        return "UserLoginBean{" +
                "token='" + token + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", enableAlarm=" + enableAlarm +
                ", enableRingtone=" + enableRingtone +
                ", enableVibrate=" + enableVibrate +
                ", enableSms=" + enableSms +
                ", yingYanService=" + yingYanService +
                '}';
    }
}
