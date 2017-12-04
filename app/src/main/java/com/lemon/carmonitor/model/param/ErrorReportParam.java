package com.lemon.carmonitor.model.param;

import com.lemon.annotation.Module;
import com.lemon.model.BaseParam;
import com.lemon.util.ImsiUtil;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.param]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/1/10 15:19]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/1/10 15:19]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Module(server = "dev_server", name = "apiApp", httpMethod = "post")
public class ErrorReportParam extends BaseParam {

    private String phoneModel;
    private String versionInfo;
    private String errorInfo;

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(String versionInfo) {
        this.versionInfo = versionInfo;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    @Override
    public String toString() {
        return "ErrorReportParam{" +
                "phoneModel='" + phoneModel + '\'' +
                ", versionInfo='" + versionInfo + '\'' +
                ", errorInfo='" + errorInfo + '\'' +
                '}';
    }
}
