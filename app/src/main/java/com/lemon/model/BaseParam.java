package com.lemon.model;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.model]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2015/12/16 14:15]
 * 修改人:    [xflu]
 * 修改时间:  [2015/12/16 14:15]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class BaseParam {
    public boolean showDialog = true;
    protected boolean raceInterface = false;//尚锐接口
    private String invokeType;

    public void setShowDialog(boolean showDialog) {
        this.showDialog = showDialog;
    }

    public boolean getShowDialog(){
        return showDialog;
    }

    public boolean getRaceInterface() {
        return raceInterface;
    }

    public void setRaceInterface(boolean raceInterface) {
        this.raceInterface = raceInterface;
    }

    public String getInvokeType() {
        return invokeType;
    }

    public void setInvokeType(String invokeType) {
        this.invokeType = invokeType;
    }
}
