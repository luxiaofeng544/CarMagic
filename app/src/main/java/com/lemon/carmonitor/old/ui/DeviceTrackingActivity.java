package com.lemon.carmonitor.old.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Message;
import android.view.KeyEvent;
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
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.baidu.mapapi.utils.route.BaiduMapRoutePlan;
import com.baidu.mapapi.utils.route.RouteParaOption;
import com.baidu.trace.Trace;
import com.lemon.LemonActivity;
import com.lemon.LemonDaoManager;
import com.lemon.LemonLocation;
import com.lemon.bean.BeanFactory;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.contant.StatusCode;
import com.lemon.carmonitor.db.Alarm;
import com.lemon.carmonitor.event.ChangeDeviceShowEvent;
import com.lemon.carmonitor.event.FindEntityEvent;
import com.lemon.carmonitor.event.WakeUpEvent;
import com.lemon.carmonitor.listener.EntityListener;
import com.lemon.carmonitor.model.bean.DeviceInfo;
import com.lemon.carmonitor.model.param.GetUserDevsParam;
import com.lemon.carmonitor.model.param.QueryEntityListParam;
import com.lemon.carmonitor.model.result.GetUserDevsResult;
import com.lemon.carmonitor.model.result.QueryEntityLocationResult;
import com.lemon.carmonitor.trace.Entities;
import com.lemon.carmonitor.trace.TrackData;
import com.lemon.carmonitor.ui.PanoActivity;
import com.lemon.config.Config;
import com.lemon.event.CurrentLocationEvent;
import com.lemon.event.StartLocationEvent;
import com.lemon.event.StopLocationEvent;
import com.lemon.event.ToastEvent;
import com.lemon.util.DistUtil;
import com.lemon.util.ParamUtils;
import com.lemon.util.TimeUtil;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.ui]
 * 类描述:    [车辆实施跟踪]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2015/12/21 20:45]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2015/12/21 20:45]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class DeviceTrackingActivity extends LemonActivity {

    private BaiduMap mBaiduMap;
    private MapView mMapView;
    private BDLocation myLocation;
    private Trace trace;
    int serviceId = Config.getServiceId();
    String entityName = "";
    CheckBox checkBox_maptype;
    EntityListener entityListener;
    private View viewPop;
    private TextView tvPop;
    private TrackData currentTrackData;
    private String locationInfo = "未能查找到";
    private LatLng deviceLocation;
    private int refresh_time;
    private Entities carLocation;
    private TextView textView_timeout,textView_distance;
    private int left_time;
    private boolean can_refresh = true;
    private Button button_location,button_pano;
    private boolean isFinish = false;
    private LatLng startLatLng,endLatLng;

    @Override
    protected void setLayout() {
        setLayoutValue(R.layout.devicetracking);
    }

    @Override
    protected void initView() {
        if(!checkDevice()){
            finish();
            return;
        }
        getDevInfo("");
        mMapView = findControl(R.id.bmapsView);
        mBaiduMap = mMapView.getMap();
        checkBox_maptype = findControl(R.id.checkBox_maptype);
        TextView monitoring_tv_title = findControl(R.id.monitoring_tv_title);
        monitoring_tv_title.setText("实时跟踪");
        viewPop = this.getLayoutInflater().inflate(R.layout.pop_view, null);
        tvPop = (TextView) viewPop.findViewById(R.id.textcache);
        textView_timeout = findControl(R.id.textView_timeout);
        textView_distance = findControl(R.id.textView_distance);
        button_location = findControl(R.id.button_location);
        button_pano = findControl(R.id.button_pano);
        button_pano.setVisibility(Config.getBooleanValue("showpano")? View.VISIBLE: View.GONE);

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                if(ParamUtils.isNull(deviceLocation) || marker.getPosition().latitude!=deviceLocation.latitude || marker.getPosition().longitude!=deviceLocation.longitude){
                    return false;
                }
                showPopView(marker.getPosition());
                return true;
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

        handler.sendEmptyMessageDelayed(9, Config.getIntValue("location_refresh_driver"));
    }

    public void activateClick(View v){
        new MaterialDialog.Builder(this)
                .title("激活")
                .content("确定激活此设备吗")
                .positiveText("激活")
                .negativeText("取消")
                .titleColorRes(R.color.black)
                .contentColorRes(R.color.black)
                .backgroundColorRes(R.color.white)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {

                        EventBus.getDefault().post(new WakeUpEvent(cacheManager.getCurrentDevSn()));
                    }
                }).show();
    }

    public void panoClick(View v)
    {
        if(ParamUtils.isNull(deviceLocation)){
            EventBus.getDefault().post(new ToastEvent("还未能获取到位置信息,请尝试刷新后获取到位置在使用全景功能"));
            return;
        }
        Intent intent = new Intent(DeviceTrackingActivity.this, PanoActivity.class);
        intent.putExtra("lat",deviceLocation.latitude);
        intent.putExtra("lon",deviceLocation.longitude);
        startActivity(intent);
    }

    private void refresh(){
        if(!can_refresh){
            return;
        }
        if(left_time==0){
            handler.sendEmptyMessage(6);
            left_time = refresh_time;
        }else {
            left_time--;
            textView_timeout.setText(left_time+"秒后刷新");
        }
    }

    private void getDevInfo(String type){
        GetUserDevsParam param = new GetUserDevsParam();
        param.setLoginToken(cacheManager.getCurrentToken());
        param.setShowDialog(false);
        if(!ParamUtils.isEmpty(type)){
            param.setInvokeType(type);
        }
        apiManager.getUserDevs(param);
    }

    private void showPopView(LatLng ll){
        if(isFinish){return;}

        tvPop.setText(makeInfo());
        tvPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String text = ((TextView) v).getText().toString();
                EventBus.getDefault().post(new ToastEvent(text));*/
            }
        });
        if(ParamUtils.isNull(ll) || ParamUtils.isNull(mBaiduMap) || ParamUtils.isNull(mBaiduMap.getProjection())){
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

    private String makeInfo(){
        DeviceInfo deviceInfo = (DeviceInfo) cacheManager.getBean(cacheManager.getCurrentDevSn());
        if(ParamUtils.isNull(deviceInfo)){
            return  "";
        }

        if(ParamUtils.isNull(currentTrackData) || ParamUtils.isEmpty(currentTrackData.getEntities())){
            return "无设备信息";
        }
        Entities entity = currentTrackData.getEntities().get(0);
        String proType = ((DeviceInfo)cacheManager.getBean(cacheManager.getCurrentDevSn())).getDevProtocol();
        if(proType.contains("W-C10M") || proType.contains("W-C02M")){
            return makeWatretInfo(deviceInfo,entity);
        }

        double speed = 0;
        if(!ParamUtils.isNull(entity.getRealtime_point()) && !ParamUtils.isNull(entity.getRealtime_point().getSpeed())){
            speed = entity.getRealtime_point().getSpeed().doubleValue();
        }

        int direction = 0;
        if(!ParamUtils.isNull(entity.getRealtime_point()) && !ParamUtils.isNull(entity.getRealtime_point().getDirection())){
            direction = entity.getRealtime_point().getDirection().intValue();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
        stringBuilder.append(formatter.format(entity.getRealtime_point().getLoc_time().longValue()*1000)).append("\n");
//        stringBuilder.append(formatter.format(deviceInfo.getLastUpdateDate())).append("\n");

        EventBus.getDefault().post(new ChangeDeviceShowEvent(deviceName,deviceInfo.getBatteryVolt()));
        stringBuilder.append("速度:").append(speed).append(" km/h");
        stringBuilder.append("方向:").append(direction).append("度");
        stringBuilder.append("\n");

        stringBuilder.append("地址:").append(locationInfo);
        return stringBuilder.toString();
    }

    private String makeWatretInfo(DeviceInfo deviceInfo, Entities entity){
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
        stringBuilder.append(formatter.format(entity.getRealtime_point().getLoc_time().longValue()*1000)).append("\n");

        if(acc != -1) {
            stringBuilder.append("Acc:").append((acc == 1) ? "启动 " : "熄火 ");
        }
        if(!ParamUtils.isEmpty(deviceInfo.getBatteryVolt())){
            stringBuilder.append("电量").append(deviceInfo.getBatteryVolt()).append("%");
        }
        stringBuilder.append("\n");

        stringBuilder.append("地址:").append(locationInfo);
        return stringBuilder.toString();
    }

    @Override
    protected void initData() {
        if (ParamUtils.isEmpty(cacheManager.getCurrentDevSn())) {
            return;
        }
        can_refresh = true;
        refresh_time = Config.getIntValue("refresh_time");

        entityListener = BeanFactory.getInstance().getBean(EntityListener.class);
        handler.sendEmptyMessage(9);
        handler.sendEmptyMessage(4);
        handler.sendEmptyMessageDelayed(6, 1000);
        handler.sendEmptyMessageDelayed(7, 1000);
    }

    private int p_c_statue = 0;
    private boolean isLocationPerson = true;
    public void locationClick(View v){
        if(p_c_statue == 0){
            //定位个人位置
            button_location.setBackgroundResource(R.drawable.location_person);
            showLocation();
        }else if(p_c_statue == 1){
            //定位车位置
            button_location.setBackgroundResource(R.drawable.location_normal);
            showCarLocation();
        }else {
            showCarPerson();
        }

        p_c_statue++;
        if(p_c_statue == 3){
            p_c_statue = 0;
        }
    }

    public void getDeviceLocation(){
        String columnKey = "";
        int returnType = 0;
        int activeTime = (int) (System.currentTimeMillis() / 1000 - 12*30*24 * 60 * 60);
        int pageSize = 1000;
        int pageIndex = 1;
        if(!cacheManager.containBean(cacheManager.getCurrentDevSn())){
            toast("请先绑定设备");
            return;
        }
        entityName = ((DeviceInfo)cacheManager.getBean(cacheManager.getCurrentDevSn())).getTraceEntityName() ;


        if(isUseYingyanService()){
            queryEntityListByYingyan(serviceId,entityName,columnKey,returnType,activeTime,pageSize,pageIndex);
            return;
        }

        queryEntityListByServer(serviceId, entityName, columnKey, returnType, activeTime, pageSize, pageIndex);
    }

    private void queryEntityListByYingyan(int serviceId, String entityName, String columnKey, int returnType, int activeTime, int pageSize, int pageIndex){
        entityListener.setType("deviceTrack");
        lbsTraceClient.queryEntityList(serviceId, entityName, columnKey, returnType, activeTime, pageSize, pageIndex, entityListener);
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
        apiManager.queryCurrentLocation(param);
    }

    public void onEventMainThread(QueryEntityLocationResult result){
        if(result.getRetCode().equals(StatusCode.SUCCESS.getCode())){
            currentTrackData = result.getRetData();
            LatLng latLng = new LatLng(Double.parseDouble(currentTrackData.getEntities().get(0).getRealtime_point().getLocation()[1]), Double.parseDouble(currentTrackData.getEntities().get(0).getRealtime_point().getLocation()[0]));
            deviceLocation = latLng;
            findLocation(latLng);
            carLocation(currentTrackData);
        }
    }

    public void refreshClick(View v){
        handler.sendEmptyMessage(6);
    }

    public void onEventMainThread(FindEntityEvent event){
        if(!event.getType().equals("deviceTrack")){
            return;
        }
        currentTrackData = event.getTrackData();
        LatLng latLng = new LatLng(Double.parseDouble(currentTrackData.getEntities().get(0).getRealtime_point().getLocation()[1]), Double.parseDouble(currentTrackData.getEntities().get(0).getRealtime_point().getLocation()[0]));
        deviceLocation = latLng;
        findLocation(latLng);
        carLocation(event.getTrackData());
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
                showLocation();
                break;
            case 5:
                showPopView((LatLng) msg.obj);
                break;
            case 6:
                getDevInfo("deviceTracking");
//                getDeviceLocation();
                break;
            case 7:
                refresh();
                handler.sendEmptyMessageDelayed(7, 1000);
                break;
            case 8:
                textView_distance.setText("人车距离 "+msg.obj);
                break;
            case 9:
                EventBus.getDefault().post(new StartLocationEvent());
                handler.sendEmptyMessageDelayed(9, Config.getIntValue("location_refresh_driver"));
                break;
        }
    }

    public void onEventMainThread(GetUserDevsResult result){
        if(!ParamUtils.isEmpty(result.getBaseParam().getInvokeType()) && result.getBaseParam().getInvokeType().equals("deviceTracking")){
            getDeviceLocation();
        }
    }

    private void showLocation(){
        if(ParamUtils.isNull(myLocation)){
            myLocation = BeanFactory.getInstance().getBean(LemonLocation.class).getLocation();
        }
        if(ParamUtils.isNull(myLocation)){
            EventBus.getDefault().post(new ToastEvent("未能获取到手机位置,请打开系统获取位置信息设置后尝试"));
            return;
        }
        MapStatusUpdate u1 = MapStatusUpdateFactory.zoomTo(16);
        mBaiduMap.animateMapStatus(u1);

        LatLng ll = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
        mBaiduMap.animateMapStatus(u);
    }

    private void showCarPerson(){
        if(ParamUtils.isNull(startLatLng) || ParamUtils.isNull(endLatLng)){
            return;
        }
        LatLngBounds bounds = new LatLngBounds.Builder().include(startLatLng).include(endLatLng).build();
        MapStatusUpdate msUpdate = MapStatusUpdateFactory.newLatLngBounds(bounds);
        mBaiduMap.setMapStatus(msUpdate);
        mBaiduMap.animateMapStatus(msUpdate);
    }

    private LatLng carLocationPoint;
    private void showCarLocation(){
        if(ParamUtils.isNull(carLocationPoint)){
            return;
        }
        MapStatusUpdate u1 = MapStatusUpdateFactory.zoomTo(16);
        mBaiduMap.animateMapStatus(u1);

        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(carLocationPoint);
        mBaiduMap.animateMapStatus(u);
    }

    private void carLocation(TrackData trackData){
        if(ParamUtils.isEmpty(trackData.getEntities())){
            toast("未获取到车辆信息,该车辆最近一个月未有上传记录");
            return;
        }

        Entities entity = trackData.getEntities().get(0);
        if(ParamUtils.isNull(entity)){
            toast("接口有误,获取到数据异常");
            return;
        }

        carLocation = entity;
        LatLng ll = new LatLng(Double.valueOf(entity.getRealtime_point().getLocation()[1]), Double.valueOf(entity.getRealtime_point().getLocation()[0]));
        carLocationPoint = ll;
        MarkerOptions marker = new MarkerOptions();
        mBaiduMap.clear();
        String devSn = ParamUtils.isEmpty(entity.getDEV_SN())?entity.getEntity_name():entity.getDEV_SN();
        float direction = entity.getRealtime_point().getDirection().floatValue() -90;
        if(cacheManager.containBean(devSn)){
            DeviceInfo deviceInfo = (DeviceInfo) cacheManager.getBean(devSn);
            List<Alarm> list = BeanFactory.getInstance().getBean(LemonDaoManager.class).queryAllOrderBy(Alarm.class,"id",false);
            boolean hasAlarm = false;
            for(Alarm alarm:list){
                if(alarm.getStatue().equals("-1") && alarm.getDevSn().equals(deviceInfo.getDevSn())){
                    hasAlarm = true;
                    break;
                }
            }

            if(hasAlarm){
                mBaiduMap.addOverlay(marker.position(ll).icon(BitmapDescriptorFactory.fromResource(R.drawable.alarm_0)).rotate(direction));
            }else {
                if (deviceInfo.getOnline().equals("1")) {
                    if (entity.getRealtime_point().getSpeed().doubleValue() > 0) {
                        mBaiduMap.addOverlay(marker.position(ll).icon(BitmapDescriptorFactory.fromResource(R.drawable.driving_0)).rotate(direction));
                    } else {
                        mBaiduMap.addOverlay(marker.position(ll).icon(BitmapDescriptorFactory.fromResource(R.drawable.online_0)).rotate(direction));
                    }
                } else {
                    mBaiduMap.addOverlay(marker.position(ll).icon(BitmapDescriptorFactory.fromResource(R.drawable.offline_0)).rotate(direction));
                }
            }
        } else {
            mBaiduMap.addOverlay(marker.position(ll).icon(BitmapDescriptorFactory.fromResource(R.drawable.offline_0)).rotate(direction));
        }

        //draw line
        if((myLocation.getLatitude()!=4.9E-324)&&(myLocation.getLongitude()!=4.9E-324)){
            List<LatLng> points = new ArrayList<>();
            points.add(ll);
            LatLng pt_start = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            startLatLng = pt_start;
            endLatLng = ll;
            points.add(pt_start);
            OverlayOptions ooPolyline = new PolylineOptions().width(6).color(0xAA00FF00).points(points);
            mBaiduMap.addOverlay(ooPolyline);

            double distance = DistanceUtil.getDistance(ll, pt_start);
            Message message1 = handler.obtainMessage();
            message1.what=8;
            message1.obj = DistUtil.convertDistance(distance);
            handler.sendMessageDelayed(message1, 1000);
        }

        Message message = handler.obtainMessage();
        message.what=5;
        message.obj = ll;
        handler.sendMessageDelayed(message, 2000);
    }

    public void navClick(View v){
        if(ParamUtils.isNull(carLocation)){
            toast("未获取到车辆位置");
            return;
        }

        if(ParamUtils.isNull(myLocation) || (myLocation.getLatitude()==4.9E-324)||(myLocation.getLongitude()==4.9E-324)){
            toast("未获取到手机位置");
           return;
        }

        startNavigatorClick();
    }

    public void startNavigatorClick() {
        LatLng pt_start = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        LatLng pt_end = new LatLng(Double.valueOf(carLocation.getRealtime_point().getLocation()[1]), Double.valueOf(carLocation.getRealtime_point().getLocation()[0]));
        RouteParaOption para = new RouteParaOption().startPoint(pt_start).endPoint(pt_end);
        try {
            BaiduMapRoutePlan.openBaiduMapDrivingRoute(para, this);
        } catch (Exception e) {
            e.printStackTrace();
            showDialog();
        }
    }

    /**
     * 提示未安装百度地图app或app版本过低
     *
     */
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OpenClientUtil.getLatestBaiduMapApp(mContext);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();

    }

    private void findLocation(LatLng latLng){
        GeoCoder geoCoder = GeoCoder.newInstance();
        //
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
                    locationInfo = result.getAddress();
                }
            }
        };
        // 设置地理编码检索监听者
        geoCoder.setOnGetGeoCodeResultListener(listener);
        //
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
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            can_refresh = false;
            finish();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        isFinish = true;
        handler.removeCallbacksAndMessages(null);
        EventBus.getDefault().post(new StopLocationEvent());
        // 关闭定位图层
        if(!ParamUtils.isNull(mMapView)){
            mMapView.onDestroy();
            mMapView = null;
        }
        if(!ParamUtils.isNull(mBaiduMap)){
            mBaiduMap.setMyLocationEnabled(false);
        }
        super.onDestroy();
    }
}
