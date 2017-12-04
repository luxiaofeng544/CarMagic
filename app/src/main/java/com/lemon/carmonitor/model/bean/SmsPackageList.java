package com.lemon.carmonitor.model.bean;

import java.util.List;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.bean]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/2/4 9:53]
 * 修改人:    [xflu]
 * 修改时间:  [2016/2/4 9:53]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class SmsPackageList {

    private List<SmsPackageModel> smsPackages;

    public List<SmsPackageModel> getSmsPackages() {
        return smsPackages;
    }

    public void setSmsPackages(List<SmsPackageModel> smsPackages) {
        this.smsPackages = smsPackages;
    }
}
