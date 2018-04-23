package com.lemon.carmonitor.old.ui;

import android.view.View;

import com.lemon.LemonActivity;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.contant.StatusCode;
import com.lemon.carmonitor.model.bean.UserInfo;
import com.lemon.carmonitor.model.bean.UserLoginBean;
import com.lemon.carmonitor.model.param.AppUserLoginParam;
import com.lemon.carmonitor.model.param.GetUserDevsParam;
import com.lemon.carmonitor.model.result.AppUserLoginResult;
import com.lemon.carmonitor.ui.MainActivity;
import com.lemon.config.Config;
import com.lemon.util.ParamUtils;


/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.ui]
 * 类描述:    [登陆界面]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2015/12/21 20:34]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2015/12/21 20:34]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class NewLoginActivity extends LemonActivity {

    @Override
    protected void setLayout() {
        layout = R.layout.activity_my_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        if(Config.isDebug()){
            setEditTextValue(R.id.etMobile, Config.getValue("mobile"));
            setEditTextValue(R.id.etPass, Config.getValue("pwd"));
        }

        if(!ParamUtils.isEmpty(cacheManager.getCurrentMobile()) && !ParamUtils.isEmpty(cacheManager.getCurrentPassword())){
            setEditTextValue(R.id.etMobile,cacheManager.getCurrentMobile());
            setEditTextValue(R.id.etPass,cacheManager.getCurrentPassword());
        }

        if(Config.isAutoLogin()&&(ParamUtils.isNull(getIntentExtraStr("type"))||!getIntentExtraStr("type").equals("logout"))){
            loginClick(null);
        }
    }

    public void registerClick(View v) {
        startNextActivity(RegisterActivity.class, false);
    }

    public void loginClick(View v) {
        if(isEditTextEmpty(R.id.etMobile)){
            toast("用户名不允许为空");
            return;
        }
        if(isEditTextEmpty(R.id.etPass)){
            toast("密码不允许为空");
            return;
        }
        String mobile = getEditTextValue(R.id.etMobile);
        String pass = getEditTextValue(R.id.etPass);

        AppUserLoginParam param = new AppUserLoginParam();
        param.setPhoneNum(mobile);
        param.setPasswd(pass);
        apiManager.appUserLogin(param);
    }

    public void onEventMainThread(AppUserLoginResult result){
        if(result.getRetCode().equals(StatusCode.SUCCESS.getCode())){
            //缓存用户信息
            cacheManager.setCurrentToken(result.getRetData().getToken());
            cacheManager.setCurrentServiceId(result.getRetData().getServiceId());
            cacheManager.setCurrentMobile(result.getRetData().getPhoneNum());
            cacheManager.setCurrentPassword(getEditTextValue(R.id.etPass));
            cacheManager.putBean(UserInfo.class, result.getRetData());
            cacheManager.putBean(UserLoginBean.class,result.getRetData());
            GetUserDevsParam param = new GetUserDevsParam();
            param.setShowDialog(false);
            param.setLoginToken(result.getRetData().getToken());
            apiManager.getUserDevs(param);
            startNextActivity(MainActivity.class, true);
        }else if(result.getRetCode().equals(StatusCode.NAME_PWD_ERROR.getCode())){
            toast(StatusCode.NAME_PWD_ERROR.getMessage());
        }else {
            toast(result.getRetMsg());
        }
    }

    public void forgetPasswordClick(View v) {
        startNextActivity(ForgetPasswordActivity.class, false);
    }
}
