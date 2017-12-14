package com.lemon.carmonitor.ui;

import android.view.View;

import com.lemon.LemonActivity;
import com.lemon.annotation.Layout;
import com.lemon.annotation.OnClick;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.baiduoffline.BaiduOfflineMapActivity;


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
        startNextActivity(PanoActivity.class,false);
    }

}
