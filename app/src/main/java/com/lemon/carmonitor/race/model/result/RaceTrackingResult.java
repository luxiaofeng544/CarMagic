package com.lemon.carmonitor.race.model.result;

import com.lemon.carmonitor.race.model.param.RaceTrackingParam;

/**
 * 项目名称:  [CarMonitor-3]
 * 包:        [com.lemon.carmonitor.race.model.result]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/10/9 17:03]
 * 修改人:    [xflu]
 * 修改时间:  [2016/10/9 17:03]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class RaceTrackingResult extends RaceTrackingParam {

    /**
     * DeviceId : 1
     * DeviceName : sample string 2
     * Latitude : 3.0
     * Longitude : 4.0
     * OLat : 5.0
     * OLng : 6.0
     * ShowSpeed : 7
     * Speed : 8.0
     * Course : 9.0
     * IsStop : 10
     * Acc : 11
     * Status : 12
     * DeviceUtcDate : sample string 13
     * LastCommunication : sample string 14
     * ShowDataType : 15
     * DataType : 16
     * ShowBattery : 17
     * Battery : 18
     * Icon : sample string 19
     * Distance : 20.0
     */

    private ItemEntity Item;
    /**
     * Item : {"DeviceId":1,"DeviceName":"sample string 2","Latitude":3,"Longitude":4,"OLat":5,"OLng":6,"ShowSpeed":7,"Speed":8,"Course":9,"IsStop":10,"Acc":11,"Status":12,"DeviceUtcDate":"sample string 13","LastCommunication":"sample string 14","ShowDataType":15,"DataType":16,"ShowBattery":17,"Battery":18,"Icon":"sample string 19","Distance":20}
     * State : 1
     */

    private int State;

    public void setItem(ItemEntity Item) {
        this.Item = Item;
    }

    public void setState(int State) {
        this.State = State;
    }

    public ItemEntity getItem() {
        return Item;
    }

    public int getState() {
        return State;
    }

    public static class ItemEntity {
        private int DeviceId;
        private String DeviceName;
        private double Latitude;
        private double Longitude;
        private double OLat;
        private double OLng;
        private int ShowSpeed;
        private double Speed;
        private double Course;
        private int IsStop;
        private int Acc;
        private int Status;
        private String DeviceUtcDate;
        private String LastCommunication;
        private int ShowDataType;
        private int DataType;
        private int ShowBattery;
        private int Battery;
        private String Icon;
        private double Distance;

        public void setDeviceId(int DeviceId) {
            this.DeviceId = DeviceId;
        }

        public void setDeviceName(String DeviceName) {
            this.DeviceName = DeviceName;
        }

        public void setLatitude(double Latitude) {
            this.Latitude = Latitude;
        }

        public void setLongitude(double Longitude) {
            this.Longitude = Longitude;
        }

        public void setOLat(double OLat) {
            this.OLat = OLat;
        }

        public void setOLng(double OLng) {
            this.OLng = OLng;
        }

        public void setShowSpeed(int ShowSpeed) {
            this.ShowSpeed = ShowSpeed;
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

        public void setAcc(int Acc) {
            this.Acc = Acc;
        }

        public void setStatus(int Status) {
            this.Status = Status;
        }

        public void setDeviceUtcDate(String DeviceUtcDate) {
            this.DeviceUtcDate = DeviceUtcDate;
        }

        public void setLastCommunication(String LastCommunication) {
            this.LastCommunication = LastCommunication;
        }

        public void setShowDataType(int ShowDataType) {
            this.ShowDataType = ShowDataType;
        }

        public void setDataType(int DataType) {
            this.DataType = DataType;
        }

        public void setShowBattery(int ShowBattery) {
            this.ShowBattery = ShowBattery;
        }

        public void setBattery(int Battery) {
            this.Battery = Battery;
        }

        public void setIcon(String Icon) {
            this.Icon = Icon;
        }

        public void setDistance(double Distance) {
            this.Distance = Distance;
        }

        public int getDeviceId() {
            return DeviceId;
        }

        public String getDeviceName() {
            return DeviceName;
        }

        public double getLatitude() {
            return Latitude;
        }

        public double getLongitude() {
            return Longitude;
        }

        public double getOLat() {
            return OLat;
        }

        public double getOLng() {
            return OLng;
        }

        public int getShowSpeed() {
            return ShowSpeed;
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

        public int getAcc() {
            return Acc;
        }

        public int getStatus() {
            return Status;
        }

        public String getDeviceUtcDate() {
            return DeviceUtcDate;
        }

        public String getLastCommunication() {
            return LastCommunication;
        }

        public int getShowDataType() {
            return ShowDataType;
        }

        public int getDataType() {
            return DataType;
        }

        public int getShowBattery() {
            return ShowBattery;
        }

        public int getBattery() {
            return Battery;
        }

        public String getIcon() {
            return Icon;
        }

        public double getDistance() {
            return Distance;
        }
    }
}
