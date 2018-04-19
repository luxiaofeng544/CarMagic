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
@Entity(nameInDb = "tb_alarm")
public class Alarm implements IAlarmConstant {

    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb  = "devSn")
    private String devSn;
    @Property(nameInDb = "devName")
    private String devName;
    @Property(nameInDb = "alarmType")
    private String alarmType;
    @Property(nameInDb = "alarmTypeName")
    private String alarmTypeName;
    @Property(nameInDb = "alarmTime")
    private String alarmTime;
    @Property(nameInDb = "alais")
    private String alais;
    @Property(nameInDb = "statue")
    private String statue;
    @Generated(hash = 426907249)
    public Alarm(Long id, String devSn, String devName, String alarmType,
            String alarmTypeName, String alarmTime, String alais, String statue) {
        this.id = id;
        this.devSn = devSn;
        this.devName = devName;
        this.alarmType = alarmType;
        this.alarmTypeName = alarmTypeName;
        this.alarmTime = alarmTime;
        this.alais = alais;
        this.statue = statue;
    }
    @Generated(hash = 1972324134)
    public Alarm() {
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
    public String getAlarmType() {
        return this.alarmType;
    }
    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }
    public String getAlarmTypeName() {
        return this.alarmTypeName;
    }
    public void setAlarmTypeName(String alarmTypeName) {
        this.alarmTypeName = alarmTypeName;
    }
    public String getAlarmTime() {
        return this.alarmTime;
    }
    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }
    public String getAlais() {
        return this.alais;
    }
    public void setAlais(String alais) {
        this.alais = alais;
    }
    public String getStatue() {
        return this.statue;
    }
    public void setStatue(String statue) {
        this.statue = statue;
    }

}
