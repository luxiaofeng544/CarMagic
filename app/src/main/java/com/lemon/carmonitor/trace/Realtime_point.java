package com.lemon.carmonitor.trace;


import com.google.gson.annotations.SerializedName;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.*;
/**
 *
 * @author dibyajyotidalai
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Realtime_point {
    private static Realtime_point instance;
    @SerializedName("radius")
    private Number mRadius = 0;
    @SerializedName("location")
    private String[] mLocation;
    @SerializedName("loc_time")
    private Number mLoc_time = 0;
    @SerializedName("speed")
    private Number mSpeed = 0;
    @SerializedName("direction")
    private Number mDirection = 0;
    @SerializedName("gpsValid")
    private Number gpsValid = 0;
    @SerializedName("rectification")
    private Number rectification = 0;
    @SerializedName("acc")
    private Number acc = -1;

    /**
     * set radius
     *
     * @param pRadius
     */
    public void setRadius(Number pRadius){
        this.mRadius = pRadius;
    }
    /**
     * @return Number
     *
     */
    public Number getRadius(){
        return mRadius;
    }
    /**
     * set location
     *
     * @param pLocation
     */
    public void setLocation(String[] pLocation){
        this.mLocation = pLocation;
    }
    /**
     * @return String
     *
     */
    public String[] getLocation(){
        return mLocation;
    }
    /**
     * set loc_time
     *
     * @param pLoc_time
     */
    public void setLoc_time(Number pLoc_time){
        this.mLoc_time = pLoc_time;
    }
    /**
     * @return Number
     *
     */
    public Number getLoc_time(){
        return mLoc_time;
    }
    /**
     * set speed
     *
     * @param pSpeed
     */
    public void setSpeed(Number pSpeed){
        this.mSpeed = pSpeed;
    }
    /**
     * @return Number
     *
     */
    public Number getSpeed(){
        return mSpeed;
    }
    /**
     * set direction
     *
     * @param pDirection
     */
    public void setDirection(Number pDirection){
        this.mDirection = pDirection;
    }
    /**
     * @return Number
     *
     */
    public Number getDirection(){
        return mDirection;
    }

    public Number getGpsValid() {
        return gpsValid;
    }

    public void setGpsValid(Number gpsValid) {
        this.gpsValid = gpsValid;
    }

    public Number getRectification() {
        return rectification;
    }

    public void setRectification(Number rectification) {
        this.rectification = rectification;
    }

    public Number getAcc() {
        return acc;
    }

    public void setAcc(Number acc) {
        this.acc = acc;
    }
}