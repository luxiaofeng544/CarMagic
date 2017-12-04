package com.lemon.carmonitor.model.bean.protocol;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.bean.protocol]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/2/2 22:07]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/2/2 22:07]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class CmdParamEntity {
    private String input;
    private CmdsEntity model;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public CmdsEntity getModel() {
        return model;
    }

    public void setModel(CmdsEntity model) {
        this.model = model;
    }
}
