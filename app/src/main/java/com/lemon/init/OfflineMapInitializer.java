package com.lemon.init;

import android.util.Log;

import com.baidu.mapapi.NetworkUtil;
import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.lemon.config.Config;
import com.lemon.event.OfflineMapEvent;
import com.lemon.event.StartLocationEvent;
import com.lemon.util.NetUtil;
import com.lemon.util.ParamUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 *
 */
public class OfflineMapInitializer  extends AbstractInitializer implements MKOfflineMapListener {

    private MKOfflineMap mOffline;
    private int time = 0;

    @Override
    public Object initialize(Object... objects) throws Exception {
        EventBus.getDefault().register(this);
        mOffline = new MKOfflineMap();
        mOffline.init(this);
        EventBus.getDefault().post(new StartLocationEvent(true));
        return null;
    }

    @Subscribe
    public void onEventAsync(OfflineMapEvent event){
        if(ParamUtils.isNull(event.getItem())){
            if(time<= Config.getIntValue("max_location_times")){
                EventBus.getDefault().post(new StartLocationEvent(true));
            }
            time++;
            return;
        }

        time = 0;
        List<MKOLUpdateElement> cities = mOffline.getAllUpdateInfo();
        boolean isDownload = false;
        for (MKOLUpdateElement record:cities){
            if(record.cityID == event.getItem().getCityCode()){
                isDownload = !isDownload;
                break;
            }
        }

        if(!isDownload){
            if(NetUtil.isWifi(mContext) || NetUtil.is4G(mContext)) {
                mOffline.start(event.getItem().getCityCode());
            }
        }
    }

    @Override
    public void onGetOfflineMapState(int type, int state) {
        Log.d("OfflineMapInitializer", String.format("add offlinemap type:%d ,state:%d",type, state));
    }
}
