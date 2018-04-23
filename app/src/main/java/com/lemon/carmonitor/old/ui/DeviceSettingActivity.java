package com.lemon.carmonitor.old.ui;

import android.content.Intent;
import android.view.View;

import com.lemon.LemonActivity;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.old.ui.DeviceInfoActivity;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.ui]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2015/12/21 20:39]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2015/12/21 20:39]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class DeviceSettingActivity extends LemonActivity {
    @Override
    protected void setLayout() {
        layout = R.layout.activity_device_setting;
    }

    @Override
    protected void initView() {
        if(!checkDevice()){
            finish();
            return;
        }
    }

    public void deviceInfoClick(View v) {
        startNextActivity(DeviceInfoActivity.class, false);
    }

    public void gprsClick(View v){
        cacheManager.putBean("command_connection_type","gprs");
        Intent intent = new Intent(mContext,CommandCategoryActivity.class);
        intent.putExtra("type","gprs");
        startNextActivity(intent, false);
    }

    public void smsClick(View v){
        cacheManager.putBean("command_connection_type","sms");
        Intent intent = new Intent(mContext,CommandCategoryActivity.class);
        intent.putExtra("type","sms");
        startNextActivity(intent, false);
    }

    public void alarmClick(View v) {
        startNextActivity(AlarmsetActivity.class, false);
    }

    public void workModelClick(View v) {
        startNextActivity(DeviceWorkModelSettingActivity.class, false);
    }

    public void deviceClick(View v) {
        startNextActivity(DeviceCmdSettingActivity.class, false);
    }

    public void baseClick(View v) {
        startNextActivity(DeviceBaseSettingActivity.class, false);
    }

    public void phoneClick(View v) {

        startNextActivity(DevicePhoneSettingActivity.class, false);
    }

}
