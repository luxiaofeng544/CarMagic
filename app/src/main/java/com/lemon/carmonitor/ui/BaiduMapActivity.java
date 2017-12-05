package com.lemon.carmonitor.ui;

import com.baidu.mapapi.map.MapView;
import com.lemon.LemonActivity;
import com.lemon.annotation.FieldView;
import com.lemon.annotation.Layout;
import com.lemon.carmonitor.R;

/**
 * Created by xflu on 2017/12/5.
 */
@Layout(id= R.layout.baidumap_activity)
public class BaiduMapActivity extends LemonActivity {
    @FieldView(id=R.id.bmapView)
    public MapView mapView;

    @Override
    protected void initView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
