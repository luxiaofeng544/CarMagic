package com.lemon.carmonitor.old.ui;

import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.lemon.LemonActivity;
import com.lemon.bean.BeanFactory;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.component.MaterialRangeSlider;
import com.lemon.carmonitor.contant.StatusCode;
import com.lemon.carmonitor.event.BaiduFenceAddEvent;
import com.lemon.carmonitor.event.BaiduFenceUpdateEvent;
import com.lemon.carmonitor.event.FindEntityEvent;
import com.lemon.carmonitor.listener.EntityListener;
import com.lemon.carmonitor.listener.GeoFenceListener;
import com.lemon.carmonitor.model.FenceModel;
import com.lemon.carmonitor.model.bean.DeviceInfo;
import com.lemon.carmonitor.model.param.SaveDevFenceParam;
import com.lemon.carmonitor.model.result.SaveDevFenceResult;
import com.lemon.carmonitor.trace.TrackData;
import com.lemon.config.Config;
import com.lemon.util.ParamUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by XiaoFeng on 2015/11/2.
 */
public class AddFenceActivity extends LemonActivity implements MaterialRangeSlider.RangeSliderListener {
    BaiduMap mBaiduMap = null;
    MapView mMapView = null;
    ImageView imageView1_hidden;
    TextView fence_btn_add, radius_textview, tv_min, tv_max;
    private LinearLayout fence_panel_view_inner;
    private RadioButton fence_danger, fence_safe;
    private LatLng currentLocation;
    private SeekBar radius_seekbar;
    private int min_radius, max_radius;
    private GeoFenceListener geoFenceListener;
    private MaterialRangeSlider timeSlider;
    private int currentMinTime = 0;
    private int currentMaxTime = 100;
    private TextView tv_mon,tv_tue,tv_wed,tv_thu,tv_fri,tv_sat,tv_sun;
    private boolean selectMon,selectTue,selectWed,selectThu,selectFri,selectSat,selectSun;
    private String loadStartTime,loadEndTime;
    private MyLocationData locData;
    private String entityName = "";

    @Override
    protected void setLayout() {
        setLayoutValue(R.layout.activity_addfence);
    }

    @Override
    protected void initView() {
        mMapView = (MapView) findViewById(R.id.map);
        fence_panel_view_inner = (LinearLayout) findViewById(R.id.fence_panel_view_inner);
        imageView1_hidden = (ImageView) findViewById(R.id.imageView1_hidden);
        fence_btn_add = (TextView) findViewById(R.id.fence_btn_add);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, null));

        radius_seekbar = (SeekBar) findViewById(R.id.radius_seekbar);
        radius_textview = (TextView) findViewById(R.id.radius_textview);
        fence_danger = findControl(R.id.fence_danger);
        fence_safe = findControl(R.id.fence_safe);
        tv_max = findControl(R.id.tv_max);
        tv_min = findControl(R.id.tv_min);
        tv_mon = findControl(R.id.tv_mon);
        tv_tue = findControl(R.id.tv_tue);
        tv_wed = findControl(R.id.tv_wed);
        tv_thu = findControl(R.id.tv_thu);
        tv_fri = findControl(R.id.tv_fri);
        tv_sat = findControl(R.id.tv_sat);
        tv_sun = findControl(R.id.tv_sun);

        tv_mon.setOnClickListener(weekClickListener);
        tv_tue.setOnClickListener(weekClickListener);
        tv_wed.setOnClickListener(weekClickListener);
        tv_thu.setOnClickListener(weekClickListener);
        tv_fri.setOnClickListener(weekClickListener);
        tv_sat.setOnClickListener(weekClickListener);
        tv_sun.setOnClickListener(weekClickListener);

        timeSlider = (MaterialRangeSlider) findViewById(R.id.time_seekbar);
        timeSlider.setMin(0);
        timeSlider.setMax(1439);
        timeSlider.setStartingMinMax(0, 1439);
        timeSlider.setRangeSliderListener(this);

        min_radius = Config.getIntValue("min_radius");
        max_radius = Config.getIntValue("max_radius");
        fence_btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFence();
            }
        });

        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                currentLocation = mapStatus.target;
                locData = new MyLocationData.Builder()
                        .accuracy(Integer.valueOf(getTextViewValue(R.id.radius_textview)))
                        .direction(100).latitude(currentLocation.latitude)
                        .longitude(currentLocation.longitude).build();
                mBaiduMap.setMyLocationData(locData);
                LatLng ll = new LatLng(currentLocation.latitude, currentLocation.longitude);
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
            }
        });

        radius_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                radius_textview.setText((200 + progress * 48) + "");
                Message msg = handler.obtainMessage();
                msg.what =2;
                msg.obj = (200 + progress * 48);
                handler.sendMessage(msg);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        selectAllWeek(true);
    }

    @Override
    protected void initData() {
        geoFenceListener = BeanFactory.getInstance().getBean(GeoFenceListener.class);
        getDeviceLocation();
    }

    private void initWeek(String days){
        if(days.contains("1")){
            selectMon = true;
        }

        if(days.contains("2")){
            selectTue = true;
        }

        if(days.contains("3")){
            selectWed = true;
        }

        if(days.contains("4")){
            selectThu = true;
        }

        if(days.contains("5")){
            selectFri = true;
        }

        if(days.contains("6")){
            selectSat = true;
        }

        if(days.contains("7")){
            selectSun = true;
        }

        refreshWeekStatue();
    }

    private View.OnClickListener weekClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.tv_mon) {
                selectMon = !selectMon;
            } else if (v.getId() == R.id.tv_tue) {
                selectTue = !selectTue;
            } else if (v.getId() == R.id.tv_wed) {
                selectWed = !selectWed;
            }else if (v.getId() == R.id.tv_thu) {
                selectThu = !selectThu;
            } else if (v.getId() == R.id.tv_fri) {
                selectFri = !selectFri;
            } else if (v.getId() == R.id.tv_sat) {
                selectSat = !selectSat;
            } else if (v.getId() == R.id.tv_sun) {
                selectSun = !selectSun;
            }

            refreshWeekStatue();
        }
    };

    private void refreshWeekStatue(){
        tv_mon.setBackgroundResource(selectMon? R.drawable.week_cricle_bg: R.drawable.week_cricle_bg_unselected);
        tv_tue.setBackgroundResource(selectTue? R.drawable.week_cricle_bg: R.drawable.week_cricle_bg_unselected);
        tv_wed.setBackgroundResource(selectWed? R.drawable.week_cricle_bg: R.drawable.week_cricle_bg_unselected);
        tv_thu.setBackgroundResource(selectThu? R.drawable.week_cricle_bg: R.drawable.week_cricle_bg_unselected);
        tv_fri.setBackgroundResource(selectFri? R.drawable.week_cricle_bg: R.drawable.week_cricle_bg_unselected);
        tv_sat.setBackgroundResource(selectSat? R.drawable.week_cricle_bg: R.drawable.week_cricle_bg_unselected);
        tv_sun.setBackgroundResource(selectSun ? R.drawable.week_cricle_bg : R.drawable.week_cricle_bg_unselected);
    }

    private void selectAllWeek(boolean isSelect){
        selectMon = selectTue = selectWed = selectThu = selectFri = selectSat = selectSun = isSelect;
        tv_mon.setBackgroundResource(isSelect? R.drawable.week_cricle_bg: R.drawable.week_cricle_bg_unselected);
        tv_tue.setBackgroundResource(isSelect? R.drawable.week_cricle_bg: R.drawable.week_cricle_bg_unselected);
        tv_wed.setBackgroundResource(isSelect? R.drawable.week_cricle_bg: R.drawable.week_cricle_bg_unselected);
        tv_thu.setBackgroundResource(isSelect? R.drawable.week_cricle_bg: R.drawable.week_cricle_bg_unselected);
        tv_fri.setBackgroundResource(isSelect? R.drawable.week_cricle_bg: R.drawable.week_cricle_bg_unselected);
        tv_sat.setBackgroundResource(isSelect? R.drawable.week_cricle_bg: R.drawable.week_cricle_bg_unselected);
        tv_sun.setBackgroundResource(isSelect? R.drawable.week_cricle_bg: R.drawable.week_cricle_bg_unselected);
    }

    private String getWeek(){
        String week = "";
        boolean isFirst = true;

        if (selectMon) {
            isFirst = false;
            week = "1";
        }

        if (selectTue) {
            if (isFirst) {
                week = "2";
            }
            week += ",2";
        }

        if (selectWed) {
            if (isFirst) {
                week = "3";
            }
            week += ",3";
        }

        if (selectThu) {
            if (isFirst) {
                week = "4";
            }
            week += ",4";
        }

        if (selectFri) {
            if (isFirst) {
                week = "5";
            }
            week += ",5";
        }

        if (selectSat) {
            if (isFirst) {
                week = "6";
            }
            week += ",6";
        }

        if (selectSun) {
            if (isFirst) {
                week = "7";
            }
            week += ",7";
        }
        return week;
    }

    private String getTime(){
        double totalMin = currentMinTime;
        int hourMin = (int)(totalMin/60);
        int minutesMin = (int)(totalMin-hourMin*60);
        String minTime = (hourMin==0?"00":(hourMin<10?"0"+hourMin:hourMin))+""+(minutesMin==0?"00":(minutesMin<10?"0"+minutesMin:minutesMin));

        double totalMax = currentMaxTime;
        int hourMax = (int)(totalMax/60);
        int minutesMax = (int)(totalMax-hourMax*60);
        String maxTime = (hourMax==0?"00":(hourMax<10?"0"+hourMax:hourMax))+""+(minutesMax==0?"00":(minutesMax<10?"0"+minutesMax:minutesMax));

        return minTime+","+maxTime;
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
        EntityListener entityListener = BeanFactory.getInstance().getBean(EntityListener.class);
        entityListener.setType("fenceDeviceTrack");
        lbsTraceClient.queryEntityList(Config.getServiceId(), entityName, columnKey, returnType, activeTime, pageSize, pageIndex, entityListener);
    }

    public void onEventMainThread(FindEntityEvent event){
        if(!event.getType().equals("fenceDeviceTrack")){
            return;
        }
        TrackData currentTrackData = event.getTrackData();
        LatLng latLng = new LatLng(Double.parseDouble(currentTrackData.getEntities().get(0).getRealtime_point().getLocation()[1]), Double.parseDouble(currentTrackData.getEntities().get(0).getRealtime_point().getLocation()[0]));

        locData = new MyLocationData.Builder()
                .accuracy(500)
                .direction(100).latitude(latLng.latitude)
                .longitude(latLng.longitude).build();
        mBaiduMap.setMyLocationData(locData);
        currentLocation = latLng;
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(currentLocation);
        mBaiduMap.animateMapStatus(u);
        MapStatusUpdate u1 = MapStatusUpdateFactory.zoomTo(16);
        mBaiduMap.animateMapStatus(u1);
    }

    @Override
    public void onMinChanged(int newValue) {
        int hour = (int) (newValue / 60);
        int minutes = (int) (newValue - hour * 60);
        String minTime = (hour == 0 ? "00" : (hour < 10 ? "0" + hour : hour)) + ":" + (minutes == 0 ? "00" : (minutes < 10 ? "0" + minutes : minutes));
        tv_min.setText("" + minTime);
        currentMinTime = newValue;
    }

    @Override
    public void onMaxChanged(int newValue) {
        int hour = (int) (newValue / 60);
        int minutes = (int) (newValue - hour * 60);
        String maxTime = (hour == 0 ? "00" : (hour < 10 ? "0" + hour : hour)) + ":" + (minutes == 0 ? "00" : (minutes < 10 ? "0" + minutes : minutes));
        tv_max.setText("" + maxTime);
        currentMaxTime = newValue;
    }

    public void backClick(View v) {
        finish();
    }

    public void showClick(View v) {
        if (fence_panel_view_inner.getVisibility() == View.VISIBLE) {
            fence_panel_view_inner.setVisibility(View.GONE);
        } else {
            fence_panel_view_inner.setVisibility(View.VISIBLE);
        }
    }

    public void saveFence() {
        if (ParamUtils.isNull(currentLocation)) {
            toast("无法获取到地理位置信息");
            return;
        }

        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);

        long serviceId = Config.getServiceId();
        String creator = cacheManager.getCurrentEntityName();
        String fenceName = getEditTextValue(R.id.name_edittext);
        String fenceDesc = "";
        String monitoredPersons = creator;
        String observers = creator;
        String validTimes = getTime();
        int validCycle = 4;
        String validDate = dateString.replaceAll("-", "");
        String validDays = getWeek();
        int coordType = 1;
        String center = currentLocation.longitude + "," + currentLocation.latitude;
        double radius = Double.parseDouble(getTextViewValue(R.id.radius_textview));
        int alarmCondition = 3;

        lbsTraceClient.createCircularFence(serviceId, creator, fenceName, fenceDesc, monitoredPersons,
                observers, validTimes, validCycle, validDate, validDays, coordType, center, radius, alarmCondition, geoFenceListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onEventMainThread(BaiduFenceAddEvent event) {
        if (event.getStatue() != 0) {
            toast(event.getMessage());
        } else {
            FenceModel fenceModel = new FenceModel();
            fenceModel.setFenceId(event.getFenceId());
            fenceModel.setEntityName(cacheManager.getCurrentEntityName());
            fenceModel.setDevSn(cacheManager.getCurrentDevSn());
            fenceModel.setFenceName(getEditTextValue(R.id.name_edittext));
            fenceModel.setFenceRadius(getTextViewValue(R.id.radius_textview));
            fenceModel.setType(fence_danger.isChecked() ? "危险区域" : "安全区域");
            fenceModel.setLatitude(currentLocation.latitude);
            fenceModel.setValidDays(getWeek());
            fenceModel.setValidTimes(getTime());
            fenceModel.setLongitude(currentLocation.longitude);
            SaveDevFenceParam param = convert(fenceModel);

            apiManager.saveDevFence(param);
        }
    }

    public void onEventMainThread(SaveDevFenceResult result) {
        if (result.getRetCode().equals(StatusCode.SUCCESS.getCode())) {
            toast("保存成功");
            finish();
        } else {
            toast(result.getRetMsg());
        }
    }

    public void onEventMainThread(BaiduFenceUpdateEvent event) {
        if (event.getStatue() != 0) {
            toast(event.getMessage());
        } else {
            FenceModel fenceModel = new FenceModel();
            fenceModel.setFenceId(event.getFenceId());
            fenceModel.setEntityName(cacheManager.getCurrentEntityName());
            fenceModel.setDevSn(cacheManager.getCurrentDevSn());
            fenceModel.setFenceName(getEditTextValue(R.id.name_edittext));
            fenceModel.setFenceRadius(getTextViewValue(R.id.radius_textview));
            fenceModel.setType(fence_danger.isChecked() ? "危险区域" : "安全区域");
            fenceModel.setLatitude(currentLocation.latitude);
            fenceModel.setLongitude(currentLocation.longitude);
            fenceModel.setValidDays(getWeek());
            fenceModel.setValidTimes(getTime());
            SaveDevFenceParam param = convert(fenceModel);

            apiManager.saveDevFence(param);
        }
    }

    @Override
    public boolean isDestroyed() {
        return super.isDestroyed();
    }

    private SaveDevFenceParam convert(FenceModel fenceModel) {
        SaveDevFenceParam param = new SaveDevFenceParam();
        param.setLoginToken(cacheManager.getCurrentToken());
        param.setDevSn(cacheManager.getCurrentDevSn());
        param.setFenceId(Integer.valueOf(fenceModel.getFenceId()));
        param.setFenceName(fenceModel.getFenceName());
        param.setType(fenceModel.getType().equals("危险区域") ? 1 : 2);
        param.setRadius(Integer.valueOf(fenceModel.getFenceRadius()));
        param.setLongitude(fenceModel.getLongitude());
        param.setLatitude(fenceModel.getLatitude());
        param.setValidDays(fenceModel.getValidDays());
        param.setValidTimes(fenceModel.getValidTimes());
        param.setShowDialog(false);
        return param;
    }

    @Override
    public void notificationMessage(Message msg) {
        switch (msg.what){
            case 1:
                tv_min.setText(loadStartTime);
                tv_max.setText(loadEndTime);
                break;
            case 2:
                //draw circle
                drawCircle((Integer) msg.obj);
                break;
        }
    }

    private void drawCircle(int radius){
        if(ParamUtils.isNull(currentLocation)){
            return;
        }
        locData = new MyLocationData.Builder()
                .accuracy(radius)
                .direction(100).latitude(currentLocation.latitude)
                .longitude(currentLocation.longitude).build();
        mBaiduMap.setMyLocationData(locData);
        LatLng ll = new LatLng(currentLocation.latitude, currentLocation.longitude);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
        mBaiduMap.animateMapStatus(u);
    }

}
