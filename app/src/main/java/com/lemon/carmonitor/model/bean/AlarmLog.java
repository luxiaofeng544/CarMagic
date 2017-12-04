package com.lemon.carmonitor.model.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.bean]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/3/12 19:26]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/3/12 19:26]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlarmLog {

    private int id;
    private String appUserId;
    private String alarmTime;
    private String alarmType;
    private String alarmTypeName;
    private String alias;
    private String devName;
    private String devSn;
    private long createTime;
    private String seenFlag;
    private String platType;
    private String voiceUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(String appUserId) {
        this.appUserId = appUserId;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmTypeName() {
        return alarmTypeName;
    }

    public void setAlarmTypeName(String alarmTypeName) {
        this.alarmTypeName = alarmTypeName;
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

    public String getDevSn() {
        return devSn;
    }

    public void setDevSn(String devSn) {
        this.devSn = devSn;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getSeenFlag() {
        return seenFlag;
    }

    public void setSeenFlag(String seenFlag) {
        this.seenFlag = seenFlag;
    }

    public String getPlatType() {
        return platType;
    }

    public void setPlatType(String platType) {
        this.platType = platType;
    }

    public String getVoiceUrl() {
        return voiceUrl;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    @Override
    public String toString() {
        return "AlarmLog{" +
                "id=" + id +
                ", appUserId='" + appUserId + '\'' +
                ", alarmTime='" + alarmTime + '\'' +
                ", alarmType='" + alarmType + '\'' +
                ", alarmTypeName='" + alarmTypeName + '\'' +
                ", alias='" + alias + '\'' +
                ", devName='" + devName + '\'' +
                ", devSn='" + devSn + '\'' +
                ", createTime=" + createTime +
                ", seenFlag='" + seenFlag + '\'' +
                ", platType='" + platType + '\'' +
                ", voiceUrl='" + voiceUrl + '\'' +
                '}';
    }
}
