package com.lemon.carmonitor.widgets;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.lemon.carmonitor.R;
import com.lemon.carmonitor.model.bean.protocol.FieldsEntity;
import com.lemon.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vijay on 24-05-2015.
 */
public class EditTextFactory implements FormWidgetFactory {

    @Override
    public List<View> getViews(Context context, FieldsEntity fieldsEntity) throws Exception {
        List<View> views = new ArrayList<>(1);
        EditText editText = (EditText) LayoutInflater.from(context).inflate(R.layout.item_edit_text, null);
        editText.setHint(fieldsEntity.getHint());
        editText.setId(ViewUtil.generateViewId());
        editText.setTag(R.id.key, fieldsEntity.getKey());
        editText.setTag(R.id.type, fieldsEntity.getType());

        if (!TextUtils.isEmpty(fieldsEntity.getValue())) {
            editText.setText(fieldsEntity.getValue());
        }

        views.add(editText);
        return views;
    }

}
