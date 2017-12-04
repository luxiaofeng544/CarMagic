package com.lemon.carmonitor.model.bean;

import java.util.List;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.bean]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/1/30 23:18]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/1/30 23:18]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class FenceTotal {

    private List<Fence> fenceList;

    public List<Fence> getFenceList() {
        return fenceList;
    }

    public void setFenceList(List<Fence> fenceList) {
        this.fenceList = fenceList;
    }
}
