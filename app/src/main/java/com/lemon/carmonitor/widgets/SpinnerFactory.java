package com.lemon.carmonitor.widgets;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.lemon.carmonitor.R;
import com.lemon.carmonitor.model.bean.protocol.FieldsEntity;
import com.lemon.util.ParamUtils;
import com.lemon.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;


public class SpinnerFactory implements FormWidgetFactory {

    @Override
    public List<View> getViews(Context context, FieldsEntity fieldsEntity) throws Exception {
        List<View> views = new ArrayList<>(1);
        Spinner spinner = (Spinner) LayoutInflater.from(context).inflate(R.layout.item_spinner, null);

        String hint = fieldsEntity.getHint();

        spinner.setId(ViewUtil.generateViewId());

        spinner.setTag(R.id.key, fieldsEntity.getKey());
        spinner.setTag(R.id.type, fieldsEntity.getType());
        spinner.setTag(R.id.keys, fieldsEntity.getKeys());
        spinner.setTag(R.id.values, fieldsEntity.getValues());

        String valueToSelect = "";
        int indexToSelect = -1;
        if (!TextUtils.isEmpty(fieldsEntity.getValue())) {
            valueToSelect = fieldsEntity.getValue();
        }

        List<String> values = fieldsEntity.getValues();
        if (!ParamUtils.isEmpty(values)) {
            for (int i = 0; i < values.size(); i++) {
                if (valueToSelect.equals(values.get(i))) {
                    indexToSelect = i;
                }
            }
        }

        if (values != null) {
            spinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, values));
            spinner.setSelection(indexToSelect + 1, true);
        }
        views.add(spinner);
        return views;
    }

}
