package com.lemon.carmonitor.contant;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.contant]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/1/11 15:45]
 * 修改人:    [xflu]
 * 修改时间:  [2016/1/11 15:45]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public interface ICmdConstant {

    /**
     * 主控手机号
     */
    public static final String SET_ADMIN = "setAdmin";
    /**
     * 删除主控号码
     */
    public static final String CLEAR_ADMIN = "clearAdmin";
    /**
     * 设置亲情号码
     */
    public static final String SET_SOSNUM = "setSosNum";
    /**
     * 删除亲情号码
     */
    public static final String CLEAR_SOSNUM = "clearSosNum";
    /**
     * 开启移位报警
     */
    public static final String SET_MOVEALARM = "setMoveAlarm";
    /**
     * 关闭移位报警
     */
    public static final String CLOSE_MOVEALARM = "closeMoveAlarm";
    /**
     * 开启超速报警
     */
    public static final String SET_OVERSPEEDALARM = "setOverSpeedAlarm";
    /**
     * 关闭超速报警
     */
    public static final String CLOSE_OVERSPEEDALARM = "closeOverSpeedAlarm";
    /**
     * 上传频率
     */
    public static final String SET_RUNINTERVAL = "setRunInterval";
    /**
     * 修改设备密码
     */
    public static final String EDIT_PASSWD = "editPasswd";
    /**
     * SOS报警
     */
    public static final String SET_SOSALARMTYPE = "setSosAlarmType";
    /**
     * APN设置
     */
    public static final String apn = "";
    /**
     * 监听及通话
     */
    public static final String MONITOR = "startMonitor";
    /**
     * 重新启动
     */
    public static final String RESTART = "restart";
    /**
     * 恢复出厂设置
     */
    public static final String FACTORY_RESET = "factoryReset";
    /**
     * 语言选择
     */
    public static final String SET_LANG = "setLang";
    /**
     * 时区设置
     */
    public static final String SET_TIMEZONE = "setTimeZone";
    /**
     * 设置监听模式
     */
    public static final String SET_MONITOR = "setMonitor";
    /**
     * 设置定位模式
     */
    public static final String SET_LB = "setLB";
    /**
     * 关闭GPRS
     */
    public static final String CLOSE_GPRS = "closeGPRS";
    /**
     * 打开GPRS
     */
    public static final String OPEN_GPRS = "openGPRS";

    public static final String CONFIRM_MESSAGE = "你确定要发送该指令吗";
}
