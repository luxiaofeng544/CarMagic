package com.lemon.carmonitor.event;

import com.lemon.carmonitor.trace.TrackData;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.event]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/2/15 16:06]
 * 修改人:    [xflu]
 * 修改时间:  [2016/2/15 16:06]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class FindEntityEvent {

    private String type;
    private TrackData trackData;

    public FindEntityEvent(String type, TrackData trackData) {
        this.type = type;
        this.trackData = trackData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TrackData getTrackData() {
        return trackData;
    }

    public void setTrackData(TrackData trackData) {
        this.trackData = trackData;
    }

    @Override
    public String toString() {
        return "FindEntityEvent{" +
                "type='" + type + '\'' +
                ", trackData=" + trackData +
                '}';
    }
}
