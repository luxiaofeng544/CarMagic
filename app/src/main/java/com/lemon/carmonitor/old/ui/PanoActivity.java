package com.lemon.carmonitor.old.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.MKGeneralListener;
import com.baidu.lbsapi.panoramaview.PanoramaView;
import com.baidu.lbsapi.panoramaview.PanoramaViewListener;
import com.lemon.bean.BeanFactory;
import com.lemon.carmonitor.R;
import com.lemon.init.PanoInitializer;
import com.lemon.util.LogUtils;

/**
 * 全景Demo主Activity
 */
public class PanoActivity extends Activity {

    private static final String LTAG = com.lemon.carmonitor.ui.PanoActivity.class.getSimpleName();
    public static BMapManager mBMapManager = null;
    private PanoramaView mPanoView;

    private void initBMapManager() {
        PanoInitializer pano = BeanFactory.getInstance().getBean("panoInitializer");
        if (pano.mBMapManager == null) {
            pano.mBMapManager = new BMapManager(getApplicationContext());
            pano.mBMapManager.init(new PanoInitializer.MyGeneralListener());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initBMapManager();
        setContentView(R.layout.activity_pano);
        initView();
    }

    // 常用事件监听，用来处理通常的网络错误，授权验证错误等
    static class MyGeneralListener implements MKGeneralListener {

        @Override
        public void onGetPermissionState(int iError) {
            // 非零值表示key验证未通过
            if (iError != 0) {
                // 授权Key错误：
                LogUtils.e("请在AndoridManifest.xml中输入正确的授权Key,并检查您的网络连接是否正常！error: " + iError);
            } else {
                LogUtils.e("key认证成功");
            }
        }
    }

    protected void initView() {
        // 先初始化BMapManager
        mPanoView = (PanoramaView) findViewById(R.id.panorama);
        double lat = getIntent().getDoubleExtra("lat",0.0);
        double lon = getIntent().getDoubleExtra("lon",0.0);
        showPano(lat,lon);
    }

    private void showPano(double lat,double lon) {
        mPanoView.setShowTopoLink(true);
        // 测试回调函数,需要注意的是回调函数要在setPanorama()之前调用，否则回调函数可能执行异常
        mPanoView.setPanoramaViewListener(new PanoramaViewListener() {
            @Override
            public void onLoadPanoramaBegin() {
                Log.i(LTAG, "onLoadPanoramaStart...");
            }

            @Override
            public void onLoadPanoramaEnd(String json) {
                Log.i(LTAG, "onLoadPanoramaEnd : " + json);
            }

            @Override
            public void onLoadPanoramaError(String error) {
                Log.i(LTAG, "onLoadPanoramaError : " + error);
            }

            @Override
            public void onDescriptionLoadEnd(String json) {

            }

            @Override
            public void onMessage(String msgName, int msgType) {

            }

            @Override
            public void onCustomMarkerClick(String key) {

            }
        });

            mPanoView.setPanorama(lon, lat);
    }

    public void backClick(View view){
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mPanoView.destroy();
        super.onDestroy();
    }

}
