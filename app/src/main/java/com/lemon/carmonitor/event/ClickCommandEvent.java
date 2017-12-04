package com.lemon.carmonitor.event;

import com.lemon.carmonitor.model.bean.protocol.CmdsEntity;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.event]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/2/2 19:38]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/2/2 19:38]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class ClickCommandEvent {

    CmdsEntity model;

    public ClickCommandEvent(CmdsEntity model) {
        this.model = model;
    }

    public CmdsEntity getModel() {
        return model;
    }

    public void setModel(CmdsEntity model) {
        this.model = model;
    }
}

