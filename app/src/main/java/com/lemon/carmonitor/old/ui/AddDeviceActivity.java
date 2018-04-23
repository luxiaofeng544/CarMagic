package com.lemon.carmonitor.old.ui;

import android.view.View;

import com.lemon.LemonActivity;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.contant.StatusCode;
import com.lemon.carmonitor.model.param.AppUserAddDevParam;
import com.lemon.carmonitor.model.result.AppUserAddDevResult;
import com.lemon.config.Config;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.ui]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2015/12/21 21:02]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2015/12/21 21:02]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class AddDeviceActivity extends LemonActivity {
    @Override
    protected void setLayout() {
        setLayoutValue(R.layout.activity_adddevice);
    }

    @Override
    protected void initData() {
        if(Config.isDebug()){
            setEditTextValue(R.id.et_sn, Config.getValue("dev_sn"));
        }
    }

    public void addClick(View v){
        if(isEditTextEmpty(R.id.et_sn)){
            toast("请输入设备号");
            return;
        }

        AppUserAddDevParam param = new AppUserAddDevParam();
        param.setLoginToken(cacheManager.getCurrentToken());
        param.setDevSn(getEditTextValue(R.id.et_sn));
        apiManager.appUserAddDev(param);
    }

    public void onEventMainThread(AppUserAddDevResult result){
        if(result.getRetCode().equals(StatusCode.SUCCESS.getCode())){
            toast("添加成功");
            finish();
            return;
        }else{
            toast(result.getRetMsg());
        }
    }

}
