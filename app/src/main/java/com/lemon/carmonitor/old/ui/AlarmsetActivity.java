package com.lemon.carmonitor.old.ui;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.lemon.LemonActivity;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.contant.StatusCode;
import com.lemon.carmonitor.model.bean.AlarmSet;
import com.lemon.carmonitor.model.bean.AppAlarmSet;
import com.lemon.carmonitor.model.bean.DeviceInfo;
import com.lemon.carmonitor.model.bean.protocol.AlarmTypeEntity;
import com.lemon.carmonitor.model.bean.protocol.ModelsEntity;
import com.lemon.carmonitor.model.param.AppUserAlarmSettingParam;
import com.lemon.carmonitor.model.param.GetAppUserSettingsParam;
import com.lemon.carmonitor.model.param.GetDevAlarmParam;
import com.lemon.carmonitor.model.param.SetDevAlarmParam;
import com.lemon.carmonitor.model.result.AppUserAlarmSettingResult;
import com.lemon.carmonitor.model.result.GetAppUserSettingsResult;
import com.lemon.carmonitor.model.result.GetDevAlarmResult;
import com.lemon.carmonitor.model.result.SetDevAlarmResult;
import com.lemon.util.ParamUtils;

import java.util.List;

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
public class AlarmsetActivity extends LemonActivity {

    private CheckBox checkBox_alarmSOS, checkBox_alarmVibration, checkBox_alarmOffline, checkBox_alarmLowPower;
    private CheckBox checkBox_alarmDisPower, checkBox_alarmZoneIn, checkBox_alarmZoneOut, checkBox_alarmExpire, checkBox_alarmAlert, checkBox_alertSound, checkBox_alertVibration;
    private CheckBox checkBox_move,checkBox_dropped,checkBox_antiDismantle,checkBox_sound,checkBox_close,checkBox_stay,checkBox_overSpeed,checkBox_enableSms,checkBox_enableShowStation;
    private Button button_save;
    private boolean isEdit = false;
    private List<AlarmTypeEntity> alarmTypeEntityList;

    private LinearLayout ll_alarmSOS, ll_alarmVibration, ll_alarmOffline, ll_alarmLowPower;
    private LinearLayout ll_alarmDisPower, ll_alarmZoneIn, ll_alarmZoneOut, ll_alarmExpire, ll_alarmAlert, ll_alertSound, ll_alertVibration;
    private LinearLayout ll_move,ll_dropped,ll_antiDismantle,ll_sound,ll_close,ll_stay,ll_overSpeed,ll_enableSms;

    @Override
    protected void setLayout() {
        layout = R.layout.alarmset;
    }

    @Override
    protected void initView() {
        if(!checkDevice()){
            finish();
            return;
        }
        button_save = findControl(R.id.button_save);
        checkBox_alarmSOS = findControl(R.id.checkBox_alarmSOS);
        checkBox_alarmVibration = findControl(R.id.checkBox_alarmVibration);
        checkBox_alarmOffline = findControl(R.id.checkBox_alarmOffline);
        checkBox_alarmLowPower = findControl(R.id.checkBox_alarmLowPower);
        checkBox_alarmDisPower = findControl(R.id.checkBox_alarmDisPower);
        checkBox_alarmZoneIn = findControl(R.id.checkBox_alarmZoneIn);
        checkBox_alarmZoneOut = findControl(R.id.checkBox_alarmZoneOut);
        checkBox_alarmExpire = findControl(R.id.checkBox_alarmExpire);
        checkBox_alarmAlert = findControl(R.id.checkBox_alarmAlert);
        checkBox_alertSound = findControl(R.id.checkBox_alertSound);
        checkBox_alertVibration = findControl(R.id.checkBox_alertVibration);
        checkBox_move = findControl(R.id.checkBox_move);
        checkBox_overSpeed = findControl(R.id.checkBox_overSpeed);
        checkBox_dropped = findControl(R.id.checkBox_dropped);
        checkBox_antiDismantle = findControl(R.id.checkBox_antiDismantle);
        checkBox_sound = findControl(R.id.checkBox_sound);
        checkBox_close = findControl(R.id.checkBox_close);
        checkBox_stay = findControl(R.id.checkBox_stay);
        checkBox_enableSms = findControl(R.id.checkBox_enableSms);
        checkBox_enableShowStation = findControl(R.id.checkBox_enableShowStation);

        ll_alarmSOS = findControl(R.id.ll_alarmSOS);
        ll_alarmVibration = findControl(R.id.ll_alarmVibration);
        ll_alarmOffline = findControl(R.id.ll_alarmOffline);
        ll_alarmLowPower = findControl(R.id.ll_alarmLowPower);
        ll_alarmDisPower = findControl(R.id.ll_alarmDisPower);
        ll_alarmZoneIn = findControl(R.id.ll_alarmZoneIn);
        ll_alarmZoneOut = findControl(R.id.ll_alarmZoneOut);
        ll_alarmExpire = findControl(R.id.ll_alarmExpire);
        ll_alarmAlert = findControl(R.id.ll_alarmAlert);
        ll_alertSound = findControl(R.id.ll_alertSound);
        ll_alertVibration = findControl(R.id.ll_alertVibration);
        ll_move = findControl(R.id.ll_move);
        ll_overSpeed = findControl(R.id.ll_overSpeed);
        ll_dropped = findControl(R.id.ll_dropped);
        ll_antiDismantle = findControl(R.id.ll_antiDismantle);
        ll_sound = findControl(R.id.ll_sound);
        ll_close = findControl(R.id.ll_close);
        ll_stay = findControl(R.id.ll_stay);
        ll_enableSms = findControl(R.id.ll_enableSms);

        checkBox_alarmSOS.setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox_alarmVibration.setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox_alarmOffline.setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox_alarmLowPower.setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox_alarmDisPower.setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox_alarmZoneIn.setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox_alarmZoneOut.setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox_alarmExpire.setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox_alarmAlert.setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox_alertSound.setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox_alertVibration.setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox_move.setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox_overSpeed.setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox_dropped.setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox_antiDismantle.setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox_sound.setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox_close.setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox_stay.setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox_enableSms.setOnCheckedChangeListener(onCheckedChangeListener);
        setCheck(checkBox_enableShowStation, cacheManager.getShowStationNode().equals("true"));
        checkBox_enableShowStation.setEnabled(true);
        checkBox_enableShowStation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cacheManager.setShowStationNode(isChecked?"true":"false");
                setCheck(checkBox_enableShowStation, isChecked);
            }
        });
    }

    @Override
    protected void initData() {
        if(ParamUtils.isEmpty(cacheManager.getCurrentDevSn())){
            return;
        }
        setCkEnable(false);
        setVisiableGone();
        initAlarmType();
        initDeviceAlarmSetting();
        initAppAlarmSetting();
    }

    private void initDeviceAlarmSetting(){
        GetDevAlarmParam param = new GetDevAlarmParam();
        param.setLoginToken(cacheManager.getCurrentToken());
        param.setDevSn(cacheManager.getCurrentDevSn());
        apiManager.getDevAlarm(param);
    }

    private void initAlarmType(){
        String proType = ((DeviceInfo)cacheManager.getBean(cacheManager.getCurrentDevSn())).getDevProtocol();
        proType = ParamUtils.isEmpty(proType) ? "Lianhua" : proType;
        ModelsEntity modelsEntity = cacheManager.getBean(proType, ModelsEntity.class);
        if(ParamUtils.isNull(modelsEntity)){
            return;
        }
        alarmTypeEntityList = (List<AlarmTypeEntity>) modelsEntity.getAlarm_type();

        for (AlarmTypeEntity item:alarmTypeEntityList){
            if (item.getType().equals("sos")) {
                ll_alarmSOS.setVisibility(View.VISIBLE);
            } else if (item.getType().equals("vibrate")) {
                ll_alarmVibration.setVisibility(View.VISIBLE);
            } else if (item.getType().equals("offline")) {
                ll_alarmOffline.setVisibility(View.VISIBLE);
            } else if (item.getType().equals("lowPower")) {
                ll_alarmLowPower.setVisibility(View.VISIBLE);
            } else if (item.getType().equals("powerOff")) {
                ll_alarmDisPower.setVisibility(View.VISIBLE);
            } else if (item.getType().equals("move")) {
                ll_move.setVisibility(View.VISIBLE);
            } else if (item.getType().equals("overSpeed")) {
                ll_overSpeed.setVisibility(View.VISIBLE);
            } else if (item.getType().equals("dropped")) {
                ll_dropped.setVisibility(View.VISIBLE);
            } else if (item.getType().equals("close")) {
                ll_close.setVisibility(View.VISIBLE);
            } else if (item.getType().equals("sound")) {
                ll_sound.setVisibility(View.VISIBLE);
            } else if (item.getType().equals("antiDismantle")) {
                ll_antiDismantle.setVisibility(View.VISIBLE);
            } else if (item.getType().equals("stay")) {
                ll_stay.setVisibility(View.VISIBLE);
            } else if (item.getType().equals("expire")) {
                ll_alarmExpire.setVisibility(View.VISIBLE);
            }
        }

    }

    private void setVisiableGone(){
        ll_alarmSOS.setVisibility(View.GONE);
        ll_alarmVibration.setVisibility(View.GONE);
        ll_alarmOffline.setVisibility(View.GONE);
        ll_alarmLowPower.setVisibility(View.GONE);
        ll_alarmDisPower.setVisibility(View.GONE);
        ll_alarmExpire.setVisibility(View.GONE);
        ll_move.setVisibility(View.GONE);
        ll_overSpeed.setVisibility(View.GONE);
        ll_dropped.setVisibility(View.GONE);
        ll_antiDismantle.setVisibility(View.GONE);
        ll_sound.setVisibility(View.GONE);
        ll_close.setVisibility(View.GONE);
        ll_stay.setVisibility(View.GONE);
        ll_alarmZoneIn.setVisibility(View.VISIBLE);
        ll_alarmZoneOut.setVisibility(View.VISIBLE);

        /*ll_alarmAlert.setVisibility(View.GONE);
        ll_alertSound.setVisibility(View.GONE);
        ll_alertVibration.setVisibility(View.GONE);
        ll_enableSms.setVisibility(View.GONE);*/
    }


    private void setCheck(CompoundButton ckBtn, boolean isCheck){
        ckBtn.setChecked(isCheck);
        ckBtn.setBackgroundResource(isCheck ? R.drawable.alarm_on : R.drawable.alarm_off);
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
            saveAppAlarmSetting();
        }else {
            button_save.setBackgroundResource(R.drawable.save);
            setCkEnable(true);
        }

        isEdit = !isEdit;
    }

    public void setCkEnable(boolean enable){
        checkBox_alarmSOS.setEnabled(enable);
        checkBox_alarmVibration.setEnabled(enable);
        checkBox_alarmOffline.setEnabled(enable);
        checkBox_alarmLowPower.setEnabled(enable);
        checkBox_alarmDisPower.setEnabled(enable);
        checkBox_alarmZoneIn.setEnabled(enable);
        checkBox_alarmZoneOut.setEnabled(enable);
        checkBox_alarmExpire.setEnabled(enable);
        checkBox_alarmAlert.setEnabled(enable);
        checkBox_alertSound.setEnabled(enable);
        checkBox_alertVibration.setEnabled(enable);
        checkBox_move.setEnabled(enable);
        checkBox_overSpeed.setEnabled(enable);
        checkBox_dropped.setEnabled(enable);
        checkBox_sound.setEnabled(enable);
        checkBox_antiDismantle.setEnabled(enable);
        checkBox_close.setEnabled(enable);
        checkBox_stay.setEnabled(enable);
        checkBox_enableSms.setEnabled(enable);
    }

    private void saveDeviceAlarmSetting(){
        SetDevAlarmParam param = new SetDevAlarmParam();
        param.setDevSn(cacheManager.getCurrentDevSn());
        param.setLoginToken(cacheManager.getCurrentToken());
        param.setSos(checkBox_alarmSOS.isChecked());
        param.setVibrate(checkBox_alarmVibration.isChecked());
        param.setOffline(checkBox_alarmOffline.isChecked());
        param.setLowPower(checkBox_alarmLowPower.isChecked());
        param.setPowerOff(checkBox_alarmDisPower.isChecked());
        param.setMove(checkBox_move.isChecked());
        param.setOverSpeed(checkBox_overSpeed.isChecked());
        param.setDropped(checkBox_dropped.isChecked());
        param.setClose(checkBox_close.isChecked());
        param.setSound(checkBox_sound.isChecked());
        param.setAntiDismantle(checkBox_antiDismantle.isChecked());
        param.setStay(checkBox_stay.isChecked());
        param.setEnableAlarm(checkBox_alarmAlert.isChecked());
        param.setEnableRingtone(checkBox_alertSound.isChecked());
        param.setEnableVibrate(checkBox_alertVibration.isChecked());
        param.setExpire(checkBox_alarmExpire.isChecked());
        param.setEnableSms(checkBox_enableSms.isChecked());
        param.setInFence(checkBox_alarmZoneIn.isChecked());
        param.setOutFence(checkBox_alarmZoneOut.isChecked());
        apiManager.setDevAlarm(param);
    }

    public void onEventMainThread(GetDevAlarmResult result){
        if(result.getRetCode().equals(StatusCode.SUCCESS.getCode())){
            AlarmSet alarmSet = result.getRetData();
            setCheck(checkBox_alarmSOS,alarmSet.isSos());
            setCheck(checkBox_alarmVibration,alarmSet.isVibrate());
            setCheck(checkBox_alarmOffline,alarmSet.isOffline());
            setCheck(checkBox_alarmLowPower,alarmSet.isLowPower());
            setCheck(checkBox_alarmDisPower,alarmSet.isPowerOff());
            setCheck(checkBox_move,alarmSet.isMove());
            setCheck(checkBox_overSpeed,alarmSet.isOverSpeed());
            setCheck(checkBox_dropped,alarmSet.isDropped());
            setCheck(checkBox_sound,alarmSet.isSound());
            setCheck(checkBox_close,alarmSet.isClose());
            setCheck(checkBox_antiDismantle,alarmSet.isAntiDismantle());
            setCheck(checkBox_stay,alarmSet.isStay());
            setCheck(checkBox_alarmExpire,alarmSet.isExpire());
            setCheck(checkBox_alarmZoneIn,alarmSet.isInFence());
            setCheck(checkBox_alarmZoneOut,alarmSet.isOutFence());
        }else {
            toast(result.getRetMsg());
        }
    }

    public void onEventMainThread(SetDevAlarmResult result){
        if(result.getRetCode().equals(StatusCode.SUCCESS.getCode())){
            toast(result.getRetMsg());
        }else {
            toast(result.getRetMsg());
        }
    }

    protected void initAppAlarmSetting() {
        setCkEnable(false);
        GetAppUserSettingsParam param = new GetAppUserSettingsParam();
        param.setLoginToken(cacheManager.getCurrentToken());
        apiManager.getAppUserSettings(param);
    }

    private void saveAppAlarmSetting(){
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
