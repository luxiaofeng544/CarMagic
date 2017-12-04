package com.lemon.carmonitor.event;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.event]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/1/24 22:03]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/1/24 22:03]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class BaiduFenceDeleteEvent {

    private int statue;
    private String message;
    private String fenceId;
    private String fenceName;

    public int getStatue() {
        return statue;
    }

    public void setStatue(int statue) {
        this.statue = statue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFenceName() {
        return fenceName;
    }

    public void setFenceName(String fenceName) {
        this.fenceName = fenceName;
    }

    public String getFenceId() {
        return fenceId;
    }

    public void setFenceId(String fenceId) {
        this.fenceId = fenceId;
    }

}
