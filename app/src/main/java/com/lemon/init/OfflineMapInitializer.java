package com.lemon.init;

import android.util.Log;

import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.lemon.event.OfflineMapEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 *
 */
public class OfflineMapInitializer  extends AbstractInitializer implements MKOfflineMapListener {

    private MKOfflineMap mOffline;

    @Override
    public Object initialize(Object... objects) throws Exception {
        EventBus.getDefault().register(this);
        mOffline = new MKOfflineMap();
        mOffline.init(this);
        return null;
    }

    @Subscribe
    public void onEventAsync(OfflineMapEvent event){
        List<MKOLUpdateElement> cities = mOffline.getAllUpdateInfo();
        boolean isDownload = false;
        for (MKOLUpdateElement record:cities){
            if(record.cityID == event.getItem().getCityCode()){
                isDownload = !isDownload;
                break;
            }
        }

        if(!isDownload){
            mOffline.start(event.getItem().getCityCode());
        }
    }

    @Override
    public void onGetOfflineMapState(int type, int state) {
        Log.d("OfflineMapInitializer", String.format("add offlinemap type:%d ,state:%d",type, state));
    }
}
