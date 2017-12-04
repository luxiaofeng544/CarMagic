package com.lemon.carmonitor.event;

import com.lemon.carmonitor.model.bean.protocol.CategoriesEntity;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.event]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/2/1 23:07]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/2/1 23:07]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class ChoiceCommandCategoryEvent {

    private CategoriesEntity model;

    public CategoriesEntity getModel() {
        return model;
    }

    public void setModel(CategoriesEntity model) {
        this.model = model;
    }

    public ChoiceCommandCategoryEvent(CategoriesEntity model) {
        this.model = model;
    }
}
