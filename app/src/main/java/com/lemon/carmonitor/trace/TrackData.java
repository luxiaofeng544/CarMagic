package com.lemon.carmonitor.trace;
import com.google.gson.annotations.SerializedName;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.*;
/**
 *
 * @author dibyajyotidalai
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackData {
    private static TrackData instance;
    @SerializedName("status")
    private Number mStatus = 0;
    @SerializedName("size")
    private Number mSize = 0;
    @SerializedName("total")
    private Number mTotal = 0;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("entities")
    private ArrayList<Entities> mEntities;
    /**
     * set status
     *
     * @param pStatus
     */
    public void setStatus(Number pStatus){
        this.mStatus = pStatus;
    }
    /**
     * @return Number
     *
     */
    public Number getStatus(){
        return mStatus;
    }
    /**
     * set size
     *
     * @param pSize
     */
    public void setSize(Number pSize){
        this.mSize = pSize;
    }
    /**
     * @return Number
     *
     */
    public Number getSize(){
        return mSize;
    }
    /**
     * set total
     *
     * @param pTotal
     */
    public void setTotal(Number pTotal){
        this.mTotal = pTotal;
    }
    /**
     * @return Number
     *
     */
    public Number getTotal(){
        return mTotal;
    }
    /**
     * set message
     *
     * @param pMessage
     */
    public void setMessage(String pMessage){
        this.mMessage = pMessage;
    }
    /**
     * @return String
     *
     */
    public String getMessage(){
        return mMessage;
    }
    /**
     * set collection of Entities
     *
     * @param pEntities
     */
    public void setEntities(ArrayList<Entities> pEntities){
        this.mEntities = pEntities;
    }
    /**
     * @return ArrayList<Entities>
     *
     */
    public ArrayList<Entities> getEntities(){
        return mEntities;
    }
}