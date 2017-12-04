package com.lemon.carmonitor.model.bean;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.bean]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/3/18 14:08]
 * 修改人:    [xflu]
 * 修改时间:  [2016/3/18 14:08]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class UserAccount {

    /**
     * id : 5
     * smsTotal : 3001
     * smsBalance : 2239
     * moneyTotal : 200.01
     */

    private int id;
    private int smsTotal;
    private int smsBalance;
    private double moneyTotal;

    public void setId(int id) {
        this.id = id;
    }

    public void setSmsTotal(int smsTotal) {
        this.smsTotal = smsTotal;
    }

    public void setSmsBalance(int smsBalance) {
        this.smsBalance = smsBalance;
    }

    public void setMoneyTotal(double moneyTotal) {
        this.moneyTotal = moneyTotal;
    }

    public int getId() {
        return id;
    }

    public int getSmsTotal() {
        return smsTotal;
    }

    public int getSmsBalance() {
        return smsBalance;
    }

    public double getMoneyTotal() {
        return moneyTotal;
    }
}
