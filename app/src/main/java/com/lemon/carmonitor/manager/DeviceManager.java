package com.lemon.carmonitor.manager;

import com.lemon.annotation.Autowired;
import com.lemon.annotation.Component;
import com.lemon.carmonitor.util.AppCacheManager;
import com.lemon.util.ParamUtils;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.manager]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/3/13 20:21]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/3/13 20:21]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Component
public class DeviceManager {
    @Autowired
    public AppCacheManager appCacheManager;

    public boolean checkCurrentDevice(){
        if(ParamUtils.isEmpty(appCacheManager.getCurrentDevSn())){
            return false;
        }
        return true;
    }

}
