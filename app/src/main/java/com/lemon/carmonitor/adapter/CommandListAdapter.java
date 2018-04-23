package com.lemon.carmonitor.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lemon.carmonitor.R;
import com.lemon.carmonitor.event.ClickCommandEvent;
import com.lemon.carmonitor.model.bean.protocol.CmdsEntity;
import com.lemon.util.JSONUtils;
import com.lemon.util.ParamUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.adapter]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/2/1 21:29]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/2/1 21:29]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class CommandListAdapter extends BaseAdapter {

    private List<CmdsEntity> models;
    private Handler handler;
    private Context context;
    private LayoutInflater mInflater;
    private Map<String,TextView> valueMapControllers = new HashMap<>();

    public CommandListAdapter(Handler handler, Context context,
                              List<CmdsEntity> models) {
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.handler = handler;
        this.context = context;
        this.models = models;
    }

    public Map<String, TextView> getValueMapControllers() {
        return valueMapControllers;
    }

    @Override
    public int getCount() {
        return ParamUtils.isEmpty(models) ? 0 : models.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final CmdsEntity model = models.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.command_item, null);
            holder = new ViewHolder();
            holder.tv_command_name = (TextView) convertView
                    .findViewById(R.id.tv_command_name);
            holder.tv_command_value = (TextView) convertView
                    .findViewById(R.id.tv_command_value);
            holder.ll_command = (LinearLayout) convertView
                    .findViewById(R.id.ll_command);

            holder.tv_command_name.setText(model.getName());
            holder.ll_command.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new ClickCommandEvent(model));
                }
            });

            String key = model.getKey();
            if(model.getInput().equals("false")){
                String defaultValue = model.getValue();
                if(!ParamUtils.isEmpty(model.getValue())){
                    String defaultParam1 = JSONUtils.getString(defaultValue,"param1","");
                    if(!ParamUtils.isEmpty(defaultParam1)){
                        key += "-param1:"+defaultParam1;
                    }
                    String defaultParam2 = JSONUtils.getString(defaultValue,"param2","");
                    if(!ParamUtils.isEmpty(defaultParam2)){
                        key += "-param2:"+defaultParam1;
                    }
                    String defaultParam3 = JSONUtils.getString(defaultValue,"param3","");
                    if(!ParamUtils.isEmpty(defaultParam3)){
                        key += "-param3:"+defaultParam1;
                    }
                }
            }
            valueMapControllers.put(key,holder.tv_command_value);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    private class ViewHolder {
        TextView tv_command_name;
        TextView tv_command_value;
        LinearLayout ll_command;
    }
}
