package com.lemon.carmonitor.old.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.baidu.mapapi.utils.poi.BaiduMapPoiSearch;
import com.baidu.mapapi.utils.poi.PoiParaOption;
import com.lemon.LemonActivity;
import com.lemon.LemonLocation;
import com.lemon.bean.BeanFactory;
import com.lemon.carmonitor.R;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.ui]
 * 类描述:    [周边导航主页]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2015/12/21 20:55]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2015/12/21 20:55]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class NavigationActivity extends LemonActivity {


    // 天安门坐标
    double mLat1 = 39.915291;
    double mLon1 = 116.403857;


    @Override
    protected void setLayout() {
        setLayoutValue(R.layout.navigation);
    }

    public void parkinglotClick(View v) {
        startPoiNearbySearch("停车场");
    }

    public void bankClick(View v) {
        startPoiNearbySearch("银行");
    }

    public void foodClick(View v) {
        startPoiNearbySearch("美食");
    }

    public void hotelClick(View v) {
        startPoiNearbySearch("酒店");
    }

    public void stationClick(View v) {
        startPoiNearbySearch("加油站");
    }

    public void hospitalClick(View v) {
        startPoiNearbySearch("医院");
    }

    public void busClick(View v) {
        startPoiNearbySearch("公交站");
    }

    public void supermarketClick(View v) {
        startPoiNearbySearch("超市");
    }

    public void resortClick(View v) {
        startPoiNearbySearch("娱乐场");
    }

    /**
     * 启动百度地图Poi周边检索
     *
     */
    public void startPoiNearbySearch(String key) {
        LemonLocation lemonLocation = BeanFactory.getInstance().getBean(LemonLocation.class);
        LatLng pt_center;
        if(lemonLocation.currentLocation == null){
            pt_center = new LatLng(mLat1, mLon1); // 天安门
        }else{
            pt_center = new LatLng(lemonLocation.currentLocation.getLatitude(), lemonLocation.currentLocation.getLongitude()); // 天安门
        }
        PoiParaOption para = new PoiParaOption()
                .key(key)
                .center(pt_center)
                .radius(2000);

        try {
            BaiduMapPoiSearch.openBaiduMapPoiNearbySearch(para, this);
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
                OpenClientUtil.getLatestBaiduMapApp(NavigationActivity.this);
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

}
