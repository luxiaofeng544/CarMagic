package com.lemon.carmonitor.ui;

import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.MKGeneralListener;
import com.baidu.lbsapi.panoramaview.PanoramaView;
import com.lemon.LemonActivity;
import com.lemon.annotation.FieldView;
import com.lemon.annotation.Layout;
import com.lemon.carmonitor.R;
import com.lemon.event.ToastEvent;
import com.lemon.util.ParamUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by xflu on 2017/12/5.
 */
@Layout(id= R.layout.pano_activity)
public class PanoActivity extends LemonActivity {

    @FieldView(id=R.id.panorama)
    public PanoramaView mPanoView;

    @Override
    public void beforeSetLayout() {
        initBMapManager();
    }

    @Override
    protected void initView() {
        double  lat = 24.786791864909;
        double lon = 113.30110631914;
//        mPanoView.setPanorama(lat,lon);
        mPanoView.setPanorama("0100220000130817164838355J5");
    }

    public BMapManager mBMapManager = null;

    private void initBMapManager() {
        if(ParamUtils.isNull(mBMapManager)){
            mBMapManager = new BMapManager(getApplicationContext());
            mBMapManager.init(new PanoActivity.MyGeneralListener());
        }
        if (!mBMapManager.init(new MyGeneralListener())) {
            EventBus.getDefault().post(new ToastEvent("Pano BMapManager  初始化错误!"));
        }
    }

    // 常用事件监听，用来处理通常的网络错误，授权验证错误等
    public static class MyGeneralListener implements MKGeneralListener {

        @Override
        public void onGetPermissionState(int iError) {
            // 非零值表示key验证未通过
            if (iError != 0) {
                // 授权Key错误：
                EventBus.getDefault().post(new ToastEvent("请在AndoridManifest.xml中输入正确的授权Key,并检查您的网络连接是否正常！error: " + iError));
            } else {
                EventBus.getDefault().post(new ToastEvent("Pano key认证成功"));
            }
        }
    }
}
