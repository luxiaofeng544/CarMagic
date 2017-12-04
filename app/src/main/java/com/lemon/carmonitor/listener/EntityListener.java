package com.lemon.carmonitor.listener;


import com.baidu.trace.api.entity.OnEntityListener;
import com.lemon.annotation.Component;
import com.lemon.carmonitor.event.FindEntityEvent;
import com.lemon.carmonitor.trace.TrackData;
import com.lemon.event.ToastEvent;
import com.lemon.util.JSONUtils;
import com.lemon.util.ParamUtils;

import org.greenrobot.eventbus.EventBus;


/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.listener]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/2/15 15:59]
 * 修改人:    [xflu]
 * 修改时间:  [2016/2/15 15:59]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Component
public class EntityListener extends OnEntityListener {

    public String type;

    public void setType(String type) {
        this.type = type;
    }

    public void onRequestFailedCallback(String s) {

    }

    // 查询entity回调接口，返回查询结果列表
    public void onQueryEntityListCallback(String result) {
        System.out.println("entity回调接口消息 : " + result);
        TrackData traceData = JSONUtils.parseJson(result, TrackData.class);
        if (ParamUtils.isNull(traceData)) {
            EventBus.getDefault().post(new ToastEvent("未获取到车辆信息"));
            return;
        }

        if (traceData.getStatus().intValue() != 0) {
            EventBus.getDefault().post(new ToastEvent(traceData.getMessage()));
            return;
        }

        EventBus.getDefault().post(new FindEntityEvent(type, traceData));
    }
}
