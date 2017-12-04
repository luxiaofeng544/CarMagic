package com.lemon.carmonitor.event;

import com.lemon.carmonitor.model.FenceModel;

import java.util.List;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.event]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/1/24 22:03]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/1/24 22:03]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class BaiduFenceQueryEvent {

    private int statue;
    private List<FenceModel> models;
    private String message;

    public int getStatue() {
        return statue;
    }

    public void setStatue(int statue) {
        this.statue = statue;
    }

    public List<FenceModel> getModels() {
        return models;
    }

    public void setModels(List<FenceModel> models) {
        this.models = models;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaiduFenceQueryEvent{" +
                "statue=" + statue +
                ", models=" + models +
                ", message='" + message + '\'' +
                '}';
    }
}
