package com.lemon.carmonitor.manager;

import android.content.Context;

import com.lemon.LemonActivityManager;
import com.lemon.annotation.Autowired;
import com.lemon.annotation.Component;
import com.lemon.annotation.InitMethod;
import com.lemon.carmonitor.api.ApiManager;
import com.lemon.carmonitor.event.WakeUpEvent;
import com.lemon.carmonitor.model.bean.DeviceInfo;
import com.lemon.carmonitor.model.bean.protocol.SuppliersEntity;
import com.lemon.carmonitor.model.param.IssueCmdParam;
import com.lemon.carmonitor.util.AppCacheManager;
import com.lemon.config.Config;
import com.lemon.util.PhoneUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.manager]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/2/20 14:16]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/2/20 14:16]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Component
public class WakeManager {

    @Autowired
    public AppCacheManager appCacheManager;

    @Autowired
    public ApiManager apiManager;

    @Autowired
    public Context mContext;

    @Autowired
    public LemonActivityManager lemonActivityManager;

    @InitMethod
    public void init(){
        EventBus.getDefault().register(this);
    }

    public void wakeUp(String devSn){
        if(!appCacheManager.containBean(devSn)){
            return;
        }
        DeviceInfo deviceInfo = (DeviceInfo) appCacheManager.getBean(devSn);
        IssueCmdParam param = new IssueCmdParam();
        param.setDevSn(devSn);
        param.setCmd("wakeup");
        param.setLoginToken(appCacheManager.getCurrentToken());

        if(!Config.getBooleanValue("wake_sms")){
            apiManager.issueCmd(param);
            return;
        }

        if(!appCacheManager.containBean(SuppliersEntity.class.getSimpleName()+":"+deviceInfo.getDevProtocol())){
            return;
        }
        SuppliersEntity suppliersEntity = (SuppliersEntity) appCacheManager.getBean(SuppliersEntity.class.getSimpleName()+":"+deviceInfo.getDevProtocol());
        if(suppliersEntity.getIotcard_type().contains(deviceInfo.getDevModel())){//物联网卡
            apiManager.issueCmd(param);
        }else {
            PhoneUtil.sendMessage(lemonActivityManager.getCurrentActivity(),deviceInfo.getDevPhoneNum(),"WAKE#");
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(WakeUpEvent event){
        wakeUp(event.getDevSn());
    }
}
