package com.lemon.carmonitor.old.ui;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lemon.LemonActivity;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.contant.StatusCode;
import com.lemon.carmonitor.model.bean.UserInfo;
import com.lemon.carmonitor.model.param.AppUserModifyParam;
import com.lemon.carmonitor.model.param.GetAppUserInfoParam;
import com.lemon.carmonitor.model.result.AppUserModifyResult;
import com.lemon.carmonitor.model.result.GetAppUserInfoResult;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.ui]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2015/12/21 21:15]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2015/12/21 21:15]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class UserInfoActivity extends LemonActivity {

    private Button button_save;
    private EditText et_customername,et_username,et_nickname,et_mobile,et_email,et_address;
    private boolean isEdit = false;

    @Override
    protected void setLayout() {
        setLayoutValue(R.layout.userinfo);
    }

    @Override
    protected void initView() {
        et_customername = findControl(R.id.et_customername);
        et_username = findControl(R.id.et_username);
        et_nickname = findControl(R.id.et_nickname);
        et_mobile = findControl(R.id.et_mobile);
        et_email = findControl(R.id.et_email);
        et_address = findControl(R.id.et_address);
        button_save = findControl(R.id.button_save);
        setEtEnable(false);
    }

    @Override
    protected void initData() {
        GetAppUserInfoParam param = new GetAppUserInfoParam();
        param.setLoginToken(cacheManager.getCurrentToken());
        apiManager.getAppUserInfo(param);
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
        AppUserModifyParam param = new AppUserModifyParam();
        param.setLoginToken(cacheManager.getCurrentToken());
        param.setAddress(getEditTextValue(R.id.et_address));
        param.setUserName(getEditTextValue(R.id.et_nickname));
        param.setEmail(getEditTextValue(R.id.et_email));
        apiManager.appUserModify(param);
    }

    public void onEventMainThread(GetAppUserInfoResult result){
        if(result.getRetCode().equals(StatusCode.SUCCESS.getCode())){
            UserInfo data = result.getRetData();
            setUserInfo(data);
        }else {
            toast("加载用户信息失败");
        }
    }

    public void onEventMainThread(AppUserModifyResult result){
        if(result.getRetCode().equals(StatusCode.SUCCESS.getCode())){
            toast("保存成功");
        }else {
            toast("保存失败");
        }
    }

    private void setEtEnable(boolean enable){
        et_customername.setEnabled(enable);
        et_username.setEnabled(enable);
        et_nickname.setEnabled(enable);
        et_mobile.setEnabled(false);
        et_email.setEnabled(enable);
        et_address.setEnabled(enable);
    }

    private void setUserInfo(UserInfo userInfo){
        et_customername.setText(userInfo.getUserLoginName());
        et_username.setText(userInfo.getPhoneNum());
        et_nickname.setText(userInfo.getUserName());
        et_mobile.setText(userInfo.getPhoneNum());
        et_email.setText(userInfo.getEmail());
        et_address.setText(userInfo.getAddress());
    }

}
