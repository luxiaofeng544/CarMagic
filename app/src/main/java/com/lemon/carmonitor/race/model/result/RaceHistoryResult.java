package com.lemon.carmonitor.race.model.result;

import java.util.List;

/**
 * 项目名称:  [CarMonitor-3]
 * 包:        [com.lemon.carmonitor.race.model.result]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/10/9 17:16]
 * 修改人:    [xflu]
 * 修改时间:  [2016/10/9 17:16]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class RaceHistoryResult extends BaseRaceResult {

    /**
     * State : 1
     * LastLocationId : 2
     * LastDeviceUtcDate : sample string 3
     * Items : [{"LocationId":1,"Time":"sample string 2","Lat":3,"Lng":4,"Speed":5,"Course":6,"IsStop":7,"StopTime":8,"Icon":"sample string 9","DataType":10},{"LocationId":1,"Time":"sample string 2","Lat":3,"Lng":4,"Speed":5,"Course":6,"IsStop":7,"StopTime":8,"Icon":"sample string 9","DataType":10}]
     */

    private int State;
    private int LastLocationId;
    private String LastDeviceUtcDate;
    /**
     * LocationId : 1
     * Time : sample string 2
     * Lat : 3.0
     * Lng : 4.0
     * Speed : 5.0
     * Course : 6.0
     * IsStop : 7
     * StopTime : 8
     * Icon : sample string 9
     * DataType : 10
     */

    private List<ItemsEntity> Items;

    public void setState(int State) {
        this.State = State;
    }

    public void setLastLocationId(int LastLocationId) {
        this.LastLocationId = LastLocationId;
    }

    public void setLastDeviceUtcDate(String LastDeviceUtcDate) {
        this.LastDeviceUtcDate = LastDeviceUtcDate;
    }

    public void setItems(List<ItemsEntity> Items) {
        this.Items = Items;
    }

    public int getState() {
        return State;
    }

    public int getLastLocationId() {
        return LastLocationId;
    }

    public String getLastDeviceUtcDate() {
        return LastDeviceUtcDate;
    }

    public List<ItemsEntity> getItems() {
        return Items;
    }

    public static class ItemsEntity {
        private int LocationId;
        private String Time;
        private double Lat;
        private double Lng;
        private double Speed;
        private double Course;
        private int IsStop;
        private int StopTime;
        private String Icon;
        private int DataType;

        public void setLocationId(int LocationId) {
            this.LocationId = LocationId;
        }

        public void setTime(String Time) {
            this.Time = Time;
        }

        public void setLat(double Lat) {
            this.Lat = Lat;
        }

        public void setLng(double Lng) {
            this.Lng = Lng;
        }

        public void setSpeed(double Speed) {
            this.Speed = Speed;
        }

        public void setCourse(double Course) {
            this.Course = Course;
        }

        public void setIsStop(int IsStop) {
            this.IsStop = IsStop;
        }

        public void setStopTime(int StopTime) {
            this.StopTime = StopTime;
        }

        public void setIcon(String Icon) {
            this.Icon = Icon;
        }

        public void setDataType(int DataType) {
            this.DataType = DataType;
        }

        public int getLocationId() {
            return LocationId;
        }

        public String getTime() {
            return Time;
        }

        public double getLat() {
            return Lat;
        }

        public double getLng() {
            return Lng;
        }

        public double getSpeed() {
            return Speed;
        }

        public double getCourse() {
            return Course;
        }

        public int getIsStop() {
            return IsStop;
        }

        public int getStopTime() {
            return StopTime;
        }

        public String getIcon() {
            return Icon;
        }

        public int getDataType() {
            return DataType;
        }
    }
}
