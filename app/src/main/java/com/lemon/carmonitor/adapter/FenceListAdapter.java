/**
 * 
 */
package com.lemon.carmonitor.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.event.FenceDeleteEvent;
import com.lemon.carmonitor.model.FenceModel;
import com.lemon.carmonitor.ui.EditFenceActivity;
import com.lemon.util.ParamUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;



/**
 * @projectName Tracker
 * @PackageName com.lemon.tracker.adapter
 * @Title FenceListAdapter
 * @Description
 * @Author lemon
 * @Date 2015-7-11
 * @Version V1.0
 */
public class FenceListAdapter extends BaseAdapter {

	private List<FenceModel> models;
	private Handler handler;
	private Context context;
	private LayoutInflater mInflater;

	public FenceListAdapter(Handler handler, Context context) {
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.handler = handler;
		this.context = context;
	}


	public FenceListAdapter(Handler handler, Context context, List<FenceModel> models) {
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.handler = handler;
		this.context = context;
		this.models = models;
	}

	public void setModels(List<FenceModel> models){
		this.models = models;
	}

	@Override
	public int getCount() {
		return models.size();
	}

	@Override
	public Object getItem(int position) {
		return models.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		final FenceModel model = models.get(position);
//		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.fence_item, null);
			holder = new ViewHolder();
			holder.fence_edit = (Button) convertView.findViewById(R.id.fence_edit);
			holder.fence_delete = (Button) convertView.findViewById(R.id.fence_delete);
			holder.fence_name = (TextView) convertView.findViewById(R.id.fence_name);
			holder.fence_type = (TextView) convertView.findViewById(R.id.fence_type);
			holder.fence_radius = (TextView) convertView.findViewById(R.id.fence_radius);
			holder.fence_id = (TextView) convertView.findViewById(R.id.fence_id);
			holder.fence_date = (TextView) convertView.findViewById(R.id.fence_date);

			holder.fence_edit.setTag(model);
			holder.fence_edit.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					/*FenceModel model = (FenceModel) v.getTag();
					EventBus.getDefault().post(new FenceEditEvent(model.getFenceId()));*/
					Intent intent = new Intent(context,EditFenceActivity.class);
					intent.putExtra("fenceId", Integer.parseInt(model.getFenceId()));
					intent.putExtra("update",true);
					context.startActivity(intent);
				}
			});

			if(!ParamUtils.isEmpty(model.getValidDays()) && !ParamUtils.isEmpty(model.getValidTimes())) {
				String date = "日期: "+(ParamUtils.isEmpty(model.getValidDays()) ? "" : model.getValidDays()) + "  时间: " + (ParamUtils.isEmpty(model.getValidTimes()) ? "" : model.getValidTimes());
				holder.fence_date.setText(date);
			}else{
				holder.fence_date.setText("");
			}
			holder.fence_name.setText(model.getFenceName());
			holder.fence_type.setText(model.getType());
			holder.fence_radius.setText(model.getFenceRadius());
			holder.fence_id.setText(model.getFenceId());

			System.out.println("getView++++++++++++++++++++++++++++++++++++++");
			System.out.println(model.toString());
			System.out.println("++++++++++++++++++++++++++++++++++++++");

			holder.fence_delete.setTag(model);
			holder.fence_delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					final FenceModel model = (FenceModel) v.getTag();
					new MaterialDialog.Builder(context)
							.content("删除'"+model.getFenceName()+"'")
							.positiveText("确定")
							.negativeText("取消")
							.titleColorRes(R.color.black)
							.contentColorRes(R.color.black)
							.backgroundColorRes(R.color.white)
							.onPositive(new MaterialDialog.SingleButtonCallback() {
								@Override
								public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
									EventBus.getDefault().post(new FenceDeleteEvent(model.getFenceId()));
								}
							}).show();
				}
			});

			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}

		return convertView;
	}

	private class ViewHolder {
		Button fence_edit;
		Button fence_delete;
		TextView fence_id;
		TextView fence_name;
		TextView fence_type;
		TextView fence_date;
		TextView fence_radius;
	}

}
