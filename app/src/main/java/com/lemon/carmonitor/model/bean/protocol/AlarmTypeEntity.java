package com.lemon.carmonitor.model.bean.protocol;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.bean.protocol]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/2/1 22:34]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/2/1 22:34]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class AlarmTypeEntity {
    private String name;
    private String type;
    private String status;

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }
}
