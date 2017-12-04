package com.lemon.carmonitor.model.bean.protocol;

import java.util.List;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.bean.protocol]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/2/1 22:37]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/2/1 22:37]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class SuppliersEntity {
    private String name;
    private String protocol;
    private String iotcard_type;
    private String comcard_type;
    /**
     * name : 联华
     * type : Lianhua
     * alarm_type : [{"name":"位移报警","type":"move","status":"10111111111111111111111111111111"},{"name":"震动报警","type":"vibrate","status":"11111111101111111111111111111111"},{"name":"劫警","type":"sos","status":"11111111111111111111111110111111"},{"name":"超速报警","type":"overSpeed","status":"11111111111111111111111111011111"}]
     * sms : {"categories":[{"name":"通用设置","type":"normal","cmds":[{"name":"语言设置","key":"setLang","template":"setLang*${param1}","input":"true","fields":[{"key":"param1","type":"spinner","hint":"语言设置","values":["中文","英文"],"keys":["-1","1"]}]}]}]}
     * gprs : {"categories":[{"name":"通用设置","type":"normal","cmds":[{"name":"语言设置","key":"setLang","template":"setLang*${param1}","input":"true","fields":[{"key":"param1","type":"spinner","hint":"语言设置","values":["中文","英文"],"keys":["-1","1"]}]}]}]}
     */

    private List<ModelsEntity> models;

    public void setName(String name) {
        this.name = name;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setModels(List<ModelsEntity> models) {
        this.models = models;
    }

    public String getName() {
        return name;
    }

    public String getProtocol() {
        return protocol;
    }

    public List<ModelsEntity> getModels() {
        return models;
    }

    public String getIotcard_type() {
        return iotcard_type;
    }

    public void setIotcard_type(String iotcard_type) {
        this.iotcard_type = iotcard_type;
    }

    public String getComcard_type() {
        return comcard_type;
    }

    public void setComcard_type(String comcard_type) {
        this.comcard_type = comcard_type;
    }
}
