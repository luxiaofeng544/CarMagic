package com.lemon.carmonitor.old.ui;

import android.view.View;

import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.OnStartTraceListener;
import com.baidu.trace.OnStopTraceListener;
import com.baidu.trace.Trace;
import com.lemon.LemonActivity;
import com.lemon.carmonitor.R;
import com.lemon.util.LogUtils;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.ui]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2015/12/21 20:54]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2015/12/21 20:54]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class MonitoringActivity extends LemonActivity {

    LBSTraceClient client;
    Trace trace;

    @Override
    protected void setLayout() {
        setLayoutValue(R.layout.monitoring);
    }


    public void start(View v) {
        //实例化轨迹服务客户端
        client = new LBSTraceClient(getApplicationContext());
        //鹰眼服务ID
        long serviceId = 108040;
        //entity标识
        String entityName = "手机13850046527";
        //轨迹服务类型（0 : 不上传位置数据，也不接收报警信息； 1 : 不上传位置数据，但接收报警信息；2 : 上传位置数据，且接收报警信息）
        int traceType = 2;
        //实例化轨迹服务
        trace = new Trace(getApplicationContext(), serviceId, entityName, traceType);
        //实例化开启轨迹服务回调接口
        OnStartTraceListener startTraceListener = new OnStartTraceListener() {
            //开启轨迹服务回调接口（arg0 : 消息编码，arg1 : 消息内容，详情查看类参考）
            @Override
            public void onTraceCallback(int arg0, String arg1) {
                LogUtils.e("startTraceListener onTraceCallback:"+arg1);
            }

            //轨迹服务推送接口（用于接收服务端推送消息，arg0 : 消息类型，arg1 : 消息内容，详情查看类参考）
            @Override
            public void onTracePushCallback(byte arg0, String arg1) {
                LogUtils.e("startTraceListener onTracePushCallback:"+arg1);
            }
        };
        //位置采集周期
        int gatherInterval = 5;
        //打包周期
        int packInterval = 30;
        //设置位置采集和打包周期
        client.setInterval(gatherInterval, packInterval);
        //开启轨迹服务
        client.startTrace(trace, startTraceListener);
        LogUtils.toast(mContext,"start");
    }

    public void stop(View v) {
        //实例化停止轨迹服务回调接口
        OnStopTraceListener stopTraceListener = new OnStopTraceListener() {
            // 轨迹服务停止成功
            @Override
            public void onStopTraceSuccess() {
                LogUtils.e("stopTraceListener onStopTraceSuccess:success");
            }

            // 轨迹服务停止失败（arg0 : 错误编码，arg1 : 消息内容，详情查看类参考）
            @Override
            public void onStopTraceFailed(int arg0, String arg1) {
                LogUtils.e("stopTraceListener onStopTraceFailed:"+arg1);
            }
        };

        //停止轨迹服务
        client.stopTrace(trace, stopTraceListener);
        LogUtils.toast(mContext, "stop");
    }
}
