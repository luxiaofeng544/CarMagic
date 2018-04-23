package com.lemon.carmonitor.old.ui;

import android.view.View;
import android.widget.Button;

import com.lemon.LemonActivity;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.contant.StatusCode;
import com.lemon.carmonitor.model.param.AppUserRegisterParam;
import com.lemon.carmonitor.model.param.AppUserRegisterReqParam;
import com.lemon.carmonitor.model.result.AppUserRegisterReqResult;
import com.lemon.carmonitor.model.result.AppUserRegisterResult;
import com.lemon.carmonitor.ui.MainActivity;
import com.lemon.config.Config;
import com.lemon.util.ParamUtils;

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
public class RegisterActivity extends LemonActivity {

    Button btGetCode;

    @Override
    protected void setLayout() {
        layout = R.layout.activity_free_register;
    }

    @Override
    protected void initData() {
        if (Config.isDebug()) {
            setEditTextValue(R.id.reg_mobile, Config.getValue("mobile"));
        }
    }
    @Override
    protected void initView() {
        btGetCode = findControl(R.id.btGetCode);
    }

    public void registerClick(View v){
        String mobile = getEditTextValue(R.id.reg_mobile);
        String code = getEditTextValue(R.id.etSMSCode);
        String pwd = getEditTextValue(R.id.reg_password);
        if(ParamUtils.isEmpty(mobile)){
            toast("手机号不合法");
            return;
        }
        if(ParamUtils.isEmpty(code) || code.length()< Config.getSmsCodeLength()){
            toast("验证码位数错误");
            return;
        }
        if(ParamUtils.isEmpty(pwd) || pwd.length()< Config.getPwdLength()){
            toast("密码不合法,至少"+ Config.getPwdLength()+"位");
            return;
        }

        AppUserRegisterParam param = new AppUserRegisterParam();
        param.setPhoneNum(mobile);
        param.setCaptcha(code);
        param.setPasswd(pwd);
        apiManager.appUserRegister(param);
    }

    public void getCodeClick(View v){
        String reg_mobile = getEditTextValue(R.id.reg_mobile);
        if(ParamUtils.isEmpty(reg_mobile)){
            toast("请输入手机号码");
            return;
        }
        toast("60秒后可重新获取");
        AppUserRegisterReqParam param = new AppUserRegisterReqParam();
        param.setPhoneNum(reg_mobile);
        apiManager.appUserRegisterReq(param);
        handler.sendEmptyMessageDelayed(0, 60 * 1000);
        btGetCode.setEnabled(false);
    }

    @Override
    public void notificationMessage(android.os.Message msg) {
        btGetCode.setEnabled(true);
    }

    public void onEventMainThread(AppUserRegisterReqResult event){
        String message = "短信发送成功,请注意查收";
        if(!event.getRetCode().equals(StatusCode.SUCCESS.getCode())){
            message = event.getRetMsg();
        }
        toast(message);
    }
    
    public void onEventMainThread(AppUserRegisterResult result){
        if(result.getRetCode().equals(StatusCode.SUCCESS.getCode())){
            //缓存用户信息
            cacheManager.setCurrentToken(result.getRetData().getToken());
            cacheManager.setCurrentMobile(result.getRetData().getPhoneNum());
            cacheManager.setCurrentPassword(getEditTextValue(R.id.reg_password));
            startNextActivity(MainActivity.class,true);
        } else if(result.getRetCode().equals(StatusCode.CAPTCHA_ERROR.getCode())){
            toast(StatusCode.CAPTCHA_ERROR.getMessage());
        }else {
            toast(result.getRetMsg());
        }
    }
}
