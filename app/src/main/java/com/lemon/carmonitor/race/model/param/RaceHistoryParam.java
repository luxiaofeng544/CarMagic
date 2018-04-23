package com.lemon.carmonitor.race.model.param;

/**
 * 项目名称:  [CarMonitor-3]
 * 包:        [com.lemon.carmonitor.race.model.param]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/10/9 17:08]
 * 修改人:    [xflu]
 * 修改时间:  [2016/10/9 17:08]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class RaceHistoryParam extends BaseRaceParam {

    /**
     * DeviceId : 1
     * StartTime : 2016-10-09T17:07:46.9682059+08:00
     * EndTime : 2016-10-09T17:07:46.9691825+08:00
     * ShowLbs : 4
     * MapType : sample string 5
     * SelectCount : 6
     * Token : sample string 7
     * Language : sample string 8
     * AppId : sample string 9
     */

    private int DeviceId;
    private String StartTime;
    private String EndTime;
    private int ShowLbs;
    private String MapType;
    private int SelectCount;
    private String Token;
    private String Language;
    private String AppId;

    public void setDeviceId(int DeviceId) {
        this.DeviceId = DeviceId;
    }

    public void setStartTime(String StartTime) {
        this.StartTime = StartTime;
    }

    public void setEndTime(String EndTime) {
        this.EndTime = EndTime;
    }

    public void setShowLbs(int ShowLbs) {
        this.ShowLbs = ShowLbs;
    }

    public void setMapType(String MapType) {
        this.MapType = MapType;
    }

    public void setSelectCount(int SelectCount) {
        this.SelectCount = SelectCount;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }

    public void setLanguage(String Language) {
        this.Language = Language;
    }

    public void setAppId(String AppId) {
        this.AppId = AppId;
    }

    public int getDeviceId() {
        return DeviceId;
    }

    public String getStartTime() {
        return StartTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public int getShowLbs() {
        return ShowLbs;
    }

    public String getMapType() {
        return MapType;
    }

    public int getSelectCount() {
        return SelectCount;
    }

    public String getToken() {
        return Token;
    }

    public String getLanguage() {
        return Language;
    }

    public String getAppId() {
        return AppId;
    }
}
