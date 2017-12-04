package com.lemon.carmonitor.model.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.param]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/1/25 16:01]
 * 修改人:    [xflu]
 * 修改时间:  [2016/1/25 16:01]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlarmLogList {
    private List<AlarmLog> logList;

    public List<AlarmLog> getLogList() {
        return logList;
    }

    public void setLogList(List<AlarmLog> logList) {
        this.logList = logList;
    }
}
