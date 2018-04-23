package com.lemon.carmonitor.race.model.param;

/**
 * 项目名称:  [CarMonitor-3]
 * 包:        [com.lemon.carmonitor.race.model]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/10/9 16:55]
 * 修改人:    [xflu]
 * 修改时间:  [2016/10/9 16:55]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class RaceTrackingParam extends BaseRaceParam {

    /**
     * DeviceId : 1
     * MapType : sample string 2
     * Token : sample string 3
     * Language : sample string 4
     * AppId : sample string 5
     */

    private int DeviceId;
    private String MapType;
    private String Token;
    private String Language;
    private String AppId;

    public void setDeviceId(int DeviceId) {
        this.DeviceId = DeviceId;
    }

    public void setMapType(String MapType) {
        this.MapType = MapType;
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

    public String getMapType() {
        return MapType;
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
