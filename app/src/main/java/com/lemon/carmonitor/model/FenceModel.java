package com.lemon.carmonitor.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class FenceModel {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "fenceId")
    private String fenceId;
    @DatabaseField(columnName = "fenceName")
    private String fenceName;
    @DatabaseField(columnName = "fenceRadius")
    private String fenceRadius;
    @DatabaseField(columnName = "entityName")
    private String entityName;
    @DatabaseField(columnName = "devSn")
    private String devSn;
    @DatabaseField(columnName = "longitude")
    private double longitude;
    @DatabaseField(columnName = "latitude")
    private double latitude;
    @DatabaseField(columnName = "type")
    private String type;//安全 危险
    @DatabaseField(columnName = "radius")
    private String radius;
    @DatabaseField(columnName = "validDays")
    private String validDays;
    @DatabaseField(columnName = "validTimes")
    private String validTimes;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFenceId() {
        return fenceId;
    }

    public void setFenceId(String fenceId) {
        this.fenceId = fenceId;
    }

    public String getFenceName() {
        return fenceName;
    }

    public void setFenceName(String fenceName) {
        this.fenceName = fenceName;
    }

    public String getFenceRadius() {
        return fenceRadius;
    }

    public void setFenceRadius(String fenceRadius) {
        this.fenceRadius = fenceRadius;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDevSn() {
        return devSn;
    }

    public void setDevSn(String devSn) {
        this.devSn = devSn;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getValidDays() {
        return validDays;
    }

    public void setValidDays(String validDays) {
        this.validDays = validDays;
    }

    public String getValidTimes() {
        return validTimes;
    }

    public void setValidTimes(String validTimes) {
        this.validTimes = validTimes;
    }

    @Override
    public String toString() {
        return "FenceModel{" +
                "id=" + id +
                ", fenceId='" + fenceId + '\'' +
                ", fenceName='" + fenceName + '\'' +
                ", fenceRadius='" + fenceRadius + '\'' +
                ", entityName='" + entityName + '\'' +
                ", devSn='" + devSn + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", type='" + type + '\'' +
                ", radius='" + radius + '\'' +
                ", validDays='" + validDays + '\'' +
                ", validTimes='" + validTimes + '\'' +
                '}';
    }
}
