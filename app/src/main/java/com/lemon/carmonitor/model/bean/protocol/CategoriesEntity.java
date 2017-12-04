package com.lemon.carmonitor.model.bean.protocol;

import java.util.List;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.bean.protocol]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/2/1 22:35]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/2/1 22:35]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class CategoriesEntity {
    private String name;
    private String type;
    /**
     * name : 语言设置
     * key : setLang
     * template : setLang*${param1}
     * input : true
     * fields : [{"key":"param1","type":"spinner","hint":"语言设置","values":["中文","英文"],"keys":["-1","1"]}]
     */

    private List<CmdsEntity> cmds;

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCmds(List<CmdsEntity> cmds) {
        this.cmds = cmds;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public List<CmdsEntity> getCmds() {
        return cmds;
    }
}
