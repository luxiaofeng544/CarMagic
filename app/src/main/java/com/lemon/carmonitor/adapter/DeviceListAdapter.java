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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lemon.bean.BeanFactory;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.event.ChangeDeviceEvent;
import com.lemon.carmonitor.event.ChangeViewEvent;
import com.lemon.carmonitor.event.ReadAlarmEvent;
import com.lemon.carmonitor.event.UnbindDevEvent;
import com.lemon.carmonitor.event.WakeUpEvent;
import com.lemon.carmonitor.model.bean.DeviceInfo;
import com.lemon.carmonitor.util.AppCacheManager;
import com.lemon.util.DateUtils;
import com.lemon.util.ParamUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.List;



/**
 * @projectName Tracker
 * @PackageName com.lemon.tracker.adapter
 * @Title DeviceListAdapter
 * @Description
 * @Author lemon
 * @Date 2015-7-11
 * @Version V1.0
 */
public class DeviceListAdapter extends BaseAdapter {

    private List<DeviceInfo> models;
    private Handler handler;
    private Context context;
    private LayoutInflater mInflater;
    private static final int all_type = 0;
    private static final int online_type = 1;
    private static final int offline_type = 2;
    private static final int alarm_type = 3;
    private static final int unbind_type = 4;
    private int type;


    public DeviceListAdapter(Handler handler, Context context,
                             List<DeviceInfo> models, int type) {
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.handler = handler;
        this.context = context;
        this.models = models;
        this.type = type;
    }

    @Override
    public int getCount() {
        return ParamUtils.isEmpty(models)?0:models.size();
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
        final DeviceInfo model = models.get(position);
//        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.devicelist_item, null);
            holder = new ViewHolder();
            holder.devicelist_item_textView_name = (TextView) convertView
                    .findViewById(R.id.devicelist_item_textView_name);
            holder.devicelist_item_textView_status = (TextView) convertView
                    .findViewById(R.id.devicelist_item_textView_status);
            holder.rl_device_item = (RelativeLayout)convertView.findViewById(R.id.rl_device_item);

            holder.devicelist_item_textView_name.setText(model.getDevName());
            String status = model.getOnline().equals("1")?"在线":"离线";
            status += " " + "电量"+model.getBatteryVolt()+"%";
            status += " " + (model.getEnableFlag().equals("1")?"启用":"停用");
            if(!ParamUtils.isEmpty(model.getValidDate())){
                Date endDate = DateUtils.parseDate(model.getValidDate(),"yyyy-MM-dd");
                if(endDate.before(new Date())){
                    status = "欠费 " +status;
                }
            }
            holder.devicelist_item_textView_status.setText(status);
            if(model.getOnline().equals("1")){
                holder.devicelist_item_textView_status.setTextColor(context.getResources().getColor(R.color.green));
            }

            holder.rl_device_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                            .content("选择了'" + model.getDevName() + "',是否切换设备")
                            .positiveText("切换")
                            .negativeText("取消")
                            .titleColorRes(R.color.black)
                            .contentColorRes(R.color.black)
                            .backgroundColorRes(R.color.white)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                    //save current select device sn
                                    BeanFactory.getInstance().getBean(AppCacheManager.class).setCurrentDevSn(model.getDevSn());
                                    BeanFactory.getInstance().getBean(AppCacheManager.class).setCurrentEntityName(model.getTraceEntityName());

                                    //send event
                                    EventBus.getDefault().post(new ChangeDeviceEvent(model));
                                    EventBus.getDefault().post(new ChangeViewEvent(0));
                                }
                            });
                    if(type==alarm_type){
                        builder.neutralText("清除报警");
                        builder.onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                EventBus.getDefault().post(new ReadAlarmEvent(model.getDevSn()));
                            }
                        });
                    }else if(type==unbind_type){
                        builder.neutralText("解绑");
                        builder.onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                EventBus.getDefault().post(new UnbindDevEvent(model.getDevSn()));
                            }
                        });
                    }else {
                        builder.neutralText("激活");
                        builder.onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                EventBus.getDefault().post(new WakeUpEvent(model.getDevSn()));
                            }
                        });
                    }
                    builder.show();
                }
            });

            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }

        return convertView;
    }

    private class ViewHolder {
        TextView devicelist_item_textView_name;
        TextView devicelist_item_textView_status;
        RelativeLayout rl_device_item;
    }

}
