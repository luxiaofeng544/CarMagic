package com.lemon.carmonitor.old.ui;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.lemon.LemonActivity;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.contant.StatusCode;
import com.lemon.carmonitor.model.bean.AppAlarmSet;
import com.lemon.carmonitor.model.param.AppUserAlarmSettingParam;
import com.lemon.carmonitor.model.param.GetAppUserSettingsParam;
import com.lemon.carmonitor.model.result.AppUserAlarmSettingResult;
import com.lemon.carmonitor.model.result.GetAppUserSettingsResult;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.ui]
 * 类描述:    [报警提醒]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2015/12/21 20:37]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2015/12/21 20:37]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class AppAlarmsetActivity extends LemonActivity {

    private CheckBox checkBox_alarmAlert, checkBox_alertSound, checkBox_alertVibration,checkBox_enableSms;
    private Button button_save;
    private boolean isEdit = false;

    @Override
    protected void setLayout() {
        layout = R.layout.app_alarmset;
    }

    @Override
    protected void initView() {
        button_save = findControl(R.id.button_save);
        checkBox_alarmAlert = findControl(R.id.checkBox_alarmAlert);
        checkBox_alertSound = findControl(R.id.checkBox_alertSound);
        checkBox_alertVibration = findControl(R.id.checkBox_alertVibration);
        checkBox_enableSms = findControl(R.id.checkBox_enableSms);

        checkBox_alarmAlert.setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox_alertSound.setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox_alertVibration.setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox_enableSms.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    @Override
    protected void initData() {
        setCkEnable(false);
        GetAppUserSettingsParam param = new GetAppUserSettingsParam();
        param.setLoginToken(cacheManager.getCurrentToken());
        apiManager.getAppUserSettings(param);
    }

    private void setCheck(CompoundButton ckBtn, boolean isCheck){
        ckBtn.setChecked(isCheck);
        ckBtn.setBackgroundResource(isCheck? R.drawable.alarm_on: R.drawable.alarm_off);
    }

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            setCheck(buttonView,isChecked);
        }
    };

    public void editClick(View v){

        if(isEdit){
            button_save.setBackgroundResource(R.drawable.edit);
            setCkEnable(false);
            saveDeviceAlarmSetting();
        }else {
            button_save.setBackgroundResource(R.drawable.save);
            setCkEnable(true);
        }

        isEdit = !isEdit;
    }

    public void setCkEnable(boolean enable){
        checkBox_alarmAlert.setEnabled(enable);
        checkBox_alertSound.setEnabled(enable);
        checkBox_alertVibration.setEnabled(enable);
        checkBox_enableSms.setEnabled(enable);
    }

    private void saveDeviceAlarmSetting(){
        AppUserAlarmSettingParam param = new AppUserAlarmSettingParam();
        param.setLoginToken(cacheManager.getCurrentToken());
        param.setEnableAlarm(checkBox_alarmAlert.isChecked());
        param.setEnableRingtone(checkBox_alertSound.isChecked());
        param.setEnableVibrate(checkBox_alertVibration.isChecked());
        param.setEnableSms(checkBox_enableSms.isChecked());
        apiManager.appUserAlarmSetting(param);
    }

    public void onEventMainThread(GetAppUserSettingsResult result){
        if(result.getRetCode().equals(StatusCode.SUCCESS.getCode())){
            AppAlarmSet alarmSet = result.getRetData();
            setCheck(checkBox_alarmAlert,alarmSet.isEnableAlarm());
            setCheck(checkBox_alertSound,alarmSet.isEnableRingtone());
            setCheck(checkBox_alertVibration,alarmSet.isEnableVibrate());
            setCheck(checkBox_enableSms,alarmSet.isEnableSms());
        }else {
            toast(result.getRetMsg());
        }
    }

    public void onEventMainThread(AppUserAlarmSettingResult result){
        if(result.getRetCode().equals(StatusCode.SUCCESS.getCode())){
            toast(result.getRetMsg());
            if(checkBox_enableSms.isChecked()){
                toast("开启短信提醒,请确保用户短信条数充足,如果不足请充值短信套餐");
            }
        }else {
            toast(result.getRetMsg());
        }
    }
}
