package com.lemon.carmonitor.old.ui;

import android.view.View;
import android.widget.Button;

import com.lemon.LemonActivity;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.contant.StatusCode;
import com.lemon.carmonitor.model.param.AppUserFindPwdReqParam;
import com.lemon.carmonitor.model.param.AppUserResetPwdParam;
import com.lemon.carmonitor.model.result.AppUserFindPwdReqResult;
import com.lemon.carmonitor.model.result.AppUserResetPwdResult;
import com.lemon.config.Config;
import com.lemon.util.ParamUtils;
import com.lemon.util.RegUtils;

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
public class ForgetPasswordActivity extends LemonActivity {

    Button btGetCode;

    @Override
    protected void setLayout() {
        layout = R.layout.forgetpassword;
    }

    @Override
    protected void initView() {
        btGetCode = findControl(R.id.btGetCode);
    }

    @Override
    protected void initData() {
        if (Config.isDebug()) {
            setEditTextValue(R.id.etMobile, Config.getValue("mobile"));
        }
    }

    public void finishClick(View v) {
        String mobile = getEditTextValue(R.id.etMobile);
        String code = getEditTextValue(R.id.etSMSCode);
        String pwd = getEditTextValue(R.id.etPassword);
        if (ParamUtils.isEmpty(mobile) || !RegUtils.isMobileNumber(mobile)) {
            toast("手机号不合法");
            return;
        }
        if (ParamUtils.isEmpty(code) || code.length() < Config.getSmsCodeLength()) {
            toast("验证码位数错误");
            return;
        }
        if (ParamUtils.isEmpty(pwd) || pwd.length() < Config.getPwdLength()) {
            toast("密码不合法,至少" + Config.getPwdLength() + "位");
            return;
        }

        AppUserResetPwdParam param = new AppUserResetPwdParam();
        param.setPhoneNum(mobile);
        param.setCaptcha(code);
        param.setNewPwd(pwd);
        apiManager.appUserResetPwd(param);
    }

    public void getCodeClick(View v) {
        String reg_mobile = getEditTextValue(R.id.etMobile);
        if (ParamUtils.isEmpty(reg_mobile) || !RegUtils.isMobileNumber(reg_mobile)) {
            toast("手机号码输入有误,请重新输入");
            return;
        }
        btGetCode.setEnabled(false);
        AppUserFindPwdReqParam param = new AppUserFindPwdReqParam();
        param.setPhoneNum(reg_mobile);
        apiManager.appUserFindPwdReq(param);
        handler.sendEmptyMessageDelayed(0, 60 * 1000);
        toast("60秒后可重新获取");
    }

    @Override
    public void notificationMessage(android.os.Message msg) {
        btGetCode.setEnabled(true);
    }

    public void onEventMainThread(AppUserFindPwdReqResult event) {
        String message = "短信发送成功,请注意查收";
        if (!event.getRetCode().equals(StatusCode.SUCCESS.getCode())) {
            message = "短信发送失败,请稍后重试";
        }
        toast(message);
    }

    public void onEventMainThread(AppUserResetPwdResult result) {
        if (result.getRetCode().equals(StatusCode.SUCCESS.getCode())) {
            //缓存用户信息
            cacheManager.setCurrentMobile(getEditTextValue(R.id.etMobile));
            cacheManager.setCurrentPassword(getEditTextValue(R.id.etPassword));
            startNextActivity(NewLoginActivity.class, true);
        } else if(result.getRetCode().equals(StatusCode.CAPTCHA_ERROR.getCode())){
            toast(StatusCode.CAPTCHA_ERROR.getMessage());
        }else {
            toast("修改失败,请稍后重试");
        }
    }
}
