package com.lemon.carmonitor.model.bean;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.bean]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/2/4 10:53]
 * 修改人:    [xflu]
 * 修改时间:  [2016/2/4 10:53]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class ChargeModel {

    /**
     * id : 12
     * appUserId : 5
     * packageId : 1
     * payWay : 1
     * payChannel : 1
     * orderId : android-test-1
     * money : 9
     * smsAmount : 100
     * createTime : 1454553714000
     * finishedTime : null
     * status : 0
     * totalFee : null
     * sellerId : null
     * createTimeStr : 2016-02-04 10:41:54
     * finishedTimeStr : null
     */

    private int id;
    private int appUserId;
    private int packageId;
    private String payWay;
    private String payChannel;
    private String orderId;
    private double money;
    private int smsAmount;
    private long createTime;
    private Object finishedTime;
    private String status;
    private Object totalFee;
    private Object sellerId;
    private String createTimeStr;
    private Object finishedTimeStr;

    public void setId(int id) {
        this.id = id;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setSmsAmount(int smsAmount) {
        this.smsAmount = smsAmount;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void setFinishedTime(Object finishedTime) {
        this.finishedTime = finishedTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalFee(Object totalFee) {
        this.totalFee = totalFee;
    }

    public void setSellerId(Object sellerId) {
        this.sellerId = sellerId;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public void setFinishedTimeStr(Object finishedTimeStr) {
        this.finishedTimeStr = finishedTimeStr;
    }

    public int getId() {
        return id;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public int getPackageId() {
        return packageId;
    }

    public String getPayWay() {
        return payWay;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public String getOrderId() {
        return orderId;
    }

    public double getMoney() {
        return money;
    }

    public int getSmsAmount() {
        return smsAmount;
    }

    public long getCreateTime() {
        return createTime;
    }

    public Object getFinishedTime() {
        return finishedTime;
    }

    public String getStatus() {
        return status;
    }

    public Object getTotalFee() {
        return totalFee;
    }

    public Object getSellerId() {
        return sellerId;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public Object getFinishedTimeStr() {
        return finishedTimeStr;
    }
}
