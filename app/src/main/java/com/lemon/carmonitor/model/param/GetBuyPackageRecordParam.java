package com.lemon.carmonitor.model.param;

import com.lemon.annotation.Module;
import com.lemon.model.BaseParam;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.param]
 * 类描述:    []
 * 创建人:    [xflu]
 * 创建时间:  [2016/1/6 17:19]
 * 修改人:    [xflu]
 * 修改时间:  [2016/1/6 17:19]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Module(server = "dev_server", name = "apiApp", httpMethod = "get")
public class GetBuyPackageRecordParam extends BaseParam {
    private String loginToken;
    private String limit;
    private String lastId;
    private String status = "1";

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getLastId() {
        return lastId;
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "GetBuyPackageRecordParam{" +
                "loginToken='" + loginToken + '\'' +
                ", limit='" + limit + '\'' +
                ", lastId='" + lastId + '\'' +
                '}';
    }
}
