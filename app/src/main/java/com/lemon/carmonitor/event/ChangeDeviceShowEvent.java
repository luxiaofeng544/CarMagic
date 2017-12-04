package com.lemon.carmonitor.event;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.event]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/1/8 14:11]
 * 修改人:    [xflu]
 * 修改时间:  [2016/1/8 14:11]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class ChangeDeviceShowEvent {

    public ChangeDeviceShowEvent(String devName, String batteryVolt) {
        this.devName = devName;
        this.batteryVolt = batteryVolt;
    }

    String devName;
    String batteryVolt;

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getBatteryVolt() {
        return batteryVolt;
    }

    public void setBatteryVolt(String batteryVolt) {
        this.batteryVolt = batteryVolt;
    }

    @Override
    public String toString() {
        return "ChangeDeviceShowEvent{" +
                "devName='" + devName + '\'' +
                ", batteryVolt='" + batteryVolt + '\'' +
                '}';
    }
}
