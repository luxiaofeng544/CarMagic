package com.lemon.carmonitor.event;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.event]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/1/24 22:01]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/1/24 22:01]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class FenceDeleteEvent {


    private String fenceId;

    public FenceDeleteEvent(String fenceId) {
        this.fenceId = fenceId;
    }


    public String getFenceId() {
        return fenceId;
    }

    public void setFenceId(String fenceId) {
        this.fenceId = fenceId;
    }
}
