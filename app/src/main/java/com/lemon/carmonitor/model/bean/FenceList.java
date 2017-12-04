package com.lemon.carmonitor.model.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.bean]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/1/25 16:19]
 * 修改人:    [xflu]
 * 修改时间:  [2016/1/25 16:19]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FenceList {

    /**
     * status : 0
     * message : 成功
     * size : 1
     * fences : [{"fence_id":43,"name":"家1","description":"","valid_times":[{"begin_time":"0001","end_time":"2359"}],"valid_cycle":4,"valid_days":[1,2,3,4,5,6,7],"shape":1,"center":{"longitude":118.20518597370304,"latitude":24.498297398653268},"radius":5000,"vertexes":[],"alarm_condition":1,"creator":"4209504369Y4NZG3-E","observers":["4209504369Y4NZG3-E"],"monitored_persons":["4209504369Y4NZG3-E"],"create_time":"2016-01-25 16:14:17","update_time":"2016-01-25 21:49:50","valid_date":"20160125","coord_type":3}]
     */

    private int status;
    private String message;
    private int size;
    /**
     * fence_id : 43
     * name : 家1
     * description :
     * valid_times : [{"begin_time":"0001","end_time":"2359"}]
     * valid_cycle : 4
     * valid_days : [1,2,3,4,5,6,7]
     * shape : 1
     * center : {"longitude":118.20518597370304,"latitude":24.498297398653268}
     * radius : 5000.0
     * vertexes : []
     * alarm_condition : 1
     * creator : 4209504369Y4NZG3-E
     * observers : ["4209504369Y4NZG3-E"]
     * monitored_persons : ["4209504369Y4NZG3-E"]
     * create_time : 2016-01-25 16:14:17
     * update_time : 2016-01-25 21:49:50
     * valid_date : 20160125
     * coord_type : 3
     */

    private List<FencesEntity> fences;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setFences(List<FencesEntity> fences) {
        this.fences = fences;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public int getSize() {
        return size;
    }

    public List<FencesEntity> getFences() {
        return fences;
    }

    public static class FencesEntity {
        private int fence_id;
        private String name;
        private String description;
        private int valid_cycle;
        private int shape;
        /**
         * longitude : 118.20518597370304
         * latitude : 24.498297398653268
         */

        private CenterEntity center;
        private double radius;
        private int alarm_condition;
        private String creator;
        private String create_time;
        private String update_time;
        private String valid_date;
        private int coord_type;
        /**
         * begin_time : 0001
         * end_time : 2359
         */

        private List<ValidTimesEntity> valid_times;
        private List<Integer> valid_days;
        private List<?> vertexes;
        private List<String> observers;
        private List<String> monitored_persons;

        public void setFence_id(int fence_id) {
            this.fence_id = fence_id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setValid_cycle(int valid_cycle) {
            this.valid_cycle = valid_cycle;
        }

        public void setShape(int shape) {
            this.shape = shape;
        }

        public void setCenter(CenterEntity center) {
            this.center = center;
        }

        public void setRadius(double radius) {
            this.radius = radius;
        }

        public void setAlarm_condition(int alarm_condition) {
            this.alarm_condition = alarm_condition;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public void setValid_date(String valid_date) {
            this.valid_date = valid_date;
        }

        public void setCoord_type(int coord_type) {
            this.coord_type = coord_type;
        }

        public void setValid_times(List<ValidTimesEntity> valid_times) {
            this.valid_times = valid_times;
        }

        public void setValid_days(List<Integer> valid_days) {
            this.valid_days = valid_days;
        }

        public void setVertexes(List<?> vertexes) {
            this.vertexes = vertexes;
        }

        public void setObservers(List<String> observers) {
            this.observers = observers;
        }

        public void setMonitored_persons(List<String> monitored_persons) {
            this.monitored_persons = monitored_persons;
        }

        public int getFence_id() {
            return fence_id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public int getValid_cycle() {
            return valid_cycle;
        }

        public int getShape() {
            return shape;
        }

        public CenterEntity getCenter() {
            return center;
        }

        public double getRadius() {
            return radius;
        }

        public int getAlarm_condition() {
            return alarm_condition;
        }

        public String getCreator() {
            return creator;
        }

        public String getCreate_time() {
            return create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public String getValid_date() {
            return valid_date;
        }

        public int getCoord_type() {
            return coord_type;
        }

        public List<ValidTimesEntity> getValid_times() {
            return valid_times;
        }

        public List<Integer> getValid_days() {
            return valid_days;
        }

        public List<?> getVertexes() {
            return vertexes;
        }

        public List<String> getObservers() {
            return observers;
        }

        public List<String> getMonitored_persons() {
            return monitored_persons;
        }

        public static class CenterEntity {
            private double longitude;
            private double latitude;

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public double getLongitude() {
                return longitude;
            }

            public double getLatitude() {
                return latitude;
            }
        }

        public static class ValidTimesEntity {
            private String begin_time;
            private String end_time;

            public void setBegin_time(String begin_time) {
                this.begin_time = begin_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getBegin_time() {
                return begin_time;
            }

            public String getEnd_time() {
                return end_time;
            }
        }
    }
}
