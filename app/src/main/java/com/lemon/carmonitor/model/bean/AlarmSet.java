package com.lemon.carmonitor.model.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.bean]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/1/20 21:25]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/1/20 21:25]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlarmSet {

    /**
     * sos : true
     * vibrate : true
     * offline : true
     * lowPower : true
     * powerOff : true
     * move : true
     * overSpeed : true
     * dropped : true
     * enableAlarm : true
     * enableRingtone : true
     * enableVibrate : true
     */

    private boolean sos;
    private boolean vibrate;
    private boolean offline;
    private boolean lowPower;
    private boolean powerOff;
    private boolean move;
    private boolean overSpeed;
    private boolean expire;
    private boolean dropped;
    private boolean close;
    private boolean sound;
    private boolean antiDismantle;
    private boolean stay;
    private boolean inFence;
    private boolean outFence;

    public void setSos(boolean sos) {
        this.sos = sos;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public void setOffline(boolean offline) {
        this.offline = offline;
    }

    public void setLowPower(boolean lowPower) {
        this.lowPower = lowPower;
    }

    public void setPowerOff(boolean powerOff) {
        this.powerOff = powerOff;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public void setOverSpeed(boolean overSpeed) {
        this.overSpeed = overSpeed;
    }

    public void setDropped(boolean dropped) {
        this.dropped = dropped;
    }

    public boolean isSos() {
        return sos;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public boolean isOffline() {
        return offline;
    }

    public boolean isLowPower() {
        return lowPower;
    }

    public boolean isPowerOff() {
        return powerOff;
    }

    public boolean isMove() {
        return move;
    }

    public boolean isOverSpeed() {
        return overSpeed;
    }

    public boolean isDropped() {
        return dropped;
    }

    public boolean isExpire() {
        return expire;
    }

    public void setExpire(boolean expire) {
        this.expire = expire;
    }

    public boolean isInFence() {
        return inFence;
    }

    public void setInFence(boolean inFence) {
        this.inFence = inFence;
    }

    public boolean isOutFence() {
        return outFence;
    }

    public void setOutFence(boolean outFence) {
        this.outFence = outFence;
    }

    public boolean isStay() {
        return stay;
    }

    public void setStay(boolean stay) {
        this.stay = stay;
    }

    public boolean isClose() {
        return close;
    }

    public void setClose(boolean close) {
        this.close = close;
    }

    public boolean isSound() {
        return sound;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
    }

    public boolean isAntiDismantle() {
        return antiDismantle;
    }

    public void setAntiDismantle(boolean antiDismantle) {
        this.antiDismantle = antiDismantle;
    }
}
