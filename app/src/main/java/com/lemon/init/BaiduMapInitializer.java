package com.lemon.init;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

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
public class BaiduMapInitializer extends AbstractInitializer implements MKOfflineMapListener {

    private MKOfflineMap mOffline;
    private int time = 0;

    @Override
    public Object initialize(Object... objects) throws Exception {
        EventBus.getDefault().register(this);
        mOffline = new MKOfflineMap();
        mOffline.init(this);
        handler.sendEmptyMessageDelayed(0,60000);
        return null;
    }

    @Subscribe
    public void onEventAsync(OfflineMapEvent event){
        if(ParamUtils.isNull(event.getItem())){
            if(time<= Config.getIntValue("max_location_times")){
                handler.sendEmptyMessageDelayed(0,10000);
            }
            time++;
            return;
        }

        time = 0;
        List<MKOLUpdateElement> cities = mOffline.getAllUpdateInfo();
        boolean isDownload = false;
        for (MKOLUpdateElement record:cities){
            if(record.cityID == event.getItem().getCityCode()){
                if(record.status == MKOLUpdateElement.FINISHED){
                    isDownload = !isDownload;
                    if(record.update){
                        mOffline.update(record.cityID);
                    }
                }else if(record.status == MKOLUpdateElement.DOWNLOADING){
                    mOffline.start(record.cityID);
                }else{
                    mOffline.remove(record.cityID);
                    record.status = MKOLUpdateElement.UNDEFINED;
                }
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
        Log.d("BaiduMapInitializer", String.format("add offlinemap type:%d ,state:%d",type, state));
        switch (type) {
            case MKOfflineMap.TYPE_DOWNLOAD_UPDATE:
                MKOLUpdateElement update = mOffline.getUpdateInfo(state);
                Log.d("BaiduMapInitializer", String.format("下载比例 :%d , 下载状态:%d",update.ratio,update.status));
                if(update.ratio == 100){
                    Log.d("BaiduMapInitializer", String.format("下载比例 :%d , 下载状态:%d",update.ratio,update.status));
                }
                break;

            case MKOfflineMap.TYPE_NEW_OFFLINE:
                // 有新离线地图安装
                Log.d("OfflineDemo", String.format("add offlinemap num:%d", state));
                break;

            case MKOfflineMap.TYPE_VER_UPDATE:
                // 版本更新提示
                break;
        }
    }

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            EventBus.getDefault().post(new StartLocationEvent(true));
        }
    };
}
