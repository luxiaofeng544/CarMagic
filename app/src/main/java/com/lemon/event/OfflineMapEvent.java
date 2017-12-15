package com.lemon.event;

import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon.event]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/1/9 15:10]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/1/9 15:10]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class OfflineMapEvent {

    private ReverseGeoCodeResult item;

    public OfflineMapEvent(){

    }

    public OfflineMapEvent(ReverseGeoCodeResult item){
        this.item = item;
    }

    public ReverseGeoCodeResult getItem() {
        return item;
    }

    public void setItem(ReverseGeoCodeResult item) {
        this.item = item;
    }
}
