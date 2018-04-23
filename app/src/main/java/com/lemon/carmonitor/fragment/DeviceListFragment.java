package com.lemon.carmonitor.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.lemon.LemonDaoManager;
import com.lemon.bean.BeanFactory;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.adapter.DeviceListAdapter;
import com.lemon.carmonitor.api.ApiManager;
import com.lemon.carmonitor.contant.StatusCode;
import com.lemon.carmonitor.db.Alarm;
import com.lemon.carmonitor.event.ReadAlarmEvent;
import com.lemon.carmonitor.event.UnbindDevEvent;
import com.lemon.carmonitor.manager.AlarmManager;
import com.lemon.carmonitor.model.bean.DeviceInfo;
import com.lemon.carmonitor.model.param.AppUserDelDevParam;
import com.lemon.carmonitor.model.param.GetUserDevsParam;
import com.lemon.carmonitor.model.result.AppUserDelDevResult;
import com.lemon.carmonitor.model.result.GetUserDevsResult;
import com.lemon.carmonitor.ui.AddDeviceActivity;
import com.lemon.carmonitor.util.AppCacheManager;
import com.lemon.util.LogUtils;
import com.lemon.util.ParamUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.fragment]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2015/12/23 9:24]
 * 修改人:    [xflu]
 * 修改时间:  [2015/12/23 9:24]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class DeviceListFragment extends Fragment {

    LayoutInflater inflater;
    private ListView listView;
    private Button button_all,button_online,button_offline,button_add,button_alarm,button_unbind;
    private List<DeviceInfo> allDeviceInfos,onLineDeviceInfos,offLineDeviceInfos,currentDevices;
    private ApiManager apiManager;
    private AppCacheManager cacheManager;
    private static final int all_type = 0;
    private static final int online_type = 1;
    private static final int offline_type = 2;
    private static final int alarm_type = 3;
    private static final int unbind_type = 4;
    private Activity activity;
    private Map<String,Alarm> alarmMap = new HashMap<>();
    private AlarmManager alarmManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        activity = getActivity();
        View rootView = inflater.inflate(R.layout.devicelist, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        button_all = (Button) rootView.findViewById(R.id.button_all);
        button_online = (Button) rootView.findViewById(R.id.button_online);
        button_offline = (Button) rootView.findViewById(R.id.button_offline);
        button_add = (Button) rootView.findViewById(R.id.button_add);
        button_alarm =  (Button) rootView.findViewById(R.id.button_alarm);
        button_unbind =  (Button) rootView.findViewById(R.id.button_unbind);

        button_all.setOnClickListener(onClickListener);
        button_unbind.setOnClickListener(onClickListener);
        button_online.setOnClickListener(onClickListener);
        button_offline.setOnClickListener(onClickListener);
        button_add.setOnClickListener(onClickListener);
        button_alarm.setOnClickListener(onClickListener);
        initData();
        return rootView;
    }

    private void initData() {
        EventBus.getDefault().register(this);
        apiManager = BeanFactory.getInstance().getBean(ApiManager.class);
        cacheManager= BeanFactory.getInstance().getBean(AppCacheManager.class);
        alarmManager = BeanFactory.getInstance().getBean(AlarmManager.class);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.button_all){
                show(allDeviceInfos,all_type);
            }else if(v.getId() == R.id.button_online){
                show(onLineDeviceInfos,online_type);
            }else if(v.getId() == R.id.button_offline){
                show(offLineDeviceInfos,offline_type);
            }else if(v.getId() == R.id.button_add){
                startActivity(new Intent(getActivity(), AddDeviceActivity.class));
            }else if(v.getId() == R.id.button_unbind){
                show(allDeviceInfos, unbind_type);
            }else if(v.getId() == R.id.button_alarm){
                loadAlarmDevices();
            }
        }
    };

    private void loadDevices(){
        GetUserDevsParam param = new GetUserDevsParam();
        param.setShowDialog(false);
        param.setLoginToken(cacheManager.getCurrentToken());
        apiManager.getUserDevs(param);
    }

    private void loadAlarmDevices(){
        alarmMap.clear();
        List<Alarm> list = BeanFactory.getInstance().getBean(LemonDaoManager.class).queryAllOrderBy(Alarm.class,"id",false);
        if(ParamUtils.isEmpty(list)){
            Message msg = handler.obtainMessage();
            msg.what = alarm_type;
            msg.obj = new ArrayList<DeviceInfo>();
            handler.sendMessage(msg);
            return;
        }

        for(Alarm alarm:list){
            if(alarm.getStatue().equals("-1") && !alarmMap.containsKey(alarm.getDevSn())){
                alarmMap.put(alarm.getDevSn(),alarm);
            }
        }

        List<DeviceInfo> alarmDevices = new ArrayList<>();
        for(DeviceInfo deviceInfo:allDeviceInfos){
            if(alarmMap.containsKey(deviceInfo.getDevSn())){
                alarmDevices.add(deviceInfo);
            }
        }

        Message msg = handler.obtainMessage();
        msg.obj = alarmDevices;
        msg.what = alarm_type;
        handler.sendMessage(msg);
    }

    private void categoryDevices(){
        if(ParamUtils.isEmpty(allDeviceInfos)){
            return;
        }

        onLineDeviceInfos = new ArrayList<>();
        offLineDeviceInfos = new ArrayList<>();
        for(DeviceInfo info:allDeviceInfos){
            if(info.getOnline().equals("1")){
                onLineDeviceInfos.add(info);
            }else {
                offLineDeviceInfos.add(info);
            }
        }
    }

    public void onEventMainThread(ReadAlarmEvent result){
        alarmManager.readAll(result.getDevSn());
        loadAlarmDevices();
    }

    public void onEventMainThread(UnbindDevEvent result){
        AppUserDelDevParam param = new AppUserDelDevParam();
        param.setLoginToken(cacheManager.getCurrentToken());
        param.setDevSn(result.getDevSn());
        apiManager.appUserDelDev(param);
    }

    public void onEventMainThread(AppUserDelDevResult result){
        if(result.getRetCode().equals(StatusCode.SUCCESS.getCode())){
            loadDevices();
        }
    }

    public void onEventMainThread(GetUserDevsResult result){
        if(result.getRetCode().equals(StatusCode.SUCCESS.getCode())){
            /*if(ParamUtils.isNull(result.getRetData())||ParamUtils.isEmpty(result.getRetData().getDevList())){
                LogUtils.toast(getActivity(),"未绑定设备");
                return;
            }*/
            allDeviceInfos = result.getRetData().getDevList();
            categoryDevices();
            show(allDeviceInfos,all_type);
        }else {
            LogUtils.toast(getActivity(),result.getRetMsg());
        }
    }

    private void show(List<DeviceInfo> devices, int type){
        currentDevices = devices;
        Message msg = handler.obtainMessage();
        msg.what = type;
        msg.obj = devices;
        handler.sendMessage(msg);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            List<DeviceInfo> models = (List<DeviceInfo>) msg.obj;
            if(ParamUtils.isNull(activity)){
                LogUtils.toast(getActivity(),"activity null");
                return;
            }
            listView.setAdapter(new DeviceListAdapter(handler, activity, ParamUtils.isNull(models)?new ArrayList<DeviceInfo>():models,msg.what));
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        loadDevices();
    }
}
