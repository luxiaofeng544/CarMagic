package com.lemon.carmonitor.jpush;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.jpush]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/1/15 9:58]
 * 修改人:    [xflu]
 * 修改时间:  [2016/1/15 9:58]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class JpushInfo {

    /**
     * devSn : devSn
     * alatmType : alatmType
     * alias : ["138******","121321"]
     */

    private String devSn;
    private String devName;
    private String alarmType;
    private String alarmTime;
    private String alarmTypeName;
    private String alias;

    public String getDevSn() {
        return devSn;
    }

    public void setDevSn(String devSn) {
        this.devSn = devSn;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getAlarmTypeName() {
        return alarmTypeName;
    }

    public void setAlarmTypeName(String alarmTypeName) {
        this.alarmTypeName = alarmTypeName;
    }
}
