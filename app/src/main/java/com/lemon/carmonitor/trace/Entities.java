package com.lemon.carmonitor.trace;

import com.google.gson.annotations.SerializedName;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.*;
/**
 *
 * @author dibyajyotidalai
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Entities {
    private static Entities instance;
    @SerializedName("entity_name")
    private String mEntity_name;
    @SerializedName("create_time")
    private String mCreate_time;
    @SerializedName("modify_time")
    private String mModify_time;
    @SerializedName("realtime_point")
    private Realtime_point mRealtime_point;
    @SerializedName("DEV_SN")
    private String DEV_SN;
    /**
     * set entity_name
     *
     * @param pEntity_name
     */
    public void setEntity_name(String pEntity_name){
        this.mEntity_name = pEntity_name;
    }
    /**
     * @return String
     *
     */
    public String getEntity_name(){
        return mEntity_name;
    }
    /**
     * set create_time
     *
     * @param pCreate_time
     */
    public void setCreate_time(String pCreate_time){
        this.mCreate_time = pCreate_time;
    }
    /**
     * @return String
     *
     */
    public String getCreate_time(){
        return mCreate_time;
    }
    /**
     * set modify_time
     *
     * @param pModify_time
     */
    public void setModify_time(String pModify_time){
        this.mModify_time = pModify_time;
    }
    /**
     * @return String
     *
     */
    public String getModify_time(){
        return mModify_time;
    }
    /**
     * set Realtime_point
     *
     * @param pRealtime_point
     */
    public void setRealtime_point(Realtime_point pRealtime_point){
        this.mRealtime_point = pRealtime_point;
    }
    /**
     * @return Realtime_point
     *
     */
    public Realtime_point getRealtime_point(){
        return mRealtime_point;
    }

    public String getDEV_SN() {
        return DEV_SN;
    }

    public void setDEV_SN(String DEV_SN) {
        this.DEV_SN = DEV_SN;
    }
}