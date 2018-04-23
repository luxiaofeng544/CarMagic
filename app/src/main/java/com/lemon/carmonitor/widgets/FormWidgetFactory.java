package com.lemon.carmonitor.widgets;

import android.content.Context;
import android.view.View;

import com.lemon.carmonitor.model.bean.protocol.FieldsEntity;

import java.util.List;

/**
 * Created by vijay on 24-05-2015.
 */
public interface FormWidgetFactory {
    List<View> getViews(Context context, FieldsEntity fieldsEntity) throws Exception;
}
