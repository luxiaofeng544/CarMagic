package com.lemon.carmonitor.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.lemon.carmonitor.contant.IAlarmConstant;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.db]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/1/15 15:59]
 * 修改人:    [xflu]
 * 修改时间:  [2016/1/15 15:59]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@DatabaseTable(tableName = "tb_voice")
public class Voice implements IAlarmConstant {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "devSn")
    private String devSn;
    @DatabaseField(columnName = "devName")
    private String devName;
    @DatabaseField(columnName = "alarmId")
    private int alarmId;
    @DatabaseField(columnName = "voiceUrl")
    private String voiceUrl;
    @DatabaseField(columnName = "voiceTime")
    private String voiceTime;
    @DatabaseField(columnName = "localPath")
    private String localPath;
    @DatabaseField(columnName = "statue")
    private String statue;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDevSn() {
        return devSn;
    }

    public void setDevSn(String devSn) {
        this.devSn = devSn;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public String getVoiceUrl() {
        return voiceUrl;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    public String getVoiceTime() {
        return voiceTime;
    }

    public void setVoiceTime(String voiceTime) {
        this.voiceTime = voiceTime;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    @Override
    public String toString() {
        return "Voice{" +
                "statue='" + statue + '\'' +
                ", localPath='" + localPath + '\'' +
                ", voiceTime='" + voiceTime + '\'' +
                ", voiceUrl='" + voiceUrl + '\'' +
                ", alarmId='" + alarmId + '\'' +
                ", devName='" + devName + '\'' +
                ", devSn='" + devSn + '\'' +
                ", id=" + id +
                '}';
    }
}
