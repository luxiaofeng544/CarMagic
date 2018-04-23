package com.lemon.carmonitor.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lemon.LemonActivity;
import com.lemon.bean.BeanFactory;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.model.bean.protocol.CategoriesEntity;
import com.lemon.carmonitor.old.ui.CommandListActivity;
import com.lemon.carmonitor.util.AppCacheManager;
import com.lemon.util.ParamUtils;

import java.util.List;
import java.util.UUID;

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
public class CommandCategoryAdapter extends BaseAdapter {

    private List<CategoriesEntity> models;
    private Handler handler;
    private Context mContext;
    private LayoutInflater mInflater;

    public CommandCategoryAdapter(Handler handler, Context context,
                                  List<CategoriesEntity> models) {
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.handler = handler;
        this.mContext = context;
        this.models = models;
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
        final CategoriesEntity model = models.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.command_item, null);
            holder = new ViewHolder();
            holder.tv_command_name = (TextView) convertView
                    .findViewById(R.id.tv_command_name);
            holder.ll_command = (LinearLayout) convertView
                    .findViewById(R.id.ll_command);

            holder.tv_command_name.setText(model.getName());
            holder.ll_command.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String uuid = UUID.randomUUID().toString();
                    BeanFactory.getInstance().getBean(AppCacheManager.class).putBean(uuid, model);
                    Intent intent = new Intent(mContext, CommandListActivity.class);
                    intent.putExtra("category",uuid );
                    ((LemonActivity)mContext).startNextActivity(intent, false);
//                    EventBus.getDefault().post(new ChoiceCommandCategoryEvent(model));
                }
            });

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    private class ViewHolder {
        TextView tv_command_name;
        LinearLayout ll_command;
    }
}
