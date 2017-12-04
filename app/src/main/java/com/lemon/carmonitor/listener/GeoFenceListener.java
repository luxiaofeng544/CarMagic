package com.lemon.carmonitor.listener;

import com.baidu.trace.api.fence.CreateFenceResponse;
import com.baidu.trace.api.fence.DeleteFenceResponse;
import com.baidu.trace.api.fence.FenceListResponse;
import com.baidu.trace.api.fence.HistoryAlarmResponse;
import com.baidu.trace.api.fence.MonitoredStatusByLocationResponse;
import com.baidu.trace.api.fence.MonitoredStatusResponse;
import com.baidu.trace.api.fence.OnFenceListener;
import com.baidu.trace.api.fence.UpdateFenceResponse;
import com.lemon.annotation.Component;
import com.lemon.bean.BeanFactory;
import com.lemon.carmonitor.event.BaiduFenceAddEvent;
import com.lemon.carmonitor.event.BaiduFenceDeleteEvent;
import com.lemon.carmonitor.event.BaiduFenceQueryEvent;
import com.lemon.carmonitor.event.BaiduFenceUpdateEvent;
import com.lemon.carmonitor.model.FenceModel;
import com.lemon.carmonitor.model.bean.FenceList;
import com.lemon.carmonitor.util.AppCacheManager;
import com.lemon.util.JSONUtils;
import com.lemon.util.ParamUtils;

import org.codehaus.jackson.map.ObjectMapper;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.listener]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/1/25 23:03]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/1/25 23:03]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Component
public class GeoFenceListener implements OnFenceListener {

    public void onQueryFenceListCallback(String s) {
        System.out.println("+++++++++++++++++++++++++++++GeoFenceListener  onQueryFenceListCallback+++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(s);
        System.out.println("+++++++++++++++++++++++++++++GeoFenceListener  onQueryFenceListCallback+++++++++++++++++++++++++++++++++++++++++++");

        try {
            FenceList fenceList = (new ObjectMapper()).readValue(s, FenceList.class);
            if (ParamUtils.isNull(fenceList)) {
                return;
            }

            BaiduFenceQueryEvent fenceQueryEvent = new BaiduFenceQueryEvent();
            fenceQueryEvent.setStatue(fenceList.getStatus());
            if (fenceList.getStatus() == 0) {
                List<FenceModel> models = new ArrayList<FenceModel>();
                if (ParamUtils.isEmpty(fenceList.getFences())) {
                    fenceQueryEvent.setModels(models);
                } else {
                    FenceModel fenceModel;
                    for (FenceList.FencesEntity entity : fenceList.getFences()) {
                        fenceModel = new FenceModel();
                        fenceModel.setFenceId(entity.getFence_id() + "");
                        fenceModel.setFenceName(entity.getName());
                        fenceModel.setFenceRadius(entity.getRadius() + "");
                        fenceModel.setLatitude(entity.getCenter().getLatitude());
                        fenceModel.setLongitude(entity.getCenter().getLongitude());
                        fenceModel.setType(entity.getCoord_type() == 1 ? "危险区域" : "安全区域");
                        models.add(fenceModel);
                        AppCacheManager cacheManager = BeanFactory.getInstance().getBean(AppCacheManager.class);
                        cacheManager.putBean("fenceId:"+entity.getFence_id(),fenceModel);
                    }
                    fenceQueryEvent.setModels(models);
                }
            } else {
                fenceQueryEvent.setMessage(fenceList.getMessage());
            }
            EventBus.getDefault().post(fenceQueryEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onRequestFailedCallback(String s) {
        System.out.println("+++++++++++++++++++++++++++++GeoFenceListener  onRequestFailedCallback+++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(s);
        System.out.println("+++++++++++++++++++++++++++++GeoFenceListener  onRequestFailedCallback+++++++++++++++++++++++++++++++++++++++++++");
    }

    public void onCreateCircularFenceCallback(String s) {
        System.out.println("+++++++++++++++++++++++++++++GeoFenceListener  onCreateCircularFenceCallback+++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(s);
        System.out.println("+++++++++++++++++++++++++++++GeoFenceListener  onCreateCircularFenceCallback+++++++++++++++++++++++++++++++++++++++++++");
        int status = JSONUtils.getInt(s, "status", -1);

        BaiduFenceAddEvent fenceAddEvent = new BaiduFenceAddEvent();
        fenceAddEvent.setStatue(status);
        if(status != 0){
            fenceAddEvent.setMessage(JSONUtils.getString(s,"message","保存失败"));
        }else
        {
            fenceAddEvent.setFenceId(JSONUtils.getInt(s,"fence_id",-1)+"");
            fenceAddEvent.setFenceName(JSONUtils.getString(s, "fence_name", ""));
        }
        EventBus.getDefault().post(fenceAddEvent);
    }

    public void onUpdateCircularFenceCallback(String s) {
        System.out.println("+++++++++++++++++++++++++++++GeoFenceListener  onUpdateCircularFenceCallback+++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(s);
        System.out.println("+++++++++++++++++++++++++++++GeoFenceListener  onUpdateCircularFenceCallback+++++++++++++++++++++++++++++++++++++++++++");

        int status = JSONUtils.getInt(s, "status", -1);
        BaiduFenceUpdateEvent fenceUpdateEvent = new BaiduFenceUpdateEvent();
        fenceUpdateEvent.setStatue(status);
        if (status != 0) {
            fenceUpdateEvent.setMessage(JSONUtils.getString(s, "message", "更新失败"));
        } else {
            fenceUpdateEvent.setFenceId(JSONUtils.getInt(s, "fence_id", -1) + "");
        }
        EventBus.getDefault().post(fenceUpdateEvent);
    }

    public void onDeleteFenceCallback(String s) {
        System.out.println("+++++++++++++++++++++++++++++GeoFenceListener  onDeleteFenceCallback+++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(s);
        System.out.println("+++++++++++++++++++++++++++++GeoFenceListener  onDeleteFenceCallback+++++++++++++++++++++++++++++++++++++++++++");
        int status = JSONUtils.getInt(s, "status", -1);
        BaiduFenceDeleteEvent fenceDelEvent = new BaiduFenceDeleteEvent();
        fenceDelEvent.setStatue(status);
        if (status != 0) {
            fenceDelEvent.setMessage(JSONUtils.getString(s, "message", "删除失败"));
        } else {
            fenceDelEvent.setFenceId(JSONUtils.getInt(s, "fence_id", -1) + "");
            fenceDelEvent.setFenceName(JSONUtils.getString(s, "fence_name", ""));
        }
        EventBus.getDefault().post(fenceDelEvent);
    }

    public void onQueryMonitoredStatusCallback(String s) {
        System.out.println("+++++++++++++++++++++++++++++GeoFenceListener  onQueryMonitoredStatusCallback+++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(s);
        System.out.println("+++++++++++++++++++++++++++++GeoFenceListener  onQueryMonitoredStatusCallback+++++++++++++++++++++++++++++++++++++++++++");

        JSONObject dataJson = null;
        try {
            dataJson = new JSONObject(s);
            int status = dataJson.getInt("status");
            if (0 == status) {
                int size = dataJson.getInt("size");
                if (size >= 1) {
                    JSONArray jsonArray = dataJson.getJSONArray("monitored_person_statuses");
                    JSONObject jsonObj = jsonArray.getJSONObject(0);
                    String mPerson = jsonObj.getString("monitored_person");
                    int mStatus = jsonObj.getInt("monitored_status");
                    if (1 == mStatus) {
//                        showMessage("监控对象[ " + mPerson + " ]在围栏内");
                    } else {
//                        showMessage("监控对象[ " + mPerson + " ]在围栏外");
                    }
                }
            } else {
//                showMessage("查询监控对象状态回调消息 : " + s);
            }
        } catch (JSONException e) {
//            showMessage("解析查询监控对象状态回调消息失败");
        }
    }

    public void onQueryHistoryAlarmCallback(String s) {
        System.out.println("+++++++++++++++++++++++++++++GeoFenceListener  onQueryHistoryAlarmCallback+++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(s);
        System.out.println("+++++++++++++++++++++++++++++GeoFenceListener  onQueryHistoryAlarmCallback+++++++++++++++++++++++++++++++++++++++++++");

    }

    public void onDelayAlarmCallback(String s) {
        System.out.println("+++++++++++++++++++++++++++++GeoFenceListener  onDelayAlarmCallback+++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(s);
        System.out.println("+++++++++++++++++++++++++++++GeoFenceListener  onDelayAlarmCallback+++++++++++++++++++++++++++++++++++++++++++");

    }

    @Override
    public void onCreateFenceCallback(CreateFenceResponse createFenceResponse) {

    }

    @Override
    public void onUpdateFenceCallback(UpdateFenceResponse updateFenceResponse) {

    }

    @Override
    public void onDeleteFenceCallback(DeleteFenceResponse deleteFenceResponse) {

    }

    @Override
    public void onFenceListCallback(FenceListResponse fenceListResponse) {

    }

    @Override
    public void onMonitoredStatusCallback(MonitoredStatusResponse monitoredStatusResponse) {

    }

    @Override
    public void onMonitoredStatusByLocationCallback(MonitoredStatusByLocationResponse monitoredStatusByLocationResponse) {

    }

    @Override
    public void onHistoryAlarmCallback(HistoryAlarmResponse historyAlarmResponse) {

    }
}
