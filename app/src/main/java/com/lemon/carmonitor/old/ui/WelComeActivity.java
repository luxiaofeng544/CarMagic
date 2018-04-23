package com.lemon.carmonitor.old.ui;

import android.content.Intent;

import com.lemon.LemonActivity;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.model.param.AppUpdateParam;
import com.lemon.carmonitor.ui.MainActivity;
import com.lemon.carmonitor.ui.PanoActivity;

import cn.jpush.android.api.JPushInterface;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.ui]
 * 类描述:    [欢迎页面,加载数据]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2015/12/21 20:51]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2015/12/21 20:51]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class WelComeActivity extends LemonActivity {
    @Override
    protected void setLayout() {
        setLayoutValue(R.layout.loading);
    }

    private static final int MAIN_WHAT = 1;
    private static final int GUIDE_WHAT = 2;
    private static final int GUIDE_PANO = 3;

    @Override
    public void notificationMessage(android.os.Message msg) {
        switch (msg.what) {
            case MAIN_WHAT:
                startActivity(new Intent(WelComeActivity.this,MainActivity.class));
                finish();
                break;
            case GUIDE_WHAT:
                startActivity(new Intent(WelComeActivity.this,NewLoginActivity.class));
                finish();
                break;
            case GUIDE_PANO:
                startActivity(new Intent(WelComeActivity.this, PanoActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void init() {
        AppUpdateParam param = new AppUpdateParam();
        apiManager.updateNew(param);
        param.setShowDialog(false);
        handler.sendEmptyMessageDelayed(GUIDE_WHAT, 1000);
    }


    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(mContext);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(mContext);
    }
}
