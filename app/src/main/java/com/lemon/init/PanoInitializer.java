package com.lemon.init;

import android.content.Context;

import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.MKGeneralListener;
import com.lemon.event.ToastEvent;
import com.lemon.util.ParamUtils;

import org.greenrobot.eventbus.EventBus;


/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.init]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/1/18 12:59]
 * 修改人:    [xflu]
 * 修改时间:  [2016/1/18 12:59]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class PanoInitializer extends AbstractInitializer {
    public Context mContext;

    public BMapManager mBMapManager = null;

    private void initBMapManager() {
        if(ParamUtils.isNull(mBMapManager)){
            mBMapManager = new BMapManager(mContext);
        }
        if (!mBMapManager.init(new MyGeneralListener())) {
            EventBus.getDefault().post(new ToastEvent("Pano BMapManager  初始化错误!"));
        }
    }

    @Override
    public Object initialize(Object... objects) throws Exception {
        initBMapManager();
        return null;
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