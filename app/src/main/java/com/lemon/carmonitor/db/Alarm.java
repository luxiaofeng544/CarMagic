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
@DatabaseTable(tableName = "tb_alarm")
public class Alarm implements IAlarmConstant {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "devSn")
    private String devSn;
    @DatabaseField(columnName = "devName")
    private String devName;
    @DatabaseField(columnName = "alarmType")
    private String alarmType;
    @DatabaseField(columnName = "alarmTypeName")
    private String alarmTypeName;
    @DatabaseField(columnName = "alarmTime")
    private String alarmTime;
    @DatabaseField(columnName = "alais")
    private String alais;
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

    public String getAlais() {
        return alais;
    }

    public void setAlais(String alais) {
        this.alais = alais;
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

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "id=" + id +
                ", devSn='" + devSn + '\'' +
                ", devName='" + devName + '\'' +
                ", alarmType='" + alarmType + '\'' +
                ", alarmTypeName='" + alarmTypeName + '\'' +
                ", alarmTime='" + alarmTime + '\'' +
                ", alais='" + alais + '\'' +
                '}';
    }
}
