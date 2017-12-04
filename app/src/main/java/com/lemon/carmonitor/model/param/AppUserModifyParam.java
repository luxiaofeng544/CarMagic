package com.lemon.carmonitor.model.param;

import com.lemon.annotation.Module;
import com.lemon.model.BaseParam;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.param]
 * 类描述:    [APP用户登陆后,修改用户个人信息]
 * 创建人:    [xflu]
 * 创建时间:  [2016/1/6 17:19]
 * 修改人:    [xflu]
 * 修改时间:  [2016/1/6 17:19]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Module(server = "dev_server", name = "apiApp")
public class AppUserModifyParam extends BaseParam {

    private String loginToken;
    private String userName;
    private String email;
    private String address;

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "AppUserModifyParam{" +
                "loginToken='" + loginToken + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
