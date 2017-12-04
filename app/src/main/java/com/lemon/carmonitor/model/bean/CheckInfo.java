package com.lemon.carmonitor.model.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 项目名称:  [CarMonitor-3]
 * 包:        [com.lemon.carmonitor.model.bean]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/9/5 19:43]
 * 修改人:    [xflu]
 * 修改时间:  [2016/9/5 19:43]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckInfo {


    /**
     * check : false
     * platform : Android,Ios
     * repair : false
     * probability : 100
     * exit : true
     * mobile : 13850000000,13850000000,13850000000
     * messageTime : 5000
     * exitTime : 8000
     * remember : true
     * desc : APP运行严重异常,请联系客服人员
     */

    private String check;
    private String platform;
    private String repair;
    private int probability;
    private String exit;
    private String mobile;
    private int messageTime;
    private int exitTime;
    private String remember;
    private String desc;

    public void setCheck(String check) {
        this.check = check;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setRepair(String repair) {
        this.repair = repair;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    public void setExit(String exit) {
        this.exit = exit;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setMessageTime(int messageTime) {
        this.messageTime = messageTime;
    }

    public void setExitTime(int exitTime) {
        this.exitTime = exitTime;
    }

    public void setRemember(String remember) {
        this.remember = remember;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCheck() {
        return check;
    }

    public String getPlatform() {
        return platform;
    }

    public String getRepair() {
        return repair;
    }

    public int getProbability() {
        return probability;
    }

    public String getExit() {
        return exit;
    }

    public String getMobile() {
        return mobile;
    }

    public int getMessageTime() {
        return messageTime;
    }

    public int getExitTime() {
        return exitTime;
    }

    public String getRemember() {
        return remember;
    }

    public String getDesc() {
        return desc;
    }
}
