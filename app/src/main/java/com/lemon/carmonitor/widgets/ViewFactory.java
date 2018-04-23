package com.lemon.carmonitor.widgets;

import android.content.Context;
import android.view.View;

import com.lemon.annotation.Autowired;
import com.lemon.annotation.Component;
import com.lemon.carmonitor.model.bean.protocol.FieldsEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.widgets]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/2/2 20:46]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/2/2 20:46]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Component
public class ViewFactory {

    private static final Map<String, FormWidgetFactory> map = new HashMap<>();
    @Autowired
    public Context mContext;

    public ViewFactory() {
        registerWidgets();
    }

    private void registerWidgets() {
        map.put("edit_text", new EditTextFactory());
        map.put("spinner", new SpinnerFactory());
    }

    public List<View> generate(FieldsEntity field) {
        List<View> views = new ArrayList<>(5);
        try {
            views = map.get(field.getType()).getViews(mContext, field);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return views;
    }

}
