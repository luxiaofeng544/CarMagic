package com.lemon.event;

import com.baidu.location.BDLocation;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.event]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/1/9 15:07]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/1/9 15:07]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class CurrentLocationEvent {

    private BDLocation location;

    public BDLocation getLocation() {
        return location;
    }

    public void setLocation(BDLocation location) {
        this.location = location;
    }

    public CurrentLocationEvent(BDLocation location) {
        this.location = location;
    }
}
