package com.lemon.carmonitor.model.param;

import com.lemon.annotation.Module;
import com.lemon.model.BaseParam;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.param]
 * 类描述:    [APP用户使用手机号码注册，点击获取注册验证码]
 * 创建人:    [xflu]
 * 创建时间:  [2016/1/6 17:19]
 * 修改人:    [xflu]
 * 修改时间:  [2016/1/6 17:19]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Module(server = "dev_server", name = "apiApp")
public class AppUserRegisterReqParam extends BaseParam {

    private String phoneNum;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return "AppUserRegisterReqParam{" +
                "phoneNum='" + phoneNum + '\'' +
                '}';
    }
}
