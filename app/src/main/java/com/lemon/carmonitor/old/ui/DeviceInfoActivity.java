package com.lemon.carmonitor.old.ui;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.lemon.LemonActivity;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.contant.StatusCode;
import com.lemon.carmonitor.model.bean.DeviceInfo;
import com.lemon.carmonitor.model.bean.protocol.SuppliersEntity;
import com.lemon.carmonitor.model.param.AppUserDelDevParam;
import com.lemon.carmonitor.model.param.GetUserDevParam;
import com.lemon.carmonitor.model.param.ModifyDevInfoParam;
import com.lemon.carmonitor.model.result.AppUserDelDevResult;
import com.lemon.carmonitor.model.result.GetUserDevResult;
import com.lemon.carmonitor.model.result.ModifyDevInfoResult;
import com.lemon.carmonitor.ui.*;
import com.lemon.config.Config;
import com.lemon.event.ToastEvent;
import com.lemon.util.ParamUtils;

import org.greenrobot.eventbus.EventBus;


/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.ui]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2015/12/21 20:43]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2015/12/21 20:43]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class DeviceInfoActivity extends LemonActivity {
    private Button button_save;
    private EditText et_devicename,et_devicesn,et_deviceexpreid,et_devicecarno,et_devicemodel,et_devicephone,et_contact,et_contactphone;
    private boolean isEdit = false;
    private LinearLayout llDevicePhone;

    @Override
    protected void setLayout() {
        setLayoutValue(R.layout.deviceinfo);
    }

    @Override
    protected void initView() {
        if(!checkDevice()){
            finish();
            return;
        }
        et_devicename = findControl(R.id.et_devicename);
        et_devicesn = findControl(R.id.et_devicesn);
        et_deviceexpreid = findControl(R.id.et_deviceexpreid);
        et_devicecarno = findControl(R.id.et_devicecarno);
        et_devicemodel = findControl(R.id.et_devicemodel);
        et_devicephone = findControl(R.id.et_devicephone);
        et_contact = findControl(R.id.et_contact);
        et_contactphone = findControl(R.id.et_contactphone);
        button_save = findControl(R.id.button_save);
        llDevicePhone = findControl(R.id.llDevicePhone);

        DeviceInfo deviceInfo = (DeviceInfo) cacheManager.getBean(cacheManager.getCurrentDevSn());
        SuppliersEntity suppliersEntity = (SuppliersEntity) cacheManager.getBean(SuppliersEntity.class.getSimpleName()+":"+deviceInfo.getDevProtocol());
        if(suppliersEntity.getIotcard_type().contains(deviceInfo.getDevModel())){//物联网卡
            llDevicePhone.setVisibility(View.GONE);
        }

        setEtEnable(false);
    }

    @Override
    protected void initData() {
        GetUserDevParam param = new GetUserDevParam();
        param.setLoginToken(cacheManager.getCurrentToken());
        param.setDevSn(ParamUtils.isEmpty(cacheManager.getCurrentDevSn())&& Config.isDebug()? Config.getValue("dev_sn"):cacheManager.getCurrentDevSn());
        apiManager.getUserDev(param);
    }

    public void editClick(View v){

        if(isEdit){
            button_save.setBackgroundResource(R.drawable.edit);
            setEtEnable(false);
            saveUserInfo();
        }else {
            button_save.setBackgroundResource(R.drawable.save);
            setEtEnable(true);
        }

        isEdit = !isEdit;
    }

    private void saveUserInfo() {
        ModifyDevInfoParam param = new ModifyDevInfoParam();
        param.setLoginToken(cacheManager.getCurrentToken());
        param.setDevSn(getEditTextValue(R.id.et_devicesn));
        param.setDevName(getEditTextValue(R.id.et_devicename));
        param.setCarNum(getEditTextValue(R.id.et_devicecarno));
        param.setDevPhoneNum(getEditTextValue(R.id.et_devicephone));
        param.setContractName(getEditTextValue(R.id.et_contact));
        param.setContractNum(getEditTextValue(R.id.et_contactphone));
        apiManager.modifyDevInfo(param);
    }

    public void unbindClick(View view){
        AppUserDelDevParam param = new AppUserDelDevParam();
        param.setLoginToken(cacheManager.getCurrentToken());
        param.setDevSn(cacheManager.getCurrentDevSn());
        apiManager.appUserDelDev(param);
    }

    public void onEventMainThread(AppUserDelDevResult result){
        if(result.getRetCode().equals(StatusCode.SUCCESS.getCode())){
            cacheManager.setCurrentDevSn("");
            EventBus.getDefault().post(new ToastEvent("解绑成功,请重新选择设备"));
            finish();
        }else{
            EventBus.getDefault().post(new ToastEvent(result.getRetMsg()));
        }
    }

    public void onEventMainThread(GetUserDevResult result){
        if(result.getRetCode().equals(StatusCode.SUCCESS.getCode())){
            DeviceInfo data = result.getRetData();
            setDeviceInfo(data);
        }else {
            toast(result.getRetMsg());
        }
    }

    public void onEventMainThread(ModifyDevInfoResult result){
        if(result.getRetCode().equals(StatusCode.SUCCESS.getCode())){
            toast("保存成功");
        }else {
            toast("保存失败");
        }
    }

    private void setEtEnable(boolean enable){
        et_devicename.setEnabled(enable);
        et_devicesn.setEnabled(false);
        et_deviceexpreid.setEnabled(false);
        et_devicecarno.setEnabled(enable);
        et_devicemodel.setEnabled(false);
        et_devicephone.setEnabled(enable);
        et_contact.setEnabled(enable);
        et_contactphone.setEnabled(enable);
    }

    private void setDeviceInfo(DeviceInfo info){
        et_devicename.setText(info.getDevName());
        et_devicesn.setText(info.getDevSn());
        et_deviceexpreid.setText(info.getValidDate());
        et_devicecarno.setText(info.getCarNum());
        et_devicemodel.setText(info.getDevModel());
        et_devicephone.setText(info.getDevPhoneNum());
        et_contact.setText(info.getContractName());
        et_contactphone.setText(info.getContractNum());
    }
}
