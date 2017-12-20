package com.lemon.carmonitor.umeng;

import android.content.Context;

import com.lemon.annotation.Autowired;
import com.lemon.annotation.Component;
import com.lemon.annotation.InitMethod;
import com.lemon.config.Config;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

@Component
public class UMengInitializer {

    @Autowired
    public Context mContext;

    @InitMethod
    public void initialize()  {
        UMConfigure.init(mContext, UMConfigure.DEVICE_TYPE_PHONE, Config.getValue("umeng_appkey"));
        UMConfigure.setLogEnabled(true);
        UMConfigure.setEncryptEnabled(false);
        MobclickAgent.onProfileSignIn("13850046527");
    }

}
