package com.lemon.carmonitor.model.bean.protocol;

import java.util.List;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.bean.protocol]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/2/1 22:37]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/2/1 22:37]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class SmsEntity {
    /**
     * name : 通用设置
     * type : normal
     * cmds : [{"name":"语言设置","key":"setLang","template":"setLang*${param1}","input":"true","fields":[{"key":"param1","type":"spinner","hint":"语言设置","values":["中文","英文"],"keys":["-1","1"]}]}]
     */

    private List<CategoriesEntity> categories;

    public void setCategories(List<CategoriesEntity> categories) {
        this.categories = categories;
    }

    public List<CategoriesEntity> getCategories() {
        return categories;
    }
}
