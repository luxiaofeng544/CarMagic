package com.lemon.carmonitor.ui;

import android.util.Log;
import android.view.View;

import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.lemon.LemonActivity;
import com.lemon.annotation.Layout;
import com.lemon.annotation.OnClick;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.baiduoffline.BaiduOfflineMapActivity;
import com.lemon.event.StartLocationEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


@Layout(id=R.layout.activity_main)
public class MainActivity extends LemonActivity {


    @OnClick(id=R.id.btnMap)
    public void mapClick(){
        toastMessage("mapClick");
        startNextActivity(BaiduOfflineMapActivity.class,false);
    }

    @OnClick(id=R.id.btnPanoMap)
    public void panoClick(){
        toastMessage("panoClick");

        EventBus.getDefault().post(new StartLocationEvent());
        MKOfflineMap mOffline = new MKOfflineMap();
        mOffline.init(new MKOfflineMapListener() {
            @Override
            public void onGetOfflineMapState(int type, int state) {
                Log.d("OfflineDemo", String.format("add offlinemap num:%d", state));
            }
        });
        List<MKOLUpdateElement> cities = mOffline.getAllUpdateInfo();
        boolean isDownload = false;
        for (MKOLUpdateElement record:cities){
            if(record.cityID == 194){
                isDownload = !isDownload;
                break;
            }
        }

        if(!isDownload){
            mOffline.start(194);
        }
    }

}
