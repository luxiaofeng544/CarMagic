package com.lemon.carmonitor.model.bean.protocol;

import com.lemon.util.ParamUtils;

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
public class ModelsEntity {
    private String name;
    private String type;
    private SmsEntity sms;
    private GprsEntity gprs;
    /**
     * name : 位移报警
     * type : move
     * status : 10111111111111111111111111111111
     */

    private List<AlarmTypeEntity> alarm_type;

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSms(SmsEntity sms) {
        this.sms = sms;
    }

    public void setGprs(GprsEntity gprs) {
        this.gprs = gprs;
    }

    public void setAlarm_type(List<AlarmTypeEntity> alarm_type) {
        this.alarm_type = alarm_type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public SmsEntity getSms() {
        return sms;
    }

    public GprsEntity getGprs() {
        return gprs;
    }

    public List<AlarmTypeEntity> getAlarm_type() {
        return alarm_type;
    }

    public String getAlarmTypeName(String status){
        String result = "";
        if(!ParamUtils.isEmpty(alarm_type)){
            for (AlarmTypeEntity item:alarm_type){
                if(item.getStatus().equals(status)){
                    return item.getName();
                }
            }
        }
        return result;
    }
}
