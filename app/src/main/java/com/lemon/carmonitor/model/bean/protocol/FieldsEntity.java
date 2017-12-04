package com.lemon.carmonitor.model.bean.protocol;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.bean.protocol]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/2/1 22:33]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/2/1 22:33]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldsEntity {
    private String key;
    private String type;
    private String hint;
    private String value;
    private List<String> values;
    private List<String> keys;

    public void setKey(String key) {
        this.key = key;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public String getHint() {
        return hint;
    }

    public List<String> getValues() {
        return values;
    }

    public List<String> getKeys() {
        return keys;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
