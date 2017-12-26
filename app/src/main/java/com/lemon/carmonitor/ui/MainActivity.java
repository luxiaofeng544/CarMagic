package com.lemon.carmonitor.ui;


import android.os.Message;

import com.lemon.LemonActivity;
import com.lemon.annotation.FieldView;
import com.lemon.annotation.Layout;
import com.lemon.annotation.OnClick;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.baiduoffline.BaiduOfflineMapActivity;
import com.lemon.event.StartLocationEvent;
import org.greenrobot.eventbus.EventBus;

import me.next.slidebottompanel.SlideBottomPanel;


@Layout(id=R.layout.activity_slidebottom)
public class MainActivity extends LemonActivity {

    @FieldView(id=R.id.sbv)
    public SlideBottomPanel slideBottomPanel;

    @Override
    protected void initData() {
        handler.sendEmptyMessageDelayed(0,1000);
    }

    @OnClick(id=R.id.btnMap)
    public void mapClick(){
        toastMessage("mapClick");
        //startNextActivity(BaiduOfflineMapActivity.class,false);
        slideBottomPanel.hide();
    }

    @OnClick(id=R.id.btnPanoMap)
    public void panoClick(){
        toastMessage("panoClick");
        slideBottomPanel.displayPanel();
    }

    @Override
    public void notificationMessage(Message msg) {
        EventBus.getDefault().post(new StartLocationEvent(true));
    }
}
