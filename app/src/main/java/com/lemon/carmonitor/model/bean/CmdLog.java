package com.lemon.carmonitor.model.bean;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.bean]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/3/18 14:39]
 * 修改人:    [xflu]
 * 修改时间:  [2016/3/18 14:39]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class CmdLog {

    /**
     * id : 39
     * appUserId : 6
     * devSn : 4209504370
     * cmd : setRunInterval
     * param1 : 60
     * param2 :
     * param3 :
     * param4 :
     * createTime : 1457685788000
     */

    private int id;
    private int appUserId;
    private String devSn;
    private String cmd;
    private String param1;
    private String param2;
    private String param3;
    private String param4;
    private long createTime;

    public void setId(int id) {
        this.id = id;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public void setDevSn(String devSn) {
        this.devSn = devSn;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public void setParam4(String param4) {
        this.param4 = param4;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public String getDevSn() {
        return devSn;
    }

    public String getCmd() {
        return cmd;
    }

    public String getParam1() {
        return param1;
    }

    public String getParam2() {
        return param2;
    }

    public String getParam3() {
        return param3;
    }

    public String getParam4() {
        return param4;
    }

    public long getCreateTime() {
        return createTime;
    }

    @Override
    public String toString() {
        return "CmdLog{" +
                "id=" + id +
                ", appUserId=" + appUserId +
                ", devSn='" + devSn + '\'' +
                ", cmd='" + cmd + '\'' +
                ", param1='" + param1 + '\'' +
                ", param2='" + param2 + '\'' +
                ", param3='" + param3 + '\'' +
                ", param4='" + param4 + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
