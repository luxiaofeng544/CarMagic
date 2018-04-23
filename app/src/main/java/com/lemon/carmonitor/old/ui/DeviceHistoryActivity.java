package com.lemon.carmonitor.old.ui;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
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
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.trace.OnTrackListener;
import com.lemon.LemonActivity;
import com.lemon.LemonLocation;
import com.lemon.bean.BeanFactory;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.component.CalendarView;
import com.lemon.carmonitor.component.MaterialRangeSlider;
import com.lemon.carmonitor.contant.StatusCode;
import com.lemon.carmonitor.event.DrawHistoryEvent;
import com.lemon.carmonitor.model.bean.DeviceInfo;
import com.lemon.carmonitor.model.bean.protocol.ModelsEntity;
import com.lemon.carmonitor.model.param.QueryHistoryTrackParam;
import com.lemon.carmonitor.model.result.QueryHistoryTrackResult;
import com.lemon.carmonitor.trace.HistoryTrackData;
import com.lemon.config.Config;
import com.lemon.event.ToastEvent;
import com.lemon.util.DateUtils;
import com.lemon.util.JSONUtils;
import com.lemon.util.LogUtils;
import com.lemon.util.ParamUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.ui]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2015/12/21 20:40]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2015/12/21 20:40]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class DeviceHistoryActivity extends LemonActivity implements MaterialRangeSlider.RangeSliderListener {

    private RelativeLayout way_point_calendar_rl;
    private SimpleDateFormat format;
    private CalendarView calendar;
    private TextView calendar_today_tv,tv_min,tv_max,correct_tv;
    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    private MaterialRangeSlider timeSlider;
    private int currentMinTime = 0;
    private int currentMaxTime = 100;
    private SeekBar progress_seekbar;
    private SeekBar speed_seekbar;
    private int speed = 200;
    private int playTotalCount = 0;
    private int playIndex = 0;
    private boolean progressByTouch = false;
    private boolean isFinish = false;
    CheckBox checkBox_maptype;
    private TextView tvPop;
    private View viewPop;
    Map<LatLng,HistoryTrackData.Points> pointsMap = new HashMap<>();
    Map<LatLng,Object> stopPoints = new HashMap<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

    // 起点图标
    private static BitmapDescriptor bmStart;
    // 终点图标
    private static BitmapDescriptor bmEnd;

    // 起点图标覆盖物
    private static MarkerOptions startMarker = null;
    // 终点图标覆盖物
    private static MarkerOptions endMarker = null;
    // 路线覆盖物
    private static PolylineOptions polyline = null;
    private MapStatusUpdate msUpdate = null;
    private Date selectDate;
    private List<HistoryTrackData.Points> currentHistoryPoints,selectHistoryPoints;
    private Button btn_play;
    private Thread playThread;
    int interval = 10;


    @Override
    protected void setLayout() {
        setLayoutValue(R.layout.devicehistory);
    }

    @Override
    protected void initView() {
        if(!checkDevice()){
            finish();
            return;
        }
        way_point_calendar_rl = (RelativeLayout) findViewById(R.id.way_point_calendar_rl);
        calendar_today_tv = (TextView) findViewById(R.id.calendar_today_tv);
        timeSlider =  (MaterialRangeSlider) findViewById(R.id.time_seekbar);
        tv_max = findControl(R.id.tv_max);
        tv_min = findControl(R.id.tv_min);
        btn_play = findControl(R.id.btn_play);
        correct_tv = findControl(R.id.correct_tv);
        speed_seekbar = findControl(R.id.speed_seekbar);
        progress_seekbar = findControl(R.id.progress_seekbar);
        checkBox_maptype = findControl(R.id.checkBox);
        viewPop = getLayoutInflater().inflate(R.layout.pop_view, null);
        tvPop = (TextView) viewPop.findViewById(R.id.textcache);
        initCalendar();
        initMap();

        progress_seekbar.setOnSeekBarChangeListener(seekBarChangeListener);
        speed_seekbar.setOnSeekBarChangeListener(seekBarChangeListener);
        speed_seekbar.setProgress(50);

        calendar_today_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarVisibilityClick(v);
            }
        });

        correct_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ParamUtils.isNull(selectDate)) {
                    EventBus.getDefault().post(new ToastEvent("未选择日期"));
                    return;
                }

                handler.sendEmptyMessage(6);
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

        timeSlider.setMin(0);
        timeSlider.setMax(100);
        timeSlider.setStartingMinMax(0, 100);
        timeSlider.setRangeSliderListener(this);// 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, null));
    }

    ModelsEntity modelsEntity;
    String proType;
    @Override
    protected void initData() {
        if (ParamUtils.isEmpty(cacheManager.getCurrentDevSn())) {
            return;
        }
        proType = ((DeviceInfo)cacheManager.getBean(cacheManager.getCurrentDevSn())).getDevProtocol();
        modelsEntity = cacheManager.getBean(proType, ModelsEntity.class);
        selectHistoryPoints = new ArrayList<>();
        interval = Config.getIntValue("interval");
        handler.sendEmptyMessageDelayed(11, 1000);
        showLocation();
    }

    private void initMap() {
        mMapView = (MapView) findViewById(R.id.map);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                showPopView(marker.getPosition());
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
    }


    private void showPopView(LatLng ll){
        if(!pointsMap.containsKey(ll) && !stopPoints.containsKey(ll)){
            return;
        }

        String info = "";
        if(pointsMap.containsKey(ll)) {
            info = "时间:" + pointsMap.get(ll).getCreate_time() + "\n";
            info += "速度:" + pointsMap.get(ll).getSpeed() + " km/h\n";
            info += "方向角度:" + pointsMap.get(ll).getDirection() + "\n";
        }
        if(stopPoints.containsKey(ll)){
            info = stopPoints.get(ll).toString();
        }

        tvPop.setText(info);
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

    private boolean isPlay = false;
    public void repalyClick(View v){
        if(ParamUtils.isEmpty(selectHistoryPoints)){
            toast("无轨迹可播放");
            return;
        }

        isPlay = !isPlay;
        if(isPlay){
            setProgressBarEnable(false);
            btn_play.setText("暂停");
            toast("开始播放轨迹");
        }else {
            setProgressBarEnable(true);
            btn_play.setText("播放");
            toast("结束播放轨迹");
        }
        playThread = new Thread(new Runnable() {
            @Override
            public void run() {
                play();
            }
        });
        playThread.start();
    }

    public void play(){
        if(progress_seekbar.getProgress() == 0 || progress_seekbar.getProgress() == 100){
            playIndex = playTotalCount;
        }
        int i = playIndex;
        for(;i>-1;i--){
            if(!isPlay || isFinish){
                handler.sendEmptyMessage(16);
                break;
            }
            handler.sendEmptyMessage(15);
            playIndex = i;
            if(playIndex == playTotalCount){
                playIndex = playTotalCount - 1;
            }
            Message msg = handler.obtainMessage();
            msg.obj = selectHistoryPoints.get(playIndex);
            msg.what = 7;
            handler.sendMessage(msg);
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message msg1 = handler.obtainMessage();
            msg1.obj = 100 - (playIndex*100/playTotalCount);
            msg1.what = 9;
            handler.sendMessage(msg1);
        }
        if(progress_seekbar.getProgress() == 100){
            progress_seekbar.setProgress(0);
            playIndex = playTotalCount;
        }
        isPlay = false;
        handler.sendEmptyMessage(16);
        handler.sendEmptyMessage(8);
    }

    public void palyPoint(HistoryTrackData.Points point){
        if(ParamUtils.isNull(mBaiduMap) || !isPlay || isFinish){
            return;
        }
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(Float.parseFloat(point.getRadius()+""))
                .direction(point.getDirection()).latitude(point.getLocation().get(1))
                .longitude(point.getLocation().get(0)).build();
        mBaiduMap.setMyLocationData(locData);

        LatLng ll = new LatLng(point.getLocation().get(1), point.getLocation().get(0));
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
        mBaiduMap.animateMapStatus(u);
    }

    public void palyPointByTouch(HistoryTrackData.Points point){
        if(ParamUtils.isNull(mBaiduMap) || isFinish){
            return;
        }
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(Float.parseFloat(point.getRadius()+""))
                .direction(point.getDirection()).latitude(point.getLocation().get(1))
                .longitude(point.getLocation().get(0)).build();
        mBaiduMap.setMyLocationData(locData);

        LatLng ll = new LatLng(point.getLocation().get(1), point.getLocation().get(0));
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
        mBaiduMap.animateMapStatus(u);
    }

    private void initCalendar() {
        format = new SimpleDateFormat("yyyy-MM-dd");
        //获取日历控件对象
        calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.setSelectMore(false); //单选

        //获取日历中年月 ya[0]为年，ya[1]为月（格式大家可以自行在日历控件中改）
        String[] ya = calendar.getYearAndmonth().split("-");
        calendar_today_tv.setText(ya[0] + "年" + ya[1] + "月");
        //设置控件监听，可以监听到点击的每一天（大家也可以在控件中根据需求设定）
        calendar.setOnItemClickListener(new CalendarView.OnItemClickListener() {

            @Override
            public void OnItemClick(Date selectedStartDate,
                                    Date selectedEndDate, Date downDate) {
                if (calendar.isSelectMore()) {
                    Toast.makeText(getApplicationContext(), format.format(selectedStartDate) + "到" + format.format(selectedEndDate), Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(getApplicationContext(), format.format(downDate), Toast.LENGTH_SHORT).show();
                    selectDate = downDate;
                    queryHistory(downDate);
                    timeSlider.setMin(0);
                    timeSlider.setMax(100);
                    timeSlider.setStartingMinMax(0, 100);
                    calendarVisibilityClick(null);
                }
            }
        });
    }

    @Override
    public void onMinChanged(int newValue) {
        double totalMin = 14.4*newValue;
        int hour = (int)(totalMin/60);
        int minutes = (int)(totalMin-hour*60);
        String minTime = (hour==0?"00":(hour<10?"0"+hour:hour))+":"+(minutes==0?"00":(minutes<10?"0"+minutes:minutes));
        tv_min.setText(""+minTime);
        currentMinTime = newValue;
        if(currentMinTime >0 && !ParamUtils.isNull(selectDate)){
            handler.sendEmptyMessage(12);
        }
    }

    @Override
    public void onMaxChanged(int newValue) {
        double totalMin = 14.4*newValue;
        int hour = (int)(totalMin/60);
        int minutes = (int)(totalMin-hour*60);
        String maxTime = (hour==0?"00":(hour<10?"0"+hour:hour))+":"+(minutes==0?"00":(minutes<10?"0"+minutes:minutes));
        tv_max.setText(""+maxTime);
        currentMaxTime = newValue;
        if(currentMaxTime < 100 && !ParamUtils.isNull(selectDate)){
            handler.sendEmptyMessage(12);
        }
    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(seekBar.getId() == R.id.progress_seekbar){
                if(!isPlay){
                    double result = (playTotalCount/100.0);
                    int index = (int)((100 - progress_seekbar.getProgress())*result);
                    if(index == playTotalCount){
                        index = playTotalCount - 1;
                    }
                    playIndex = index;
                    if(!ParamUtils.isNull(selectHistoryPoints) && !ParamUtils.isNull(selectHistoryPoints.get(index))){
                        Message msg = handler.obtainMessage();
                        msg.obj = selectHistoryPoints.get(index);
                        msg.what = 10;
                        handler.sendMessage(msg);
                    }
                }
            }else if(seekBar.getId() == R.id.speed_seekbar){
                speed = 410-progress*4;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private void setProgressBarEnable(boolean enable){
        progress_seekbar.setEnabled(enable);
    }

    private void setProgressBarValue(int value){
        progress_seekbar.setProgress(value);
    }

    public void calendarVisibilityClick(View v) {
        if (way_point_calendar_rl.getVisibility() == View.GONE) {
            way_point_calendar_rl.setVisibility(View.VISIBLE);
            Drawable nav_up = getResources().getDrawable(R.drawable.arraw_up);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            calendar_today_tv.setCompoundDrawables(null, null, nav_up, null);
        } else {
            Drawable nav_up = getResources().getDrawable(R.drawable.arraw_down);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            calendar_today_tv.setCompoundDrawables(null, null, nav_up, null);
            way_point_calendar_rl.setVisibility(View.GONE);
        }
    }

    public void nextClick(View v) {
        String rightYearAndmonth = calendar.clickRightMonth();
        String[] ya = rightYearAndmonth.split("-");
        calendar_today_tv.setText(ya[0] + "年" + ya[1] + "月");
    }

    public void preClick(View v) {
        String leftYearAndmonth = calendar.clickLeftMonth();
        String[] ya = leftYearAndmonth.split("-");
        calendar_today_tv.setText(ya[0] + "年" + ya[1] + "月");
    }

    public void queryCorrectHistory(Date selectData) {
        //是否返回精简的结果（0 : 将只返回经纬度，1 : 将返回经纬度及其他属性信息）
        int simpleReturn = 0;
        //开始时间（Unix时间戳）

        String strDate = DateUtils.formatDate(selectData, DateUtils.yyyyMMDD);
        selectData = DateUtils.parseDate(strDate, DateUtils.yyyyMMDD);
        int startTime = (int) ((selectData.getTime()) / 1000 )+(int)(14.4*currentMinTime)*60;
        //结束时间（Unix时间戳）
        int endTime = (int) ((selectData.getTime()) / 1000)+(int)(14.4*currentMaxTime)*60;
        //分页大小
        int pageSize = 1000;
        //分页索引
        int pageIndex = 1;
        //轨迹查询监听器
        OnTrackListener trackListener = new OnTrackListener() {
            //请求失败回调接口
            @Override
            public void onRequestFailedCallback(String arg0) {
                System.out.println("track请求失败回调接口消息 : " + arg0);
            }

            // 查询历史轨迹回调接口
            @Override
            public void onQueryHistoryTrackCallback(String historyTrack) {

                LogUtils.d("查询历史轨迹回调接口消息 : " + historyTrack);
                HistoryTrackData historyTrackData = JSONUtils.parseJson(historyTrack,
                        HistoryTrackData.class);

                List<LatLng> latLngList = new ArrayList<LatLng>();
                if (historyTrackData != null && historyTrackData.getStatus() == 0) {
                    if (historyTrackData.getListPoints() != null) {
                        latLngList.addAll(historyTrackData.getListPoints());
                    }

                    if (ParamUtils.isEmpty(latLngList)) {
                        handler.sendEmptyMessage(0);
                        return;
                    }
                    // 绘制历史轨迹
                    EventBus.getDefault().post(new DrawHistoryEvent(latLngList,historyTrackData.distance,historyTrackData));
                }
                if(!ParamUtils.isNull(historyTrack) && historyTrackData.getStatus() != 0){
                    Message msg = handler.obtainMessage();
                    msg.what=5;
                    msg.obj = "状态:"+historyTrackData.getStatus()+"   消息:"+historyTrackData.getMessage();
                    handler.sendMessage(msg);
                }
            }

        };

        mBaiduMap.clear();
        String entityName = ParamUtils.isEmpty(cacheManager.getCurrentEntityName()) ? Config.getEntityName() :cacheManager.getCurrentEntityName() ;;
        //查询历史轨迹
        int isProcessed = 1;
        lbsTraceClient.queryProcessedHistoryTrack(Config.getServiceId(), entityName, simpleReturn, isProcessed, startTime, endTime, pageSize, pageIndex, trackListener);
        isCurrentQuery = false;
    }

    public void queryHistory(Date selectData) {
        //是否返回精简的结果（0 : 将只返回经纬度，1 : 将返回经纬度及其他属性信息）
        int simpleReturn = 0;
        //开始时间（Unix时间戳）

        this.selectDate = selectData;
        String strDate = DateUtils.formatDate(selectData, DateUtils.yyyyMMDD);
        selectData = DateUtils.parseDate(strDate, DateUtils.yyyyMMDD);
        int startTime = (int) ((selectData.getTime()) / 1000 )+(int)(14.4*0)*60;
        //结束时间（Unix时间戳）
        int endTime = (int) ((selectData.getTime()) / 1000)+(int)(14.4*100)*60;
        //分页大小
        int pageSize = 5000;
        //分页索引
        int pageIndex = 1;

        String entityName = ParamUtils.isEmpty(cacheManager.getCurrentEntityName()) ? Config.getEntityName() :cacheManager.getCurrentEntityName() ;
        //查询历史轨迹

        int serviceId = Config.getServiceId();

        LogUtils.d("serviceId:"+serviceId+",entityName:"+entityName+",simpleReturn:"+simpleReturn+",startTime:"+startTime+",endTime:"+endTime+",pageSize:"+pageSize+",pageIndex:"+pageIndex);
        if(isUseYingyanService()){
            queryHistoryByYingyan(serviceId,entityName,simpleReturn,startTime,endTime,pageSize,pageIndex);
            return;
        }

        queryHistoryByServer(serviceId,entityName,simpleReturn,startTime,endTime,pageSize,pageIndex);
    }

    private void queryHistoryByYingyan(int serviceId, String entityName, int simpleReturn, int startTime, int endTime, int pageSize, int pageIndex){

        //轨迹查询监听器
        OnTrackListener trackListener = new OnTrackListener() {
            //请求失败回调接口
            @Override
            public void onRequestFailedCallback(String arg0) {
                System.out.println("track请求失败回调接口消息 : " + arg0);
            }

            // 查询历史轨迹回调接口
            @Override
            public void onQueryHistoryTrackCallback(String historyTrack) {

                LogUtils.d("查询历史轨迹回调接口消息 : " + historyTrack);
                HistoryTrackData historyTrackData = JSONUtils.parseJson(historyTrack,
                        HistoryTrackData.class);

                List<LatLng> latLngList = new ArrayList<LatLng>();
                if (historyTrackData != null && historyTrackData.getStatus() == 0) {
                    if (historyTrackData.getListPoints() != null) {
                        latLngList.addAll(historyTrackData.getListPoints());
                    }

                    if (ParamUtils.isEmpty(latLngList)) {
                        handler.sendEmptyMessage(0);
                        return;
                    }
                    // 绘制历史轨迹
                    EventBus.getDefault().post(new DrawHistoryEvent(latLngList,historyTrackData.distance,historyTrackData));
                }
                if(!ParamUtils.isNull(historyTrack) && historyTrackData.getStatus() != 0){
                    Message msg = handler.obtainMessage();
                    msg.what=5;
                    msg.obj = "状态:"+historyTrackData.getStatus()+"   消息:"+historyTrackData.getMessage();
                    handler.sendMessage(msg);
                }
            }

        };
        lbsTraceClient.queryHistoryTrack(serviceId, entityName, simpleReturn, startTime, endTime, pageSize, pageIndex, trackListener);
    }
    private void queryHistoryByServer(int serviceId, String entityName, int simpleReturn, int startTime, int endTime, int pageSize, int pageIndex){
        QueryHistoryTrackParam param = new QueryHistoryTrackParam();
        param.setLoginToken(cacheManager.getCurrentToken());
        param.setServiceId(serviceId);
        param.setEntityName(entityName);
        param.setSimpleReturn(simpleReturn);
        param.setStartTime(startTime);
        param.setEndTime(endTime);
        param.setPageSize(pageSize);
        param.setPageIndex(pageIndex);
        apiManager.queryHistoryTrack(param);
    }

    public void onEventMainThread(QueryHistoryTrackResult result){
        if(result.getRetCode().equals(StatusCode.SUCCESS.getCode())){
            currentHistoryPoints =  result.getRetData().getPoints();
            List<LatLng> points = new ArrayList<>();
            List<HistoryTrackData.Points> historyPoints = new ArrayList<>();
            for(HistoryTrackData.Points historyPoint:currentHistoryPoints){
                if(historyPoint.getRectification()==1 && cacheManager.getShowStationNode().equals("false")){
                    continue;
                }
                LatLng latLng = new LatLng(historyPoint.getLocation().get(1), historyPoint.getLocation().get(0));
                points.add(latLng);
                historyPoints.add(historyPoint);
            }

            currentHistoryPoints = historyPoints;
            selectHistoryPoints = ParamUtils.isEmpty(currentHistoryPoints)?new ArrayList<HistoryTrackData.Points>():currentHistoryPoints;
            playTotalCount = selectHistoryPoints.size();
            playIndex = playTotalCount;
            drawHistoryTrack(result.getRetData().getListPoints(), 0, currentHistoryPoints,false);
        }
    }

    public void onEventMainThread(DrawHistoryEvent event){
        currentHistoryPoints = event.getHistoryTrackData().getPoints();
        List<LatLng> points = new ArrayList<>();
        List<HistoryTrackData.Points> historyPoints = new ArrayList<>();
        for(HistoryTrackData.Points historyPoint:currentHistoryPoints){
            if(historyPoint.getRectification()==1 && cacheManager.getShowStationNode().equals("false")){
                continue;
            }
            LatLng latLng = new LatLng(historyPoint.getLocation().get(1), historyPoint.getLocation().get(0));
            points.add(latLng);
            historyPoints.add(historyPoint);
        }

        currentHistoryPoints = historyPoints;
        selectHistoryPoints = ParamUtils.isEmpty(currentHistoryPoints)?new ArrayList<HistoryTrackData.Points>():currentHistoryPoints;
        playTotalCount = selectHistoryPoints.size();
        playIndex = playTotalCount;
        drawHistoryTrack(event.getLatLngList(), event.getDistance(), currentHistoryPoints, true);
    }

    public void drawChangeTime(Date selectData){
        if(ParamUtils.isEmpty(currentHistoryPoints)){
            mBaiduMap.clear();
            return;
        }

        String strDate = DateUtils.formatDate(selectData, DateUtils.yyyyMMDD);
        selectData = DateUtils.parseDate(strDate, DateUtils.yyyyMMDD);

        long minData = selectData.getTime()+(int)(14.4*currentMinTime)*60*1000;
        long maxData = selectData.getTime()+(int)(14.4*currentMaxTime)*60*1000;
        List<LatLng> points = new ArrayList<>();
        List<HistoryTrackData.Points> historyPoints = new ArrayList<>();

        for(HistoryTrackData.Points historyPoint:currentHistoryPoints){
            if(historyPoint.getRectification()==1 && cacheManager.getShowStationNode().equals("false")){
                continue;
            }
            Date pointDate = DateUtils.parseDate(historyPoint.getCreate_time(), DateUtils.yyyyMMddHHmmss);
            if(pointDate.getTime() >= minData && pointDate.getTime()<=maxData){
                historyPoints.add(historyPoint);
                LatLng latLng = new LatLng(historyPoint.getLocation().get(1), historyPoint.getLocation().get(0));
                points.add(latLng);
            }
        }

        Collections.sort(historyPoints, new Comparator<HistoryTrackData.Points>() {
            @Override
            public int compare(HistoryTrackData.Points lhs, HistoryTrackData.Points rhs) {

                try {
                    if(sdf.parse(rhs.create_time).before(sdf.parse(lhs.create_time))){
                        return -1;
                    }else{
                        return 1;
                    }
                }catch (Exception e){
                    return 0;
                }
            }
        });

        selectHistoryPoints = historyPoints;
        playTotalCount = selectHistoryPoints.size();
        playIndex = playTotalCount;
        drawHistoryTrack(points, 0, historyPoints, false);
    }

    /**
     * 绘制历史轨迹
     *
     * @param points
     */
    private void drawHistoryTrack(final List<LatLng> points, final double distance, List<HistoryTrackData.Points> historyPoints, boolean isShowToast) {
        // 绘制新覆盖物前，清空之前的覆盖物
        mBaiduMap.clear();

        if (points == null || points.size() == 0) {
            if(isShowToast){
                EventBus.getDefault().post(new ToastEvent("当前查询无轨迹点"));
            }
            resetMarker();
        } else if (points.size() > 1) {

            LatLng llC = points.get(0);
            LatLng llD = points.get(points.size() - 1);
            LatLngBounds bounds = new LatLngBounds.Builder()
                    .include(llC).include(llD).build();

            msUpdate = MapStatusUpdateFactory.newLatLngBounds(bounds);

            bmStart = BitmapDescriptorFactory.fromResource(R.drawable.icon_start);
            bmEnd = BitmapDescriptorFactory.fromResource(R.drawable.icon_end);

            // 添加起点图标
            startMarker = new MarkerOptions()
                    .position(points.get(points.size() - 1)).icon(bmStart)
                    .zIndex(9).draggable(true);

            // 添加终点图标
            endMarker = new MarkerOptions().position(points.get(0))
                    .icon(bmEnd).zIndex(9).draggable(true);

            pointsMap.put(points.get(points.size() - 1), historyPoints.get(points.size() - 1));
            pointsMap.put(points.get(0), historyPoints.get(0));

            // 添加路线（轨迹）
            polyline = new PolylineOptions().width(10)
                    .color(Color.BLUE).points(points);

            addMarker();

            if(Config.getBooleanValue("showstoppotin")){
                int index = 0;
                int stopCount = Config.getIntValue("stopcount");
                boolean isStartPoint = false;
                HistoryTrackData.Points startPoint = null,stopPoint = null,point = null;
                for (int i=(historyPoints.size()-1);i>=0;i--) {
                    point = historyPoints.get(i);

                    if(i != (historyPoints.size()-1)) {
                        startPoint = historyPoints.get(i + 1);
                        stopPoint = point;
                        if (!ParamUtils.isNull(startPoint)) {
                            try {
                                if(proType.toLowerCase().contains("hf309")) {
                                    if (!ParamUtils.isEmpty(point.getAlarmType())) {
                                        if (point.getAlarmType().equals("06")) {//停止节点
                                            if(startPoint.getIsSessionPoint().equals("1")){
                                                startPoint = historyPoints.get(i + 2);
                                                if(ParamUtils.isNull(startPoint)){
                                                    startPoint = historyPoints.get(i + 1);
                                                }
                                            }
                                            LatLng llStart = new LatLng(startPoint.getLocation().get(1), startPoint.getLocation().get(0));
                                            long middleTime = sdf.parse(stopPoint.create_time).getTime() - sdf.parse(startPoint.create_time).getTime();
                                            long hour = middleTime / (1000 * 60 * 60);
                                            long minute = (middleTime - (hour * 1000 * 60 * 60)) / (1000 * 60);
                                            String value = "停止开始时间:" + startPoint.create_time + "\n停留时间:" + hour + "小时 " + minute + "分钟";
                                            stopPoints.put(llStart, value);
                                            MarkerOptions marker = new MarkerOptions();
                                            mBaiduMap.addOverlay(marker.position(llStart).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                                        } else if (point.getAlarmType().contains("0")) {
                                            LatLng llStart = new LatLng(startPoint.getLocation().get(1), startPoint.getLocation().get(0));
                                            String value = "报警时间：" + point.create_time + "\n报警类型：" + modelsEntity.getAlarmTypeName(point.getAlarmType());
                                            stopPoints.put(llStart, value);
                                            MarkerOptions marker = new MarkerOptions();
                                            mBaiduMap.addOverlay(marker.position(llStart).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                                        }
                                    }
                                }else{
                                    if ((sdf.parse(stopPoint.create_time).getTime() - sdf.parse(startPoint.create_time).getTime()) >= Config.getIntValue("maxtime") * 60 * 1000) {//超过30分钟
                                        LatLng llStart = new LatLng(startPoint.getLocation().get(1), startPoint.getLocation().get(0));
                                        long middleTime = sdf.parse(stopPoint.create_time).getTime() - sdf.parse(startPoint.create_time).getTime();
                                        long hour = middleTime / (1000 * 60 * 60);
                                        long minute = (middleTime - (hour * 1000 * 60 * 60)) / (1000 * 60);
                                        String value = "停止开始时间:" + startPoint.create_time + "\n停留时间:" + hour + "小时 " + minute + "分钟";
                                        stopPoints.put(llStart, value);
                                        MarkerOptions marker = new MarkerOptions();
                                        mBaiduMap.addOverlay(marker.position(llStart).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if(point.getSpeed() == 0){
                        if(!isStartPoint){
                            isStartPoint = true;
                            startPoint = point;
                        }
                        index ++;
                    }else{
                        if(index >= stopCount){
                            try {
                                startPoint = historyPoints.get(i+index-1);
                                stopPoint = historyPoints.get(i+1);
                                LatLng llStart = new LatLng(startPoint.getLocation().get(1), startPoint.getLocation().get(0));

                                long middleTime = sdf.parse(stopPoint.create_time).getTime()-sdf.parse(startPoint.create_time).getTime();
                                long hour =  middleTime / (1000 * 60 * 60);
                                long minute =  (middleTime-(hour*1000 * 60 * 60)) / (1000 * 60);
                                String value = "停止开始时间:"+startPoint.create_time+"\n停留时间:" + hour + "小时 " + minute + "分钟";
                                stopPoints.put(llStart,value);
                                MarkerOptions marker = new MarkerOptions();
                                mBaiduMap.addOverlay(marker.position(llStart).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        index = 0;
                        startPoint = null;
                        stopPoint = null;
                        isStartPoint = false;
                    }
                }
            }

            if(Config.getBooleanValue("showdirection")) {

                int index = 0;
                for (HistoryTrackData.Points point : historyPoints) {

                    int direction = point.getDirection();
                    if (point.getDirection() == 0) {
                        continue;
                    }

                    if (index == interval) {
                        index = 0;
                        MarkerOptions marker = new MarkerOptions();
                        LatLng ll = new LatLng(point.getLocation().get(1), point.getLocation().get(0));
                        pointsMap.put(ll, point);
                        int resId = 0;
                        if (direction > 330 || direction < 30) {
                            resId = R.drawable.b;
                        }

                        if (direction >= 30 && direction <= 60) {
                            resId = R.drawable.db;
                        }

                        if (direction >= 60 && direction <= 120) {
                            resId = R.drawable.d;
                        }

                        if (direction >= 120 && direction <= 150) {
                            resId = R.drawable.dn;
                        }

                        if (direction >= 150 && direction <= 210) {
                            resId = R.drawable.n;
                        }

                        if (direction >= 210 && direction <= 240) {
                            resId = R.drawable.xn;
                        }

                        if (direction >= 240 && direction <= 300) {
                            resId = R.drawable.x;
                        }

                        if (direction >= 300 && direction <= 330) {
                            resId = R.drawable.xb;
                        }
                        if (resId != 0) {
                            mBaiduMap.addOverlay(marker.position(ll).icon(BitmapDescriptorFactory.fromResource(resId)));
                        }
                    }
                    index++;
                }
            }

            if(isShowToast){
                EventBus.getDefault().post(new ToastEvent("当前轨迹里程为 : " + (int) distance + "米"));
            }
        }

    }

    /**
     * 添加覆盖物
     */
    protected void addMarker() {

        if (null != msUpdate) {
            mBaiduMap.setMapStatus(msUpdate);
        }

        if (null != startMarker) {
            mBaiduMap.addOverlay(startMarker);
        }

        if (null != endMarker) {
            mBaiduMap.addOverlay(endMarker);
        }

        if (null != polyline) {
            mBaiduMap.addOverlay(polyline);
        }else{
            toast("polyline null");
        }

    }

    /**
     * 重置覆盖物
     */
    private void resetMarker() {
        startMarker = null;
        endMarker = null;
        polyline = null;
    }

    @Override
    public void notificationMessage(Message msg) {
        switch (msg.what) {
            case 0:
                mBaiduMap.clear();
                toast("没轨迹信息");
                break;
            case 1:
                toast("无法获取到位置信息");
                break;
            case 5:
                toast(msg.obj.toString());
                break;
            case 6:
                queryCorrectHistory(selectDate);
                break;
            case 7:
                palyPoint((HistoryTrackData.Points) msg.obj);
                break;
            case 8:
                btn_play.setText("播放");
                setProgressBarEnable(true);
                break;
            case 9:
                setProgressBarValue((int)msg.obj);
                break;
            case 10:
                palyPointByTouch((HistoryTrackData.Points) msg.obj);
                break;
            case 11:
                queryHistory(new Date());
                break;
            case 12:
                if(!ParamUtils.isNull(selectDate) && !isCurrentQuery){
                    isCurrentQuery = true;
                    drawChangeTime(selectDate);
                    isCurrentQuery = false;
                }
                break;
            case 13:
                play();
                break;
            case 15:
                timeSlider.setEnabled(false);
                break;
            case 16:
                timeSlider.setEnabled(true);
                break;
        }
    }

    boolean isCurrentQuery = false;

    public void showLocation(){
        BDLocation location = BeanFactory.getInstance().getBean(LemonLocation.class).getLocation();
        if(ParamUtils.isNull(location)){
            return;
        }

        MapStatusUpdate u1 = MapStatusUpdateFactory.zoomTo(13);
        mBaiduMap.animateMapStatus(u1);
        LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
        mBaiduMap.animateMapStatus(u);

        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isFinish = true;
        if(!ParamUtils.isNull(playThread)){
            playThread = null;
        }
        handler.removeCallbacksAndMessages(null);
        if(ParamUtils.isNull(mMapView)){
            return;
        }
        mMapView.onDestroy();
    }

}
