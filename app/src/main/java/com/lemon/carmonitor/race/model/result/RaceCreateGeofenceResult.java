package com.lemon.carmonitor.race.model.result;

/**
 * 项目名称:  [CarMonitor-3]
 * 包:        [com.lemon.carmonitor.race.model.result]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/10/9 17:27]
 * 修改人:    [xflu]
 * 修改时间:  [2016/10/9 17:27]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class RaceCreateGeofenceResult extends BaseRaceResult {

    /**
     * Message : sample string 1
     * State : 2
     */

    private String Message;
    private int State;

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public void setState(int State) {
        this.State = State;
    }

    public String getMessage() {
        return Message;
    }

    public int getState() {
        return State;
    }
}
