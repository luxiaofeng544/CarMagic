package com.lemon.carmonitor.event;

import com.baidu.mapapi.model.LatLng;
import com.lemon.carmonitor.trace.HistoryTrackData;

import java.util.List;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.event]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/2/16 13:21]
 * 修改人:    [xflu]
 * 修改时间:  [2016/2/16 13:21]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class DrawHistoryEvent {

    private List<LatLng> latLngList;
    private double distance;
    private HistoryTrackData historyTrackData;

    public DrawHistoryEvent(List<LatLng> latLngList, double distance) {
        this.latLngList = latLngList;
        this.distance = distance;
    }

    public DrawHistoryEvent(List<LatLng> latLngList, double distance, HistoryTrackData historyTrackData) {
        this.latLngList = latLngList;
        this.distance = distance;
        this.historyTrackData = historyTrackData;
    }

    public HistoryTrackData getHistoryTrackData() {
        return historyTrackData;
    }

    public void setHistoryTrackData(HistoryTrackData historyTrackData) {
        this.historyTrackData = historyTrackData;
    }

    public List<LatLng> getLatLngList() {
        return latLngList;
    }

    public void setLatLngList(List<LatLng> latLngList) {
        this.latLngList = latLngList;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
