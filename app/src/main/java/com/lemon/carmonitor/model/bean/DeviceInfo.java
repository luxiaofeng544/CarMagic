package com.lemon.carmonitor.model.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.bean]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/1/7 17:31]
 * 修改人:    [xflu]
 * 修改时间:  [2016/1/7 17:31]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@DatabaseTable(tableName = "tbl_device")
public class DeviceInfo {
    @DatabaseField(columnName = "devSn")
    private String devSn;
    @DatabaseField(columnName = "createTime")
    private String createTime;
    @DatabaseField(columnName = "devName")
    private String devName;
    @DatabaseField(columnName = "carNum")
    private String carNum;
    @DatabaseField(columnName = "contractName")
    private String contractName;
    @DatabaseField(columnName = "contractNum")
    private String contractNum;
    @DatabaseField(columnName = "devModel")
    private String devModel;
    @DatabaseField(columnName = "enableFlag")
    private String enableFlag;
    @DatabaseField(columnName = "traceEntityName")
    private String traceEntityName;
    @DatabaseField(columnName = "vehicleStatus")
    private String vehicleStatus;
    @DatabaseField(columnName = "batteryVolt")
    private String batteryVolt;
    @DatabaseField(columnName = "validDate")
    private String validDate;
    @DatabaseField(columnName = "devPhoneNum")
    private String devPhoneNum;
    @DatabaseField(columnName = "online")
    private String online;
    @DatabaseField(columnName = "makerCode")
    private String makerCode;
    @DatabaseField(columnName = "lastUpdateDate")
    private long lastUpdateDate;
    @DatabaseField(columnName = "enableAlarm")
    private String enableAlarm;
    @DatabaseField(columnName = "enableRingtone")
    private String enableRingtone;
    @DatabaseField(columnName = "enableVibrate")
    private String enableVibrate;
    @DatabaseField(columnName = "devProtocol")
    private String devProtocol;
    @DatabaseField(columnName = "sleepStatus")
    private String sleepStatus;
    @DatabaseField(columnName = "workModel")
    private String workModel;

    public String getDevSn() {
        return devSn;
    }

    public void setDevSn(String devSn) {
        this.devSn = devSn;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getDevModel() {
        return devModel;
    }

    public void setDevModel(String devModel) {
        this.devModel = devModel;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public String getTraceEntityName() {
        return traceEntityName;
    }

    public void setTraceEntityName(String traceEntityName) {
        this.traceEntityName = traceEntityName;
    }

    public String getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(String vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public String getBatteryVolt() {
        return batteryVolt;
    }

    public void setBatteryVolt(String batteryVolt) {
        this.batteryVolt = batteryVolt;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getDevPhoneNum() {
        return devPhoneNum;
    }

    public void setDevPhoneNum(String devPhoneNum) {
        this.devPhoneNum = devPhoneNum;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public long getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(long lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getMakerCode() {
        return makerCode;
    }

    public void setMakerCode(String makerCode) {
        this.makerCode = makerCode;
    }

    public String getEnableAlarm() {
        return enableAlarm;
    }

    public void setEnableAlarm(String enableAlarm) {
        this.enableAlarm = enableAlarm;
    }

    public String getEnableRingtone() {
        return enableRingtone;
    }

    public void setEnableRingtone(String enableRingtone) {
        this.enableRingtone = enableRingtone;
    }

    public String getEnableVibrate() {
        return enableVibrate;
    }

    public void setEnableVibrate(String enableVibrate) {
        this.enableVibrate = enableVibrate;
    }

    public String getDevProtocol() {
        return devProtocol;
    }

    public void setDevProtocol(String devProtocol) {
        this.devProtocol = devProtocol;
    }

    public String getSleepStatus() {
        return sleepStatus;
    }

    public void setSleepStatus(String sleepStatus) {
        this.sleepStatus = sleepStatus;
    }

    public String getWorkModel() {
        return workModel;
    }

    public void setWorkModel(String workModel) {
        this.workModel = workModel;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "devSn='" + devSn + '\'' +
                ", createTime='" + createTime + '\'' +
                ", devName='" + devName + '\'' +
                ", carNum='" + carNum + '\'' +
                ", contractName='" + contractName + '\'' +
                ", contractNum='" + contractNum + '\'' +
                ", devModel='" + devModel + '\'' +
                ", enableFlag='" + enableFlag + '\'' +
                ", traceEntityName='" + traceEntityName + '\'' +
                ", vehicleStatus='" + vehicleStatus + '\'' +
                ", batteryVolt='" + batteryVolt + '\'' +
                ", validDate='" + validDate + '\'' +
                ", devPhoneNum='" + devPhoneNum + '\'' +
                ", online='" + online + '\'' +
                ", makerCode='" + makerCode + '\'' +
                ", lastUpdateDate=" + lastUpdateDate +
                ", enableAlarm='" + enableAlarm + '\'' +
                ", enableRingtone='" + enableRingtone + '\'' +
                ", enableVibrate='" + enableVibrate + '\'' +
                ", devProtocol='" + devProtocol + '\'' +
                ", sleepStatus='" + sleepStatus + '\'' +
                ", workModel='" + workModel + '\'' +
                '}';
    }
}
