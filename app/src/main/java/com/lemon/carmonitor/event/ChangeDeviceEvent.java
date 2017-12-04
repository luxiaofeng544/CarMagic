package com.lemon.carmonitor.event;

import com.lemon.carmonitor.model.bean.DeviceInfo;

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
public class ChangeDeviceEvent {

    private DeviceInfo deviceInfo;
    public ChangeDeviceEvent(DeviceInfo deviceInfo){
        this.deviceInfo = deviceInfo;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
