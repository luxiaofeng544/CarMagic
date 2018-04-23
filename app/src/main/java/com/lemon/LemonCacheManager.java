package com.lemon;

import android.content.Context;

import com.lemon.annotation.Autowired;
import com.lemon.annotation.Component;
import com.lemon.util.SettingUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon.util]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/1/7 11:31]
 * 修改人:    [xflu]
 * 修改时间:  [2016/1/7 11:31]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Component
public class LemonCacheManager {

    private static Map<String, Object> cacheMap = new HashMap<>();

    @Autowired
    public Context mContext;

    public void putBean(String key, Object object) {
        cacheMap.put(key, object);
    }

    public Object getBean(String key) {
        return cacheMap.get(key);
    }

    public boolean containBean(String key) {
        return cacheMap.containsKey(key);
    }
    public boolean containBean(Class cls){
        return cacheMap.containsKey(cls.getSimpleName());
    }

    public void putBean(Class cls, Object object) {
        cacheMap.put(cls.getSimpleName(), object);
    }

    public <T> T getBean(String key, Class<T> cls) {
        return (T) cacheMap.get(key);
    }

    public <T> T getBean(Class cls) {
        return (T) cacheMap.get(cls.getSimpleName());
    }

    public void removeBean(String key) {
        if (cacheMap.containsKey(key)) {
            cacheMap.remove(key);
        }
    }


    public void setCurrentToken(String token) {
        SettingUtils.set(mContext, "token", token);
    }

    public String getCurrentToken() {
        return SettingUtils.get(mContext, "token", "");
    }
    public void setCurrentMobile(String mobile) {
        SettingUtils.set(mContext, "mobile", mobile);
    }

    public String getCurrentMobile() {
        return SettingUtils.get(mContext, "mobile", "");
    }
    public void setCurrentServiceId(String serviceId) {
        SettingUtils.set(mContext, "serviceId", serviceId);
    }

    public String getCurrentServiceId() {
        return SettingUtils.get(mContext, "serviceId", "");
    }

    public void setCurrentPassword(String password) {
        SettingUtils.set(mContext, "password", password);
    }

    public String getCurrentPassword() {
        return SettingUtils.get(mContext, "password", "");
    }

    public void setShowStationNode(String stationNode) {
        SettingUtils.set(mContext, "stationNode", stationNode);
    }

    public String getShowStationNode() {
        return SettingUtils.get(mContext, "stationNode", "true");
    }

    public void setCurrentDevSn(String devSn) {
        cacheMap.put( "devSn", devSn);
    }

    public String getCurrentDevSn() {
        if(cacheMap.containsKey("devSn")){
            return cacheMap.get("devSn").toString();
        }
        return "";
    }

    public void setCurrentProType(String proType) {
        cacheMap.put("proType", proType);
    }

    public String getCurrentProType() {
        if(cacheMap.containsKey("proType")){
            return cacheMap.get("proType").toString();
        }
        return "";
    }

    public void setCurrentEntityName(String entityName) {
        cacheMap.put("entityName", entityName);
    }

    public String getCurrentEntityName() {
        if(cacheMap.containsKey("entityName")){
            return cacheMap.get("entityName").toString();
        }
        return "";
    }

    public void cleanCache() {
        cacheMap.remove("token");
        SettingUtils.remove(mContext, "mobile");
        SettingUtils.remove(mContext, "password");
        cacheMap.remove("devSn");
    }

}
