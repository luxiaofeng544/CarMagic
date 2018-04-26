package com.lemon.carmonitor.fragment;

import android.graphics.Point;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.api.entity.OnEntityListener;
import com.lemon.LemonFragment;
import com.lemon.LemonLocation;
import com.lemon.bean.BeanFactory;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.contant.StatusCode;
import com.lemon.carmonitor.event.ChangeDeviceEvent;
import com.lemon.carmonitor.event.ChangeViewEvent;
import com.lemon.carmonitor.event.FindEntityEvent;
import com.lemon.carmonitor.event.WakeUpEvent;
import com.lemon.carmonitor.listener.EntityListener;
import com.lemon.carmonitor.model.bean.DeviceInfo;
import com.lemon.carmonitor.model.param.GetUserDevsParam;
import com.lemon.carmonitor.model.param.QueryEntityListParam;
import com.lemon.carmonitor.model.result.QueryEntityListResult;
import com.lemon.carmonitor.trace.Entities;
import com.lemon.carmonitor.trace.TrackData;
import com.lemon.carmonitor.util.AppCacheManager;
import com.lemon.config.Config;
import com.lemon.event.CurrentLocationEvent;
import com.lemon.event.StartLocationEvent;
import com.lemon.event.ToastEvent;
import com.lemon.util.ParamUtils;
import com.lemon.util.TimeUtil;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.fragment]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2015/12/23 9:26]
 * 修改人:    [xflu]
 * 修改时间:  [2015/12/23 9:26]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class TrackingFragment extends LemonFragment {

    BaiduMap mBaiduMap = null;
    MapView mMapView = null;
    LBSTraceClient lbsTraceClient;
    EntityListener entityListener;
    Button monitoring_button_refresh,button_location;
    int serviceId;
    CheckBox checkBox_maptype;
    TextView textView_timeout,textView_distance;
    AppCacheManager appCacheManager;
    Map<String,String> locationMap = new HashMap<>();
    Map<LatLng,String> entityNameMap = new HashMap<>();
    Map<String,Entities> entityMap = new HashMap<>();
    private TrackData currentTrackData;
    private View viewPop;
    private TextView tvPop;
    private String firstEntityName;
    private LatLng firstLatLng;
    int devices_refresh = 120*1000;

    @Override
    protected void setLayout() {
        this.layout = R.layout.devicetracking;
    }

    @Override
    protected void initView() {
        mMapView = (MapView) rootView.findViewById(R.id.bmapsView);
        checkBox_maptype = (CheckBox) rootView.findViewById(R.id.checkBox_maptype);
        button_location = (Button) rootView.findViewById(R.id.button_location);
        button_location.setVisibility(View.GONE);
        mBaiduMap = mMapView.getMap();
        monitoring_button_refresh = (Button) rootView.findViewById(R.id.monitoring_button_refresh);
        monitoring_button_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceLocations();
            }
        });

        Button button_carandperson = (Button) rootView.findViewById(R.id.button_carandperson);
        button_carandperson.setVisibility(View.GONE);
        Button button_activate = (Button) rootView.findViewById(R.id.button_activate);
        button_activate.setVisibility(View.GONE);

        textView_timeout = (TextView) rootView.findViewById(R.id.textView_timeout);
        textView_timeout.setVisibility(View.GONE);
        viewPop = getActivity().getLayoutInflater().inflate(R.layout.pop_view, null);
        tvPop = (TextView) viewPop.findViewById(R.id.textcache);
        textView_distance = (TextView) rootView.findViewById(R.id.textView_distance);
        textView_distance.setVisibility(View.GONE);

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                if(!ParamUtils.isNull(myLocation) && (marker.getPosition().latitude==myLocation.getLatitude() || marker.getPosition().longitude==myLocation.getLongitude())){
                    return false;
                }
                LatLng ll = marker.getPosition();
                showPopView(entityNameMap.get(ll),marker.getPosition());
                return false;
            }
        });

        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {

            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                return false;
            }

            @Override
            public void onMapClick(LatLng arg0) {
                mBaiduMap.hideInfoWindow();
            }
        });

        button_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        checkBox_maptype.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                } else {
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                }
            }
        });
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, null));
        handler.sendEmptyMessageDelayed(5,1000);
        handler.sendEmptyMessageDelayed(7, 3000);
    }

    protected void initData() {
        EventBus.getDefault().register(this);
        appCacheManager = BeanFactory.getInstance().getBean(AppCacheManager.class);
        lbsTraceClient = BeanFactory.getInstance().getBean(LBSTraceClient.class);
        entityListener = BeanFactory.getInstance().getBean(EntityListener.class);
        handler.sendEmptyMessage(9);
        serviceId = Config.getServiceId();
        devices_refresh = Config.getIntValue("devices_refresh")*1000;
        handler.sendEmptyMessageDelayed(10, 10000);
    }

    public void getDeviceLocations() {
        mBaiduMap.clear();
        String columnKey = "";
        int returnType = 0;
        int activeTime = (int) (System.currentTimeMillis() / 1000 - 12 * 30 * 24 * 60 * 60);
        int pageSize = 1000;
        int pageIndex = 1;
        String entityNames = getEntityNames();
        if (ParamUtils.isEmpty(entityNames)) {
            return;
        }

        queryEntityListByYingyan(serviceId,entityNames,columnKey,returnType,activeTime,pageSize,pageIndex);
        if(isUseYingyanService()){
            queryEntityListByYingyan(serviceId,entityNames,columnKey,returnType,activeTime,pageSize,pageIndex);
            return;
        }

        queryEntityListByServer(serviceId, entityNames, columnKey, returnType, activeTime, pageSize, pageIndex);
    }

    private void queryEntityListByYingyan(int serviceId, String entityName, String columnKey, int returnType, int activeTime, int pageSize, int pageIndex){
        entityListener.setType("trackCenter");
        lbsTraceClient.queryEntityList(serviceId, entityName, columnKey, returnType, activeTime, pageSize, pageIndex, entityListener);
        //查询实时轨迹
        OnEntityListener entityListener = new OnEntityListener() {
            // 查询失败回调接口
            @Override
            public void onRequestFailedCallback(String arg0) {
                System.out.println("entity请求失败回调接口消息 : " + arg0);
            }

            // 查询entity回调接口，返回查询结果列表
            @Override
            public void onQueryEntityListCallback(String arg0) {
                System.out.println("entity回调接口消息 : " + arg0);
            }
        };

        lbsTraceClient.queryEntityList(serviceId, entityName, columnKey, returnType, activeTime, pageSize,
                pageIndex, entityListener);
    }

    private void queryEntityListByServer(int serviceId, String entityName, String columnKey, int returnType, int activeTime, int pageSize, int pageIndex){
        QueryEntityListParam param = new QueryEntityListParam();
        param.setLoginToken(cacheManager.getCurrentToken());
        param.setServiceId(serviceId);
        param.setEntityName(entityName);
        param.setColumnKey(columnKey);
        param.setReturnType(returnType);
        param.setActiveTime(activeTime);
        param.setPageSize(pageSize);
        param.setPageIndex(pageIndex);
        apiManager.queryEntityList(param);
    }

    public void onEventMainThread(QueryEntityListResult result){
        if(result.getRetCode().equals(StatusCode.SUCCESS.getCode())){
            currentTrackData = result.getRetData();
            carLocation(currentTrackData);
        }
    }

    private String getEntityNames() {
        List<DeviceInfo> deviceInfos = (List<DeviceInfo>) appCacheManager.getBean("deviceLists");
        if(ParamUtils.isEmpty(deviceInfos)){
//            toast("no devices");
            return "";
        }
        String strDevices = "";
        for (DeviceInfo deviceInfo : deviceInfos) {
            if(!ParamUtils.isEmpty(deviceInfo.getTraceEntityName())){
                strDevices += deviceInfo.getTraceEntityName() + ",";
            }
        }
        if (strDevices.endsWith(",")) {
            strDevices = strDevices.substring(0, strDevices.length() - 1);
        }

        return strDevices;
    }

    @Override
    public void notificationMessage(Message msg) {
        switch (msg.what){
            case 0:
                toast("未获取到车辆信息");
                break;
            case 1:
                toast(((TrackData)msg.obj).getMessage());
                break;
            case 2:
                carLocation((TrackData) msg.obj);
                break;
            case 3:
                toast("无法获取到位置信息");
                break;
            case 4:
                break;
            case 5:
                getDeviceLocations();
                break;
            case 6 :
                showPopView(firstEntityName, firstLatLng);
                break;
            case 7 :
                showLocation();
                break;
            case 9:
                EventBus.getDefault().post(new StartLocationEvent());
                break;
            case 10:
                getDevInfo();
                getDeviceLocations();
                handler.sendEmptyMessageDelayed(10,devices_refresh);
                return;
        }
    }


    private void getDevInfo(){
        GetUserDevsParam param = new GetUserDevsParam();
        param.setLoginToken(cacheManager.getCurrentToken());
        apiManager.getUserDevs(param);
    }

    public void onEventMainThread(FindEntityEvent event){
        if(!event.getType().equals("trackCenter")){
            return;
        }
        currentTrackData = event.getTrackData();
        carLocation(event.getTrackData());
    }

    private void carLocation(TrackData trackData){
        if(ParamUtils.isEmpty(trackData.getEntities())){
            toast("未获取到车辆信息,该车辆最近一个月未有上传记录");
            return;
        }

        LatLng ll = null;
        String entityName = "";
        entityMap.clear();
        for(Entities entity :trackData.getEntities()){
            ll = new LatLng(Double.parseDouble(entity.getRealtime_point().getLocation()[1]), Double.parseDouble(entity.getRealtime_point().getLocation()[0]));
            entityName = entity.getEntity_name();
            findLocation(entityName,ll);
            MarkerOptions marker = new MarkerOptions();
            String devSn = ParamUtils.isEmpty(entity.getDEV_SN())?entityName:entity.getDEV_SN();
            float direction = entity.getRealtime_point().getDirection().floatValue() -90;
            if(cacheManager.containBean(devSn)){
                DeviceInfo deviceInfo = (DeviceInfo) cacheManager.getBean(devSn);
                if(deviceInfo.getOnline().equals("1")){
                    if(entity.getRealtime_point().getSpeed().doubleValue()>0){
                        mBaiduMap.addOverlay(marker.position(ll).icon(BitmapDescriptorFactory.fromResource(R.drawable.driving_0)).rotate(direction));
                    }else {
                        mBaiduMap.addOverlay(marker.position(ll).icon(BitmapDescriptorFactory.fromResource(R.drawable.online_0)).rotate(direction));
                    }
                }else {
                    mBaiduMap.addOverlay(marker.position(ll).icon(BitmapDescriptorFactory.fromResource(R.drawable.offline_0)).rotate(direction));
                }
            } else {
                mBaiduMap.addOverlay(marker.position(ll).icon(BitmapDescriptorFactory.fromResource(R.drawable.offline_0)).rotate(direction));
            }
            entityNameMap.put(ll, entityName);
            entityMap.put(entityName, entity);
        }
        if(ParamUtils.isNull(ll)){
            return;
        }
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
        mBaiduMap.animateMapStatus(u);

        firstEntityName = entityName;
        firstLatLng = ll;
        handler.sendEmptyMessageDelayed(6, 2000);
    }

    private void showPopView(String entityName, LatLng ll){
        tvPop.setText(makeInfo(entityName));
        tvPop.setTag(entityName);
        tvPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tag = (v).getTag().toString();
                final Entities entity = entityMap.get(tag);
                final String devSn = ParamUtils.isEmpty(entity.getDEV_SN())?entity.getEntity_name():entity.getDEV_SN();
                if (!cacheManager.containBean(devSn)) {
                    return;
                }

                final DeviceInfo model = (DeviceInfo) cacheManager.getBean(devSn);
                new MaterialDialog.Builder(getActivity())
                        .content("选择了'" + model.getDevName() + "',是否切换设备")
                        .neutralText("激活")
                        .positiveText("切换")
                        .negativeText("取消")
                        .titleColorRes(R.color.black)
                        .contentColorRes(R.color.black)
                        .backgroundColorRes(R.color.white)
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                EventBus.getDefault().post(new WakeUpEvent(model.getDevSn()));
                            }
                        })
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                //save current select device sn
                                BeanFactory.getInstance().getBean(AppCacheManager.class).setCurrentDevSn(devSn);
                                BeanFactory.getInstance().getBean(AppCacheManager.class).setCurrentEntityName(tag);
                                //send event
                                EventBus.getDefault().post(new ChangeDeviceEvent(model));
                                EventBus.getDefault().post(new ChangeViewEvent(0));
                            }
                        }).show();

            }
        });
        if(ParamUtils.isNull(mBaiduMap.getProjection())){
            return;
        }
        Point p = mBaiduMap.getProjection().toScreenLocation(ll);
        p.y -= 47;
        LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);
        final InfoWindow mInfoWindow = new InfoWindow(viewPop, llInfo, 1);
        MapStatusUpdate m = MapStatusUpdateFactory.newLatLng(ll);
        mBaiduMap.setMapStatus(m);
        mBaiduMap.showInfoWindow(mInfoWindow);
    }

    private String makeInfo(String entityName){
        Entities entity = entityMap.get(entityName);
        final String devSn = ParamUtils.isEmpty(entity.getDEV_SN())?entity.getEntity_name():entity.getDEV_SN();
        DeviceInfo deviceInfo = (DeviceInfo) cacheManager.getBean(devSn);
        if(ParamUtils.isNull(deviceInfo)){
            return  "未获取到设备信息,devSn:"+devSn;
        }
        String proType = deviceInfo.getDevProtocol();
        if(proType.contains("W-C10M") || proType.contains("W-C02M")){
            return makeWatretInfo(entityName,deviceInfo,entity);
        }

        double speed = 0;
        if(!ParamUtils.isNull(entity.getRealtime_point()) && !ParamUtils.isNull(entity.getRealtime_point().getSpeed())){
            speed = entity.getRealtime_point().getSpeed().doubleValue();
        }

        int direction = 0;
        if(!ParamUtils.isNull(entity.getRealtime_point()) && !ParamUtils.isNull(entity.getRealtime_point().getDirection())){
            direction = entity.getRealtime_point().getDirection().intValue();
        }

        StringBuilder stringBuilder = new StringBuilder();
        String deviceName = ParamUtils.isEmpty(deviceInfo.getDevName())?deviceInfo.getDevSn():deviceInfo.getDevName();
        stringBuilder.append(deviceName).append("\n");

        String workModel = "";
        if(deviceInfo.getWorkModel().equals("0")){
            workModel = "无休眠模式";
        }else if(deviceInfo.getWorkModel().equals("1")){
            workModel = "智能休眠模式";
        }else if(deviceInfo.getWorkModel().equals("2")){
            workModel = "一般休眠模式";
        }else if(deviceInfo.getWorkModel().equals("3")){
            workModel = "深度休眠模式";
        }

        if(proType.toLowerCase().contains("hf309")){
            if(deviceInfo.getWorkModel().equals("01")){
                workModel = "智能定位";
            }else if(deviceInfo.getWorkModel().equals("02")){
                workModel = "定时定位";
            }else if(deviceInfo.getWorkModel().equals("03")){
                workModel = "深度休眠";
            }else if(deviceInfo.getWorkModel().equals("0")||deviceInfo.getWorkModel().equals("00")){
                workModel = "不定位";
            }
        }

        stringBuilder.append(workModel).append(" ");

        if(deviceInfo.getOnline().equals("1")){
            if(!ParamUtils.isNull(entity.getRealtime_point().getGpsValid())&&"1".equals(entity.getRealtime_point().getGpsValid().toString())){
                stringBuilder.append("GPS");
            }else if(!ParamUtils.isNull(entity.getRealtime_point().getGpsValid())&&"2".equals(entity.getRealtime_point().getGpsValid().toString())){
                stringBuilder.append("Wifi");
            }else{
                stringBuilder.append("LBS");
            }
            stringBuilder.append(speed>0?"运动":"静止").append(" ");
        }else {
            stringBuilder.append("休眠").append(" ");
            stringBuilder.append(TimeUtil.getLossDate(deviceInfo.getLastUpdateDate())).append(" ");
        }

        if(!ParamUtils.isEmpty(deviceInfo.getBatteryVolt())){
            stringBuilder.append("电量").append(deviceInfo.getBatteryVolt()).append("%");
        }
        stringBuilder.append("\n");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        stringBuilder.append(formatter.format(entity.getRealtime_point().getLoc_time().longValue()*1000)).append("\n");
//        stringBuilder.append(formatter.format(deviceInfo.getLastUpdateDate())).append("\n");

        if(ParamUtils.isNull(currentTrackData)){
            return stringBuilder.toString();
        }
        stringBuilder.append("速度:").append(speed).append(" km/h");
        stringBuilder.append("方向:").append(direction).append("度");
        stringBuilder.append("\n");

        if(!ParamUtils.isEmpty(locationMap.get(entityName))){
            stringBuilder.append("地址:").append(locationMap.get(entityName));
        }
        return stringBuilder.toString();
    }

    private String makeWatretInfo(String entityName, DeviceInfo deviceInfo, Entities entity){
        String value  = "";
        double speed = 0;
        if(!ParamUtils.isNull(entity.getRealtime_point()) && !ParamUtils.isNull(entity.getRealtime_point().getSpeed())){
            speed = entity.getRealtime_point().getSpeed().doubleValue();
        }

        int direction = 0;
        if(!ParamUtils.isNull(entity.getRealtime_point()) && !ParamUtils.isNull(entity.getRealtime_point().getDirection())){
            direction = entity.getRealtime_point().getDirection().intValue();
        }

        int acc = -1;
        if(!ParamUtils.isNull(entity.getRealtime_point()) && !ParamUtils.isNull(entity.getRealtime_point().getAcc())){
            acc = entity.getRealtime_point().getDirection().intValue();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder stringBuilder = new StringBuilder();
        String deviceName = ParamUtils.isEmpty(deviceInfo.getDevName())?deviceInfo.getDevSn():deviceInfo.getDevName();
        stringBuilder.append(deviceName).append("\n").append("状态:");
        if(deviceInfo.getOnline().equals("1")){
            if(!ParamUtils.isNull(entity.getRealtime_point().getGpsValid())&&"1".equals(entity.getRealtime_point().getGpsValid().toString())){
                stringBuilder.append("GPS");
            }else{
                stringBuilder.append("LBS");
            }
            stringBuilder.append(speed>0?"运动":"静止").append(" ");
            if(speed>0){
                stringBuilder.append("速度:").append(speed).append(" km/h");
                stringBuilder.append("方向:").append(direction).append("度");
            }
        }else {
            stringBuilder.append("离线").append(" ");
            stringBuilder.append(TimeUtil.getLossDate(deviceInfo.getLastUpdateDate())).append(" ");
        }
        stringBuilder.append("\n").append("定位时间:");
        stringBuilder.append(formatter.format(entity.getRealtime_point().getLoc_time().longValue() * 1000)).append("\n");

        if(acc != -1) {
            stringBuilder.append("Acc:").append((acc == 1) ? "启动 " : "熄火 ");
        }
        if(!ParamUtils.isEmpty(deviceInfo.getBatteryVolt())){
            stringBuilder.append("电量").append(deviceInfo.getBatteryVolt()).append("%");
        }
        stringBuilder.append("\n");

        if(!ParamUtils.isEmpty(locationMap.get(entityName))){
            stringBuilder.append("地址:").append(locationMap.get(entityName));
        }
        return stringBuilder.toString();
    }

    private BDLocation myLocation;
    private void showLocation(){
        if(ParamUtils.isNull(myLocation)){
            myLocation = BeanFactory.getInstance().getBean(LemonLocation.class).getLocation();
        }
        MapStatusUpdate u1 = MapStatusUpdateFactory.zoomTo(13);
        mBaiduMap.animateMapStatus(u1);
    }

    private void findLocation(final String entityName, LatLng latLng){
        GeoCoder geoCoder = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            // 反地理编码查询结果回调函数
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null
                        || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    // 没有检测到结果
                    EventBus.getDefault().post(new ToastEvent("抱歉，未能找到结果"));
                }
                if(!ParamUtils.isEmpty(result.getAddress())){
                    locationMap.put(entityName, result.getAddress());
                }
            }
        };
        // 设置地理编码检索监听者
        geoCoder.setOnGetGeoCodeResultListener(listener);
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
    }

    public void onEventMainThread(CurrentLocationEvent event){
        myLocation = event.getLocation();
        if (myLocation == null || mMapView == null)
            return;
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(myLocation.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(myLocation.getDirection()).latitude(myLocation.getLatitude())
                .longitude(myLocation.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);
        handler.sendEmptyMessageDelayed(9, Config.getIntValue("location_refresh")*1000);
    }
}
