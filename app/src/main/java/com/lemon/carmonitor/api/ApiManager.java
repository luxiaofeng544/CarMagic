package com.lemon.carmonitor.api;

import android.content.Context;

import com.lemon.annotation.Autowired;
import com.lemon.annotation.Component;
import com.lemon.annotation.InvokeMethod;
import com.lemon.annotation.MethodName;
import com.lemon.annotation.ParamType;
import com.lemon.annotation.ReturnType;
import com.lemon.carmonitor.model.param.AppProtocolParam;
import com.lemon.carmonitor.model.param.AppUpdateNewParam;
import com.lemon.carmonitor.model.param.AppUpdateParam;
import com.lemon.carmonitor.model.param.AppUserAddDevParam;
import com.lemon.carmonitor.model.param.AppUserAlarmSettingParam;
import com.lemon.carmonitor.model.param.AppUserChangePwdParam;
import com.lemon.carmonitor.model.param.AppUserDelDevParam;
import com.lemon.carmonitor.model.param.AppUserFindPwdReqParam;
import com.lemon.carmonitor.model.param.AppUserLocReportParam;
import com.lemon.carmonitor.model.param.AppUserLoginParam;
import com.lemon.carmonitor.model.param.AppUserModifyParam;
import com.lemon.carmonitor.model.param.AppUserRegisterParam;
import com.lemon.carmonitor.model.param.AppUserRegisterReqParam;
import com.lemon.carmonitor.model.param.AppUserResetPwdParam;
import com.lemon.carmonitor.model.param.BuySmsPackageParam;
import com.lemon.carmonitor.model.param.DelDevFenceParam;
import com.lemon.carmonitor.model.param.ErrorReportParam;
import com.lemon.carmonitor.model.param.GetAppUserAccountParam;
import com.lemon.carmonitor.model.param.GetAppUserInfoParam;
import com.lemon.carmonitor.model.param.GetAppUserSettingsParam;
import com.lemon.carmonitor.model.param.GetBuyPackageRecordParam;
import com.lemon.carmonitor.model.param.GetDevAlarmLogParam;
import com.lemon.carmonitor.model.param.GetDevAlarmParam;
import com.lemon.carmonitor.model.param.GetDevFencesParam;
import com.lemon.carmonitor.model.param.GetDevLastCmdLogParam;
import com.lemon.carmonitor.model.param.GetSmsPackagesParam;
import com.lemon.carmonitor.model.param.GetUserDevParam;
import com.lemon.carmonitor.model.param.GetUserDevsParam;
import com.lemon.carmonitor.model.param.GetUserFencesParam;
import com.lemon.carmonitor.model.param.IssueCmdParam;
import com.lemon.carmonitor.model.param.ModifyDevInfoParam;
import com.lemon.carmonitor.model.param.QueryEntityListParam;
import com.lemon.carmonitor.model.param.QueryHistoryTrackParam;
import com.lemon.carmonitor.model.param.SaveDevFenceParam;
import com.lemon.carmonitor.model.param.SetDevAlarmParam;
import com.lemon.carmonitor.model.result.AppProtocolResult;
import com.lemon.carmonitor.model.result.AppUpdateNewResult;
import com.lemon.carmonitor.model.result.AppUpdateResult;
import com.lemon.carmonitor.model.result.AppUserAddDevResult;
import com.lemon.carmonitor.model.result.AppUserAlarmSettingResult;
import com.lemon.carmonitor.model.result.AppUserChangePwdResult;
import com.lemon.carmonitor.model.result.AppUserDelDevResult;
import com.lemon.carmonitor.model.result.AppUserFindPwdReqResult;
import com.lemon.carmonitor.model.result.AppUserLocReportResult;
import com.lemon.carmonitor.model.result.AppUserLoginResult;
import com.lemon.carmonitor.model.result.AppUserModifyResult;
import com.lemon.carmonitor.model.result.AppUserRegisterReqResult;
import com.lemon.carmonitor.model.result.AppUserRegisterResult;
import com.lemon.carmonitor.model.result.AppUserResetPwdResult;
import com.lemon.carmonitor.model.result.BuySmsPackageResult;
import com.lemon.carmonitor.model.result.DelDevFenceResult;
import com.lemon.carmonitor.model.result.ErrorReportResult;
import com.lemon.carmonitor.model.result.GetAppUserAccountResult;
import com.lemon.carmonitor.model.result.GetAppUserInfoResult;
import com.lemon.carmonitor.model.result.GetAppUserSettingsResult;
import com.lemon.carmonitor.model.result.GetBuyPackageRecordResult;
import com.lemon.carmonitor.model.result.GetDevAlarmLogResult;
import com.lemon.carmonitor.model.result.GetDevAlarmResult;
import com.lemon.carmonitor.model.result.GetDevFencesResult;
import com.lemon.carmonitor.model.result.GetDevLastCmdLogResult;
import com.lemon.carmonitor.model.result.GetSmsPackagesResult;
import com.lemon.carmonitor.model.result.GetUserDevResult;
import com.lemon.carmonitor.model.result.GetUserDevsResult;
import com.lemon.carmonitor.model.result.GetUserFencesResult;
import com.lemon.carmonitor.model.result.IssueCmdResult;
import com.lemon.carmonitor.model.result.ModifyDevInfoResult;
import com.lemon.carmonitor.model.result.QueryEntityListResult;
import com.lemon.carmonitor.model.result.QueryEntityLocationResult;
import com.lemon.carmonitor.model.result.QueryHistoryTrackResult;
import com.lemon.carmonitor.model.result.SaveDevFenceResult;
import com.lemon.carmonitor.model.result.SetDevAlarmResult;
import com.lemon.model.BaseParam;
import com.lemon.net.NetEngine;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.net]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2015/12/16 14:07]
 * 修改人:    [xflu]
 * 修改时间:  [2015/12/16 14:07]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Component
public class ApiManager {

    @Autowired
    public Context mContext;
    @Autowired
    public NetEngine netEngine;

    @ParamType(value = AppUserAddDevParam.class)
    @ReturnType(value = AppUserAddDevResult.class)
    public void appUserAddDev(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = AppUserChangePwdParam.class)
    @ReturnType(value = AppUserChangePwdResult.class)
    public void appUserChangePwd(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = AppUserFindPwdReqParam.class)
    @ReturnType(value = AppUserFindPwdReqResult.class)
    public void appUserFindPwdReq(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = AppUserLoginParam.class)
    @ReturnType(value = AppUserLoginResult.class)
    public void appUserLogin(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = AppUserModifyParam.class)
    @ReturnType(value = AppUserModifyResult.class)
    public void appUserModify(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = AppUserRegisterParam.class)
    @ReturnType(value = AppUserRegisterResult.class)
    public void appUserRegister(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = AppUserRegisterReqParam.class)
    @ReturnType(value = AppUserRegisterReqResult.class)
    public void appUserRegisterReq(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = AppUserResetPwdParam.class)
    @ReturnType(value = AppUserResetPwdResult.class)
    public void appUserResetPwd(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = GetAppUserInfoParam.class)
    @ReturnType(value = GetAppUserInfoResult.class)
    public void getAppUserInfo(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = GetUserDevParam.class)
    @ReturnType(value = GetUserDevResult.class)
    public void getUserDev(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = GetUserDevsParam.class)
    @ReturnType(value = GetUserDevsResult.class)
    public void getUserDevs(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = IssueCmdParam.class)
    @ReturnType(value = IssueCmdResult.class)
    public void issueCmd(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = ModifyDevInfoParam.class)
    @ReturnType(value = ModifyDevInfoResult.class)
    public void modifyDevInfo(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = SetDevAlarmParam.class)
    @ReturnType(value = SetDevAlarmResult.class)
    public void setDevAlarm(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = GetDevAlarmParam.class)
    @ReturnType(value = GetDevAlarmResult.class)
    public void getDevAlarm(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = ErrorReportParam.class)
    @ReturnType(value = ErrorReportResult.class)
    public void errorReport(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = AppUpdateParam.class)
    @ReturnType(value = AppUpdateResult.class)
    public void update(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = AppUpdateNewParam.class)
    @ReturnType(value = AppUpdateNewResult.class)
    @InvokeMethod(methodName="update")
    public void updateNew(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = SaveDevFenceParam.class)
    @ReturnType(value = SaveDevFenceResult.class)
    public void saveDevFence(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = DelDevFenceParam.class)
    @ReturnType(value = DelDevFenceResult.class)
    public void delDevFence(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = GetDevFencesParam.class)
    @ReturnType(value = GetDevFencesResult.class)
    public void getDevFences(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = GetUserFencesParam.class)
    @ReturnType(value = GetUserFencesResult.class)
    public void getUserFences(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = AppProtocolParam.class)
    @ReturnType(value = AppProtocolResult.class)
    public void protocol(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = GetSmsPackagesParam.class)
    @ReturnType(value = GetSmsPackagesResult.class)
    public void getSmsPackages(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = BuySmsPackageParam.class)
    @ReturnType(value = BuySmsPackageResult.class)
    public void buySmsPackage(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = GetBuyPackageRecordParam.class)
    @ReturnType(value = GetBuyPackageRecordResult.class)
    public void getBuyPackageRecord(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = AppUserLocReportParam.class)
    @ReturnType(value = AppUserLocReportResult.class)
    public void appUserLocReport(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = AppUserAlarmSettingParam.class)
    @ReturnType(value = AppUserAlarmSettingResult.class)
    public void appUserAlarmSetting(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = GetAppUserSettingsParam.class)
    @ReturnType(value = GetAppUserSettingsResult.class)
    public void getAppUserSettings(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = AppUserDelDevParam.class)
    @ReturnType(value = AppUserDelDevResult.class)
    public void appUserDelDev(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = GetDevAlarmLogParam.class)
    @ReturnType(value = GetDevAlarmLogResult.class)
    public void getDevAlarmLog(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = GetAppUserAccountParam.class)
    @ReturnType(value = GetAppUserAccountResult.class)
    public void getAppUserAccount(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = GetDevLastCmdLogParam.class)
    @ReturnType(value = GetDevLastCmdLogResult.class)
    public void getDevLastCmdLog(BaseParam param) {
        netEngine.invoke(param);
    }

    @ParamType(value = QueryHistoryTrackParam.class)
    @ReturnType(value = QueryHistoryTrackResult.class)
    public void queryHistoryTrack(BaseParam param){
        netEngine.invoke(param);
    }

    @ParamType(value = QueryEntityListParam.class)
    @ReturnType(value = QueryEntityLocationResult.class)
    @MethodName(name = "queryEntityList")
    public void queryCurrentLocation(BaseParam param){
        netEngine.invoke(param);
    }

    @ParamType(value = QueryEntityListParam.class)
    @ReturnType(value = QueryEntityListResult.class)
    public void queryEntityList(BaseParam param){
        netEngine.invoke(param);
    }


    //think race interface


}
