package com.lemon.carmonitor.jpush;

import android.content.Context;

import com.lemon.LemonContext;
import com.lemon.LemonDaoManager;
import com.lemon.annotation.Autowired;
import com.lemon.annotation.Component;
import com.lemon.annotation.InitMethod;
import com.lemon.bean.BeanFactory;
import com.lemon.carmonitor.contant.StatusCode;
import com.lemon.carmonitor.event.ChangeDeviceEvent;
import com.lemon.carmonitor.model.bean.DeviceInfo;
import com.lemon.carmonitor.model.result.AppUserLoginResult;
import com.lemon.carmonitor.model.result.GetUserDevsResult;
import com.lemon.carmonitor.util.AppCacheManager;
import com.lemon.util.ParamUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

@Component
public class JpushInitializer {
    @Autowired
    public Context mContext;
    AppCacheManager appCacheManager ;
    LemonDaoManager daoManager ;

    @InitMethod
    public void initialize()  {
        EventBus.getDefault().register(this);
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(mContext);     		// 初始化 JPush
        appCacheManager = LemonContext.getBean(AppCacheManager.class);
        daoManager = LemonContext.getBean(LemonDaoManager.class);
        JPushInterface.setAlias(mContext, "13850046527", null);
    }

    @Subscribe(threadMode= ThreadMode.ASYNC)
    public void onEventMainThread(AppUserLoginResult result){
        if(result.getRetCode().equals(StatusCode.SUCCESS.getCode())){
            AppCacheManager appCacheManager = BeanFactory.getInstance().getBean(AppCacheManager.class);
            JPushInterface.setAlias(mContext, appCacheManager.getCurrentMobile(), null);
        }
    }

    @Subscribe(threadMode= ThreadMode.ASYNC)
    public void onEventMainThread(GetUserDevsResult result){
        if(result.getRetCode().equals(StatusCode.SUCCESS.getCode())){
            Set<String> tagSet = new LinkedHashSet<String>();
            for(DeviceInfo deviceInfo:result.getRetData().getDevList()){
                tagSet.add(deviceInfo.getDevSn());
                appCacheManager.putBean(deviceInfo.getDevSn(),deviceInfo);
            }
            if(!ParamUtils.isEmpty(result.getRetData().getDevList())){
                if(ParamUtils.isEmpty(appCacheManager.getCurrentDevSn())){
                    DeviceInfo deviceInfo = result.getRetData().getDevList().get(0);
                    appCacheManager.setCurrentDevSn(deviceInfo.getDevSn());
                    appCacheManager.setCurrentEntityName(deviceInfo.getTraceEntityName());
                    EventBus.getDefault().post(new ChangeDeviceEvent(deviceInfo));
                }
            }
            JPushInterface.setTags(mContext,tagSet,null);
            List<DeviceInfo> deviceInfos = result.getRetData().getDevList();
            /*for (DeviceInfo deviceInfo:deviceInfos){
                daoManager.create(DeviceInfo.class,deviceInfo);
            }*/
            if(ParamUtils.isNull(deviceInfos)){
                deviceInfos = new ArrayList<>();
            }
            appCacheManager.putBean("deviceLists",deviceInfos);
        }
    }
}
