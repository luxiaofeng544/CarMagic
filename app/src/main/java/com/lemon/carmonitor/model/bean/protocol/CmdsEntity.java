package com.lemon.carmonitor.model.bean.protocol;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

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

@JsonIgnoreProperties(ignoreUnknown = true)
public class CmdsEntity {
    private String name;
    private String key;
    private String template;
    private String input;
    private String show;
    private String value;
    /**
     * key : param1
     * type : spinner
     * hint : 语言设置
     * values : ["中文","英文"]
     * keys : ["-1","1"]
     */

    private List<FieldsEntity> fields;

    public void setName(String name) {
        this.name = name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setFields(List<FieldsEntity> fields) {
        this.fields = fields;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getTemplate() {
        return template;
    }

    public String getInput() {
        return input;
    }

    public List<FieldsEntity> getFields() {
        return fields;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
