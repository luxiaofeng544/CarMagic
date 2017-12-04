package com.lemon.carmonitor.model.param;

import com.lemon.annotation.Module;
import com.lemon.model.BaseParam;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.param]
 * 类描述:    [APP登录用户，保存设备的报警配置]
 * 创建人:    [xflu]
 * 创建时间:  [2016/1/6 17:19]
 * 修改人:    [xflu]
 * 修改时间:  [2016/1/6 17:19]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Module(server = "dev_server", name = "apiApp")
public class SetDevAlarmParam extends BaseParam {

    private String loginToken;
    private String devSn;
    private boolean sos;
    private boolean expire;
    private boolean vibrate;
    private boolean offline;
    private boolean lowPower;
    private boolean powerOff;
    private boolean move;
    private boolean overSpeed;
    private boolean dropped;
    private boolean enableAlarm;
    private boolean enableRingtone;
    private boolean enableVibrate;
    private boolean enableSms;
    private boolean inFence;
    private boolean outFence;

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

    public String getSos() {
        return Boolean.toString(sos);
    }

    public void setSos(boolean sos) {
        this.sos = sos;
    }

    public String getVibrate() {
        return Boolean.toString(vibrate);
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public String getOffline() {
        return Boolean.toString(offline);
    }

    public void setOffline(boolean offline) {
        this.offline = offline;
    }

    public String getLowPower() {
        return Boolean.toString(lowPower);
    }

    public void setLowPower(boolean lowPower) {
        this.lowPower = lowPower;
    }

    public String getPowerOff() {
        return Boolean.toString(powerOff);
    }

    public void setPowerOff(boolean powerOff) {
        this.powerOff = powerOff;
    }

    public String getMove() {
        return Boolean.toString(move);
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public String getOverSpeed() {
        return Boolean.toString(overSpeed);
    }

    public void setOverSpeed(boolean overSpeed) {
        this.overSpeed = overSpeed;
    }

    public String getDropped() {
        return Boolean.toString(dropped);
    }

    public void setDropped(boolean dropped) {
        this.dropped = dropped;
    }

    public String getEnableAlarm() {
        return Boolean.toString(enableAlarm);
    }

    public void setEnableAlarm(boolean enableAlarm) {
        this.enableAlarm = enableAlarm;
    }

    public String getEnableRingtone() {
        return Boolean.toString(enableRingtone);
    }

    public void setEnableRingtone(boolean enableRingtone) {
        this.enableRingtone = enableRingtone;
    }

    public String getEnableVibrate() {
        return Boolean.toString(enableVibrate);
    }

    public void setEnableVibrate(boolean enableVibrate) {
        this.enableVibrate = enableVibrate;
    }

    public String getEnableSms() {
        return Boolean.toString(enableSms);
    }

    public void setEnableSms(boolean enableSms) {
        this.enableSms = enableSms;
    }

    public String getExpire() {
        return Boolean.toString(expire);
    }

    public void setExpire(boolean expire) {
        this.expire = expire;
    }

    public String getInFence() {
        return Boolean.toString(inFence);
    }

    public void setInFence(boolean inFence) {
        this.inFence = inFence;
    }

    public String getOutFence() {
        return Boolean.toString(outFence);
    }

    public void setOutFence(boolean outFence) {
        this.outFence = outFence;
    }

    @Override
    public String toString() {
        return "SetDevAlarmParam{" +
                "loginToken='" + loginToken + '\'' +
                ", devSn='" + devSn + '\'' +
                ", sos=" + sos +
                ", expire=" + expire +
                ", vibrate=" + vibrate +
                ", offline=" + offline +
                ", lowPower=" + lowPower +
                ", powerOff=" + powerOff +
                ", move=" + move +
                ", overSpeed=" + overSpeed +
                ", dropped=" + dropped +
                ", enableAlarm=" + enableAlarm +
                ", enableRingtone=" + enableRingtone +
                ", enableVibrate=" + enableVibrate +
                ", enableSms=" + enableSms +
                ", inFence=" + inFence +
                ", outFence=" + outFence +
                '}';
    }
}
