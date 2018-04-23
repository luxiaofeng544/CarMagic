package com.lemon.carmonitor.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.lemon.LemonActivity;
import com.lemon.LemonDaoManager;
import com.lemon.bean.BeanFactory;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.baiduoffline.BaiduOfflineMapActivity;
import com.lemon.carmonitor.event.ChangeDeviceEvent;
import com.lemon.carmonitor.event.ChangeDeviceShowEvent;
import com.lemon.carmonitor.model.bean.DeviceInfo;
import com.lemon.carmonitor.old.ui.AlarmsetActivity;
import com.lemon.carmonitor.old.ui.DeviceInfoActivity;
import com.lemon.carmonitor.old.ui.CommandCategoryActivity;
import com.lemon.carmonitor.old.ui.DeviceHistoryActivity;
import com.lemon.carmonitor.old.ui.DeviceMessageActivity;
import com.lemon.carmonitor.old.ui.DeviceTrackingActivity;
import com.lemon.carmonitor.old.ui.DeviceZoneActivity;
import com.lemon.carmonitor.old.ui.HelpActivity;
import com.lemon.carmonitor.old.ui.NavigationActivity;
import com.lemon.carmonitor.old.ui.NewLoginActivity;
import com.lemon.carmonitor.old.ui.RechargeActivity;
import com.lemon.carmonitor.old.ui.WeixinActivity;
import com.lemon.carmonitor.util.AppCacheManager;
import com.lemon.config.Config;
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
public class HomeFragment extends Fragment {

    LayoutInflater inflater;
    private GridView list_found;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    // 图片封装为一个数组
    /*private int[] icon = {R.drawable.tracking, R.drawable.history,
            R.drawable.electronic, R.drawable.command,R.drawable.device,
            R.drawable.message, R.drawable.navi,
            R.drawable.user, R.drawable.user, R.drawable.offlinemap,
            R.drawable.recharge, R.drawable.logout};
    private String[] iconName = {"实时跟踪", "历史轨迹", "电子围栏", "设置", "设备信息", "设备消息", "周边导航",
            "帮助中心", "客服", "离线地图", "支付", "注销"};*/
    private int[] icon = {R.drawable.tracking, R.drawable.history,
            R.drawable.electronic, R.drawable.command, R.drawable.message, R.drawable.device,
            R.drawable.message, R.drawable.navi, R.drawable.offlinemap,
            R.drawable.recharge, R.drawable.logout};
    private String[] iconName = {"实时跟踪", "历史轨迹", "电子围栏", "设置", "报警设置", "设备信息", "设备消息", "周边导航", "离线地图", "支付", "注销"};
    private TextView home_tv_title;
    private AppCacheManager cacheManager;
    private LemonDaoManager lemonDaoManager;
    private int title_length;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        View rootView = inflater.inflate(R.layout.home, container, false);
        home_tv_title = (TextView) rootView.findViewById(R.id.home_tv_title);
        list_found = (GridView) rootView.findViewById(R.id.list_found);
        //新建List
        data_list = new ArrayList<Map<String, Object>>();
        //获取数据
        getData();
        //新建适配器
        String[] from = {"image", "text"};
        int[] to = {R.id.iv_item, R.id.tv_item};
        sim_adapter = new SimpleAdapter(getActivity(), data_list, R.layout.found_list_item, from, to);
        //配置适配器
        list_found.setAdapter(sim_adapter);
        list_found.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleClick(position);
            }
        });
        initData();
        return rootView;
    }

    private void initData() {
        EventBus.getDefault().register(this);
        cacheManager = BeanFactory.getInstance().getBean(AppCacheManager.class);
        lemonDaoManager = BeanFactory.getInstance().getBean(LemonDaoManager.class);

        title_length = Config.getIntValue("titlelength");
        if(!ParamUtils.isEmpty(cacheManager.getCurrentDevSn())){
            DeviceInfo currentDevice = (DeviceInfo) cacheManager.getBean(cacheManager.getCurrentDevSn());
            if (!ParamUtils.isNull(currentDevice)) {
                String deviceName = currentDevice.getDevName();
                deviceName = deviceName.length() <= title_length ? deviceName : deviceName.substring(0, title_length) + "...";
                String title = deviceName + " 电量" + currentDevice.getBatteryVolt() + "%";
                home_tv_title.setText(title);
            }
        }
    }

    public void onEventMainThread(ChangeDeviceEvent event){
        if(!ParamUtils.isNull(event)){
            String deviceName = event.getDeviceInfo().getDevName();
            deviceName = deviceName.length()<=title_length?deviceName:deviceName.substring(0,title_length)+"...";
            String title = deviceName+" 电量"+event.getDeviceInfo().getBatteryVolt()+"%";
            home_tv_title.setText(title);
        }else {
            home_tv_title.setText("首页");
            LogUtils.toast(getActivity(),"选择设备失败,请重新尝试");
        }
    }

    public void onEventMainThread(ChangeDeviceShowEvent event){
        if(!ParamUtils.isNull(event)){
            String deviceName = event.getDevName();
            deviceName = deviceName.length()<=title_length?deviceName:deviceName.substring(0,title_length)+"...";
            String title = deviceName+" 电量"+event.getBatteryVolt()+"%";
            home_tv_title.setText(title);
        }
    }

    public List<Map<String, Object>> getData() {
        //cion和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }
        return data_list;
    }

    private void handleClick(int position){
        switch (position){
            case 0:
                trackingClick();
                break;
            case 1:
                historyClick();
                break;
            case 2:
                electronicClick();
                break;
            case 3:
                deviceSettingClick();
                break;
            case 4:
                alarmsetClick();
                break;
            case 5:
                deviceInfoClick();
                break;
            case 6:
                deviceMessageClick();
                break;
            case 7:
                navigationClick();
                break;
            case 8:
                offlineClick();
                break;
            case 9:
                rechargeClick();
                break;
            case 10:
                logoutClick();
                break;
        }

    }


    public void trackingClick() {
        ((LemonActivity) getActivity()).startNextActivity(DeviceTrackingActivity.class, false);
    }

    public void historyClick() {
        ((LemonActivity) getActivity()).startNextActivity(DeviceHistoryActivity.class, false);
    }

    public void electronicClick() {
        ((LemonActivity) getActivity()).startNextActivity(DeviceZoneActivity.class, false);
    }

    public void deviceSettingClick() {
        Intent intent = new Intent(getActivity(),CommandCategoryActivity.class);
        intent.putExtra("type","gprs");
        ((LemonActivity) getActivity()).startNextActivity(intent, false);
    }

    public void alarmsetClick() {
        ((LemonActivity) getActivity()).startNextActivity(AlarmsetActivity.class, false);
    }

    public void deviceInfoClick() {
        ((LemonActivity) getActivity()).startNextActivity(DeviceInfoActivity.class, false);
    }

    public void deviceMessageClick() {
        ((LemonActivity) getActivity()).startNextActivity(DeviceMessageActivity.class, false);
    }

    public void navigationClick() {
        ((LemonActivity) getActivity()).startNextActivity(NavigationActivity.class, false);
    }

    public void helpClick() {
        ((LemonActivity) getActivity()).startNextActivity(HelpActivity.class, false);
    }

    public void weixinClick() {
        ((LemonActivity) getActivity()).startNextActivity(WeixinActivity.class, false);
    }

    public void offlineClick() {
        ((LemonActivity) getActivity()).startNextActivity(BaiduOfflineMapActivity.class, false);
    }

    public void rechargeClick() {
        ((LemonActivity) getActivity()).startNextActivity(RechargeActivity.class, false);
    }

    public void logoutClick() {
        cacheManager.cleanCache();
        Intent intent = new Intent(getActivity(),NewLoginActivity.class);
        intent.putExtra("type","logout");
        ((LemonActivity) getActivity()).startNextActivity(intent, true);
    }

}
