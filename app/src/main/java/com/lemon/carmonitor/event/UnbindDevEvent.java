package com.lemon.carmonitor.event;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.event]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/2/20 13:55]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/2/20 13:55]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class UnbindDevEvent {
    private String devSn;

    public UnbindDevEvent(String devSn) {
        this.devSn = devSn;
    }

    public String getDevSn() {
        return devSn;
    }

    public void setDevSn(String devSn) {
        this.devSn = devSn;
    }
}
