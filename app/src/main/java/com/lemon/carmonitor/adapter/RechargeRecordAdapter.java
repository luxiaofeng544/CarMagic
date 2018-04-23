package com.lemon.carmonitor.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lemon.carmonitor.R;
import com.lemon.carmonitor.model.bean.ChargeModel;
import com.lemon.util.ParamUtils;

import java.util.List;

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
public class RechargeRecordAdapter extends BaseAdapter {

    private List<ChargeModel> models;
    private Handler handler;
    private Context mContext;
    private LayoutInflater mInflater;

    public RechargeRecordAdapter(Context context,
                                 List<ChargeModel> models) {
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        final ChargeModel model = models.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.recharge_record_list_item, null);
            holder = new ViewHolder();
            holder.tv1 = (TextView) convertView.findViewById(R.id.tv1);
            holder.tv_sim = (TextView) convertView.findViewById(R.id.tv_sim);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_payment_type = (TextView) convertView.findViewById(R.id.tv_payment_type);
            holder.tv_recharge_money = (TextView) convertView.findViewById(R.id.tv_recharge_money);
            holder.tv2 = (TextView) convertView.findViewById(R.id.tv2);

            holder.tv1.setText("订单编号:");
            holder.tv_sim.setText(model.getOrderId());
            holder.tv_time.setText(model.getCreateTimeStr());
            holder.tv_payment_type.setText((model.getPayChannel().equals("1")?"支付宝":"微信"));
            holder.tv_recharge_money.setText(model.getMoney()+"");
            holder.tv_sim.setText(model.getOrderId());

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    private class ViewHolder {
        TextView tv1;
        TextView tv_sim;
        TextView tv_time;
        TextView tv_payment_type;
        TextView tv_recharge_money;
        TextView tv2;
    }
}
