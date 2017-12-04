package com.lemon.carmonitor.model.bean.protocol;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.bean]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/1/31 19:18]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/1/31 19:18]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProtocolCmds {

    /**
     * name : 联华
     * protocol : Lianhua
     * models : [{"name":"联华","type":"Lianhua","alarm_type":[{"name":"位移报警","type":"move","status":"10111111111111111111111111111111"},{"name":"震动报警","type":"vibrate","status":"11111111101111111111111111111111"},{"name":"劫警","type":"sos","status":"11111111111111111111111110111111"},{"name":"超速报警","type":"overSpeed","status":"11111111111111111111111111011111"}],"sms":{"categories":[{"name":"通用设置","type":"normal","cmds":[{"name":"语言设置","key":"setLang","template":"setLang*${param1}","input":"true","fields":[{"key":"param1","type":"spinner","hint":"语言设置","values":["中文","英文"],"keys":["-1","1"]}]}]}]},"gprs":{"categories":[{"name":"通用设置","type":"normal","cmds":[{"name":"语言设置","key":"setLang","template":"setLang*${param1}","input":"true","fields":[{"key":"param1","type":"spinner","hint":"语言设置","values":["中文","英文"],"keys":["-1","1"]}]}]}]}}]
     */

    private List<SuppliersEntity> suppliers;

    public void setSuppliers(List<SuppliersEntity> suppliers) {
        this.suppliers = suppliers;
    }

    public List<SuppliersEntity> getSuppliers() {
        return suppliers;
    }

    private String check;

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }
}
