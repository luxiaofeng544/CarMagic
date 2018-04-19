package com.lemon.carmonitor.db.gd;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.lemon.carmonitor.contant.IAlarmConstant;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

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
@Entity(nameInDb = "tb_voice")
public class Voice implements IAlarmConstant {

    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "devSn")
    private String devSn;
    @Property(nameInDb = "devName")
    private String devName;
    @Property(nameInDb = "alarmId")
    private int alarmId;
    @Property(nameInDb = "voiceUrl")
    private String voiceUrl;
    @Property(nameInDb = "voiceTime")
    private String voiceTime;
    @Property(nameInDb = "localPath")
    private String localPath;
    @Property(nameInDb = "statue")
    private String statue;
    @Generated(hash = 905198745)
    public Voice(Long id, String devSn, String devName, int alarmId,
            String voiceUrl, String voiceTime, String localPath, String statue) {
        this.id = id;
        this.devSn = devSn;
        this.devName = devName;
        this.alarmId = alarmId;
        this.voiceUrl = voiceUrl;
        this.voiceTime = voiceTime;
        this.localPath = localPath;
        this.statue = statue;
    }
    @Generated(hash = 1158611544)
    public Voice() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDevSn() {
        return this.devSn;
    }
    public void setDevSn(String devSn) {
        this.devSn = devSn;
    }
    public String getDevName() {
        return this.devName;
    }
    public void setDevName(String devName) {
        this.devName = devName;
    }
    public int getAlarmId() {
        return this.alarmId;
    }
    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }
    public String getVoiceUrl() {
        return this.voiceUrl;
    }
    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }
    public String getVoiceTime() {
        return this.voiceTime;
    }
    public void setVoiceTime(String voiceTime) {
        this.voiceTime = voiceTime;
    }
    public String getLocalPath() {
        return this.localPath;
    }
    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
    public String getStatue() {
        return this.statue;
    }
    public void setStatue(String statue) {
        this.statue = statue;
    }

}
