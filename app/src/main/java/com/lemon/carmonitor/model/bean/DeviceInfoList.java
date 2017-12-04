package com.lemon.carmonitor.model.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.bean]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/1/8 12:53]
 * 修改人:    [xflu]
 * 修改时间:  [2016/1/8 12:53]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceInfoList {
    private List<DeviceInfo> devList;

    public List<DeviceInfo> getDevList() {
        return devList;
    }

    public void setDevList(List<DeviceInfo> devList) {
        this.devList = devList;
    }

    @Override
    public String toString() {
        return "DeviceInfoList{" +
                "devList=" + devList +
                '}';
    }
}
