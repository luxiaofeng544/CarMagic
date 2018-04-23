package com.lemon.config;

import android.content.Context;

import com.lemon.bean.BeanFactory;
import com.lemon.carmonitor.util.AppCacheManager;
import com.lemon.exception.AppException;
import com.lemon.util.LogUtils;
import com.lemon.util.ParamUtils;
import com.lemon.util.RegUtils;
import com.lemon.util.StringUtils;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon.config]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2015/12/18 14:35]
 * 修改人:    [xflu]
 * 修改时间:  [2015/12/18 14:35]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class Config {

    public Context mContext;
    public String configPath;
    public static Map<String,String> configMap = new HashMap<>();

    public void parser() throws IOException {
        String strConfig = StringUtils.inputStream2String(mContext.getAssets().open(configPath));
        if(ParamUtils.isEmpty(strConfig)){
            String message = "can not find the config.json , please check the path configPath:"+configPath;
            LogUtils.e(message);
            throw new AppException(message);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        configMap = objectMapper.readValue(strConfig,Map.class);
    }

    public static boolean isDebug(){
        return configMap.get("debug").equals("true");
    }

    public static boolean isAutoLogin(){
        return configMap.get("is_auto_login").equals("true");
    }

    public static String getServerUrl(){
        return configMap.get("server_url");
    }

    public static String getConvertExt(){
        return configMap.get("convert_ext");
    }

    public static String getValue(String key){
        return configMap.get(key);
    }

    public static int getIntValue(String key){
        return Integer.valueOf(configMap.get(key));
    }

    public static boolean getBooleanValue(String key){
        return Boolean.valueOf(configMap.get(key));
    }

    public static int getSmsCodeLength(){
        return ParamUtils.isEmpty(configMap.get("smscodelength"))|| !RegUtils.isNumber(configMap.get("smscodelength"))?6:Integer.valueOf(configMap.get("smscodelength"));
    }

    public static int getPwdLength(){
        return ParamUtils.isEmpty(configMap.get("pwdlength"))|| !RegUtils.isNumber(configMap.get("pwdlength"))?6:Integer.valueOf(configMap.get("pwdlength"));
    }

    public static int getServiceId(){
        String serviceId = BeanFactory.getInstance().getBean(AppCacheManager.class).getCurrentServiceId();
        if(!ParamUtils.isEmpty(serviceId)){
            return Integer.valueOf(serviceId);
        }
        return ParamUtils.isEmpty(configMap.get("serviceId"))?0:Integer.valueOf(configMap.get("serviceId"));
    }

    public static String getEntityName(){
        return configMap.get("entityName");
    }

    public static String getDbName(){
        return ParamUtils.isEmpty(configMap.get("db_name"))?"lemon.db":configMap.get("db_name");
    }
    public static int getDbVersion(){
        return ParamUtils.isEmpty(configMap.get("db_version"))?1:Integer.valueOf(configMap.get("db_version"));
    }

    public static boolean isUpdate(){
        return configMap.get("update").equals("true");
    }

    public static boolean isCheckUpdate(){
        return configMap.get("checkUpdate").equals("true");
    }

}
