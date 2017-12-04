package com.lemon.carmonitor.model.param;

import com.lemon.annotation.Module;
import com.lemon.model.BaseParam;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.param]
 * 类描述:    [APP登录用户，修改设备信息]
 * 创建人:    [xflu]
 * 创建时间:  [2016/1/6 17:19]
 * 修改人:    [xflu]
 * 修改时间:  [2016/1/6 17:19]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Module(server = "dev_server", name = "apiApp")
public class ModifyDevInfoParam extends BaseParam {

    private String loginToken;
    private String devSn;
    private String devName;
    private String carNum;
    private String devPhoneNum;
    private String contractName;
    private String contractNum;

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getDevSn() {
        return devSn;
    }

    public void setDevSn(String devSn) {
        this.devSn = devSn;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getDevPhoneNum() {
        return devPhoneNum;
    }

    public void setDevPhoneNum(String devPhoneNum) {
        this.devPhoneNum = devPhoneNum;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    @Override
    public String toString() {
        return "ModifyDevInfoParam{" +
                "loginToken='" + loginToken + '\'' +
                ", devSn='" + devSn + '\'' +
                ", devName='" + devName + '\'' +
                ", carNum='" + carNum + '\'' +
                ", devPhoneNum='" + devPhoneNum + '\'' +
                ", contractName='" + contractName + '\'' +
                ", contractNum='" + contractNum + '\'' +
                '}';
    }
}
