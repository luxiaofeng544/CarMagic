package com.lemon.carmonitor.race.model.param;

import java.util.List;

/**
 * 项目名称:  [CarMonitor-3]
 * 包:        [com.lemon.carmonitor.race.model.param]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/10/9 17:26]
 * 修改人:    [xflu]
 * 修改时间:  [2016/10/9 17:26]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class RaceCreateGeofenceParam extends BaseRaceParam {

    /**
     * FenceId : 1
     * DeviceId : 2
     * FenceName : sample string 3
     * Latitude : 4.0
     * Longitude : 5.0
     * Radius : 6.0
     * FenceType : 7
     * AlarmType : 8
     * IsDeviceFence : true
     * AlarmModel : 10
     * DeviceFenceNo : 11
     * Description : sample string 12
     * IMEIList : ["sample string 1","sample string 2"]
     * Points : [{"Lat":1,"Lng":2,"SortOrder":3},{"Lat":1,"Lng":2,"SortOrder":3}]
     * Address : sample string 13
     * InUse : true
     * StartTime : sample string 15
     * EndTime : sample string 16
     */

    private ItemEntity Item;
    /**
     * Item : {"FenceId":1,"DeviceId":2,"FenceName":"sample string 3","Latitude":4,"Longitude":5,"Radius":6,"FenceType":7,"AlarmType":8,"IsDeviceFence":true,"AlarmModel":10,"DeviceFenceNo":11,"Description":"sample string 12","IMEIList":["sample string 1","sample string 2"],"Points":[{"Lat":1,"Lng":2,"SortOrder":3},{"Lat":1,"Lng":2,"SortOrder":3}],"Address":"sample string 13","InUse":true,"StartTime":"sample string 15","EndTime":"sample string 16"}
     * MapType : sample string 1
     * Token : sample string 2
     * Language : sample string 3
     * AppId : sample string 4
     */

    private String MapType;
    private String Token;
    private String Language;
    private String AppId;

    public void setItem(ItemEntity Item) {
        this.Item = Item;
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

    public ItemEntity getItem() {
        return Item;
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

    public static class ItemEntity {
        private int FenceId;
        private int DeviceId;
        private String FenceName;
        private double Latitude;
        private double Longitude;
        private double Radius;
        private int FenceType;
        private int AlarmType;
        private boolean IsDeviceFence;
        private int AlarmModel;
        private int DeviceFenceNo;
        private String Description;
        private String Address;
        private boolean InUse;
        private String StartTime;
        private String EndTime;
        private List<String> IMEIList;
        /**
         * Lat : 1.0
         * Lng : 2.0
         * SortOrder : 3
         */

        private List<PointsEntity> Points;

        public void setFenceId(int FenceId) {
            this.FenceId = FenceId;
        }

        public void setDeviceId(int DeviceId) {
            this.DeviceId = DeviceId;
        }

        public void setFenceName(String FenceName) {
            this.FenceName = FenceName;
        }

        public void setLatitude(double Latitude) {
            this.Latitude = Latitude;
        }

        public void setLongitude(double Longitude) {
            this.Longitude = Longitude;
        }

        public void setRadius(double Radius) {
            this.Radius = Radius;
        }

        public void setFenceType(int FenceType) {
            this.FenceType = FenceType;
        }

        public void setAlarmType(int AlarmType) {
            this.AlarmType = AlarmType;
        }

        public void setIsDeviceFence(boolean IsDeviceFence) {
            this.IsDeviceFence = IsDeviceFence;
        }

        public void setAlarmModel(int AlarmModel) {
            this.AlarmModel = AlarmModel;
        }

        public void setDeviceFenceNo(int DeviceFenceNo) {
            this.DeviceFenceNo = DeviceFenceNo;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public void setInUse(boolean InUse) {
            this.InUse = InUse;
        }

        public void setStartTime(String StartTime) {
            this.StartTime = StartTime;
        }

        public void setEndTime(String EndTime) {
            this.EndTime = EndTime;
        }

        public void setIMEIList(List<String> IMEIList) {
            this.IMEIList = IMEIList;
        }

        public void setPoints(List<PointsEntity> Points) {
            this.Points = Points;
        }

        public int getFenceId() {
            return FenceId;
        }

        public int getDeviceId() {
            return DeviceId;
        }

        public String getFenceName() {
            return FenceName;
        }

        public double getLatitude() {
            return Latitude;
        }

        public double getLongitude() {
            return Longitude;
        }

        public double getRadius() {
            return Radius;
        }

        public int getFenceType() {
            return FenceType;
        }

        public int getAlarmType() {
            return AlarmType;
        }

        public boolean isIsDeviceFence() {
            return IsDeviceFence;
        }

        public int getAlarmModel() {
            return AlarmModel;
        }

        public int getDeviceFenceNo() {
            return DeviceFenceNo;
        }

        public String getDescription() {
            return Description;
        }

        public String getAddress() {
            return Address;
        }

        public boolean isInUse() {
            return InUse;
        }

        public String getStartTime() {
            return StartTime;
        }

        public String getEndTime() {
            return EndTime;
        }

        public List<String> getIMEIList() {
            return IMEIList;
        }

        public List<PointsEntity> getPoints() {
            return Points;
        }

        public static class PointsEntity {
            private double Lat;
            private double Lng;
            private int SortOrder;

            public void setLat(double Lat) {
                this.Lat = Lat;
            }

            public void setLng(double Lng) {
                this.Lng = Lng;
            }

            public void setSortOrder(int SortOrder) {
                this.SortOrder = SortOrder;
            }

            public double getLat() {
                return Lat;
            }

            public double getLng() {
                return Lng;
            }

            public int getSortOrder() {
                return SortOrder;
            }
        }
    }
}
