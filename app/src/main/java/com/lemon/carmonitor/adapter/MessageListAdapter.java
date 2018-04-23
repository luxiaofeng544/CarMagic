/**
 *
 */
package com.lemon.carmonitor.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lemon.bean.BeanFactory;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.contant.IAlarmConstant;
import com.lemon.carmonitor.db.Alarm;
import com.lemon.carmonitor.event.VoiceEvent;
import com.lemon.carmonitor.manager.AlarmManager;
import com.lemon.carmonitor.util.AppCacheManager;

import org.greenrobot.eventbus.EventBus;

import java.util.List;



/**
 * @projectName Tracker
 * @PackageName com.lemon.tracker.adapter
 * @Title MessageListAdapter
 * @Description
 * @Author lemon
 * @Date 2015-7-11
 * @Version V1.0
 */
public class MessageListAdapter extends BaseAdapter {

    private List<Alarm> models;
    private Handler handler;
    private Context context;
    private LayoutInflater mInflater;
    private AlarmManager alarmManager;
    private AppCacheManager appCacheManager;

    public MessageListAdapter(Handler handler, Context context,
                              List<Alarm> models) {
        this.context = BeanFactory.getInstance().getBean("mContext");
        this.mInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.handler = handler;
        this.models = models;
        alarmManager = BeanFactory.getInstance().getBean(AlarmManager.class);
        appCacheManager = BeanFactory.getInstance().getBean(AppCacheManager.class);
    }

    @Override
    public int getCount() {
        return models.size();
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
        Alarm model = models.get(position);
//        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.message_list_item, null);
            holder = new ViewHolder();
            holder.list_item_textView_SendTime = (TextView) convertView
                    .findViewById(R.id.list_item_textView_SendTime);
            holder.list_item_textView_Name = (TextView) convertView
                    .findViewById(R.id.list_item_textView_Name);
            holder.list_item_textView_Status = (TextView) convertView
                    .findViewById(R.id.list_item_textView_Status);
            holder.list_item_textView_ReTime = (TextView) convertView
                    .findViewById(R.id.list_item_textView_ReTime);
            holder.iv_statue = (ImageView) convertView
                    .findViewById(R.id.iv_statue);
            holder.rl_message_item =  (RelativeLayout) convertView
                    .findViewById(R.id.rl_message_item);
            holder.rl_message_item.setTag(model);
            holder.rl_message_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Alarm alarm = (Alarm) v.getTag();
                    alarmManager.read(alarm.getDevSn(),alarm.getId());
                    alarm.setStatue("1");
                    holder.iv_statue.setVisibility(View.GONE);
                    if(alarm.getAlarmType().equals(IAlarmConstant.voiceType)){
                        EventBus.getDefault().post(new VoiceEvent("play",alarm));
                    }
                }
            });

            if(model.getStatue().equals("-1")){
                holder.iv_statue.setVisibility(View.VISIBLE);
            }else {
                holder.iv_statue.setVisibility(View.GONE);
            }
            holder.list_item_textView_SendTime.setText(model.getAlarmTime());
            holder.list_item_textView_Name.setText(model.getDevName());
            holder.list_item_textView_Status.setText(model.getAlarmTypeName());
            holder.list_item_textView_ReTime.setText(model.getAlarmType());

            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }

        return convertView;
    }

    private class ViewHolder {
        RelativeLayout rl_message_item;
        TextView list_item_textView_SendTime;
        TextView list_item_textView_Name;
        TextView list_item_textView_Status;
        TextView list_item_textView_ReTime;
        ImageView iv_statue;
    }

}
