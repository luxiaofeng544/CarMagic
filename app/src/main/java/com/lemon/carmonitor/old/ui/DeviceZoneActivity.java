package com.lemon.carmonitor.old.ui;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.lemon.LemonActivity;
import com.lemon.bean.BeanFactory;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.adapter.FenceListAdapter;
import com.lemon.carmonitor.event.BaiduFenceDeleteEvent;
import com.lemon.carmonitor.event.BaiduFenceQueryEvent;
import com.lemon.carmonitor.event.FenceDeleteEvent;
import com.lemon.carmonitor.event.FenceEditEvent;
import com.lemon.carmonitor.listener.GeoFenceListener;
import com.lemon.carmonitor.model.FenceModel;
import com.lemon.carmonitor.model.bean.DevFence;
import com.lemon.carmonitor.model.param.DelDevFenceParam;
import com.lemon.carmonitor.model.param.GetDevFencesParam;
import com.lemon.carmonitor.model.result.DelDevFenceResult;
import com.lemon.carmonitor.model.result.GetDevFencesResult;
import com.lemon.config.Config;
import com.lemon.util.ParamUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.ui]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2015/12/21 20:48]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2015/12/21 20:48]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class DeviceZoneActivity extends LemonActivity {
    ListView listView;
    GeoFenceListener geoFenceListener;

    @Override
    protected void setLayout() {
        setLayoutValue(R.layout.devicezone);
    }

    @Override
    protected void initView() {
        if(!checkDevice()){
            finish();
        }
        listView = findControl(R.id.devicezone_listView);
    }

    @Override
    protected void initData() {
        geoFenceListener = BeanFactory.getInstance().getBean(GeoFenceListener.class);
//        refresh(true);
    }

    private void refresh(boolean isShow){
        if(Config.getBooleanValue("fence_self")) {
            GetDevFencesParam param = new GetDevFencesParam();
            param.setLoginToken(cacheManager.getCurrentToken());
            param.setDevSn(cacheManager.getCurrentDevSn());
            param.setShowDialog(isShow);
            apiManager.getDevFences(param);
        }

        if(!Config.getBooleanValue("fence_baidu")){
            return;
        }
        lbsTraceClient.queryFenceList(Config.getServiceId(), cacheManager.getCurrentEntityName(), null, geoFenceListener);
    }

    @Override
    public void notificationMessage(Message msg) {
        if(msg.what == 1){
            List<FenceModel> models = (List<FenceModel>) msg.obj;
            listView.setAdapter(new FenceListAdapter(handler, mContext,models));
        }else if(msg.what ==2){
            List<FenceModel> models = (List<FenceModel>) msg.obj;
            listView.setAdapter(new FenceListAdapter(handler, mContext, models));
        }else if(msg.what == 3){
            toast(msg.obj.toString());
        }else if(msg.what == 0){
            toast("删除成功");
        }else if(msg.what == 4){
            toast("删除失败");
        }
    }

    public void addClick(View v){
        startNextActivity(AddFenceActivity.class, false);
    }

    public void onEventMainThread(BaiduFenceDeleteEvent event){
        if(event.getStatue() != 0){
            toast(event.getMessage());
        }else {
            toast("删除成功");
        }
    }

    public void onEventMainThread(BaiduFenceQueryEvent event){
        if(event.getStatue() != 0){
            toast(event.getMessage());
        }else {
            List<FenceModel> models = event.getModels();
            listView.setAdapter(new FenceListAdapter(handler, mContext,models));
        }
    }

    public void onEventMainThread(GetDevFencesResult event){
        List<DevFence> list = event.getRetData().getFenceList();
        Message message = handler.obtainMessage();
        if(!ParamUtils.isEmpty(list)){
            List<FenceModel> models = new ArrayList<FenceModel>();
            FenceModel fenceModel;
            for(DevFence devFence:list){
                fenceModel = new FenceModel();
                fenceModel.setFenceId(devFence.getFenceId()+"");
                fenceModel.setFenceName(devFence.getFenceName());
                fenceModel.setFenceRadius(devFence.getRadius() + "");
                fenceModel.setLatitude(devFence.getLatitude());
                fenceModel.setLongitude(devFence.getLongitude());
                fenceModel.setValidTimes(devFence.getValidTimes());
                fenceModel.setValidDays(devFence.getValidDays());
                fenceModel.setType(devFence.getType() == 1 ? "危险区域" : "安全区域");
                models.add(fenceModel);
                cacheManager.putBean("fenceId:" + devFence.getFenceId(), fenceModel);
            }
            message.obj = models;
        }else {
            try {
                message.obj = daoManager.query(FenceModel.class,"devSn",cacheManager.getCurrentDevSn());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        message.what = 2;
        handler.sendMessage(message);
    }

    public void onEventMainThread(FenceEditEvent event){
        Intent intent = new Intent(mContext,AddFenceActivity.class);
        intent.putExtra("fenceId", Integer.parseInt(event.getFenceId()));
        intent.putExtra("update",true);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh(true);
    }

    public void onEventMainThread(FenceDeleteEvent event){

        int fenceId = Integer.valueOf(event.getFenceId());
        DelDevFenceParam param = new DelDevFenceParam();
        param.setDevSn(cacheManager.getCurrentDevSn());
        param.setLoginToken(cacheManager.getCurrentToken());
        param.setFenceId(fenceId);
        apiManager.delDevFence(param);
        lbsTraceClient.deleteFence(Config.getServiceId(), Integer.parseInt(event.getFenceId()), geoFenceListener);
    }

    public void onEventMainThread(DelDevFenceResult event){
        System.out.println(event.toString());
        refresh(false);
    }

}
