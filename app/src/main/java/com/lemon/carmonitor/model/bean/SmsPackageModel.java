package com.lemon.carmonitor.model.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.bean]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/2/4 9:52]
 * 修改人:    [xflu]
 * 修改时间:  [2016/2/4 9:52]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SmsPackageModel {

    /**
     * id : 1
     * packageName : 9元套餐
     * money : 9
     * smsAmount : 100
     * packageDesc :
     * enableFlag : 1
     * createTime : 1454152371000
     * createTimeStr : 2016-01-30 19:12:51
     */

    private int id;
    private String packageName;
    private double money;
    private int smsAmount;
    private String packageDesc;
    private String enableFlag;
    private long createTime;
    private String createTimeStr;

    public void setId(int id) {
        this.id = id;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setSmsAmount(int smsAmount) {
        this.smsAmount = smsAmount;
    }

    public void setPackageDesc(String packageDesc) {
        this.packageDesc = packageDesc;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public int getId() {
        return id;
    }

    public String getPackageName() {
        return packageName;
    }

    public double getMoney() {
        return money;
    }

    public int getSmsAmount() {
        return smsAmount;
    }

    public String getPackageDesc() {
        return packageDesc;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    @Override
    public String toString() {
        return "SmsPackageModel{" +
                "id=" + id +
                ", packageName='" + packageName + '\'' +
                ", money=" + money +
                ", smsAmount=" + smsAmount +
                ", packageDesc='" + packageDesc + '\'' +
                ", enableFlag='" + enableFlag + '\'' +
                ", createTime=" + createTime +
                ", createTimeStr='" + createTimeStr + '\'' +
                '}';
    }
}
