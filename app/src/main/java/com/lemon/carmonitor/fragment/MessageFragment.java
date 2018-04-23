package com.lemon.carmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.lemon.LemonDaoManager;
import com.lemon.bean.BeanFactory;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.adapter.MessageListAdapter;
import com.lemon.carmonitor.db.Alarm;
import com.lemon.carmonitor.event.JpushReceiverEvent;
import com.lemon.carmonitor.manager.AlarmManager;
import com.lemon.util.DateComparator;
import com.lemon.util.ParamUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;
import java.util.List;


/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.fragment]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2015/12/23 9:24]
 * 修改人:    [xflu]
 * 修改时间:  [2015/12/23 9:24]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class MessageFragment extends Fragment {

    private LayoutInflater inflater;
    private ListView listView;
    private AlarmManager alarmManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        alarmManager = BeanFactory.getInstance().getBean(AlarmManager.class);
        this.inflater = inflater;
        View rootView = inflater.inflate(R.layout.message, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        handler.sendEmptyMessage(0);
        Button btn_clean = (Button) rootView.findViewById(R.id.btn_clean);
        btn_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmManager.readAll();
                handler.sendEmptyMessage(0);
            }
        });
        return rootView;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            List<Alarm> list = BeanFactory.getInstance().getBean(LemonDaoManager.class).queryAllOrderBy(Alarm.class,"id",false);
            if(ParamUtils.isEmpty(list)){
                return;
            }
            Collections.sort(list, new DateComparator());

            listView.setAdapter(new MessageListAdapter(handler, getActivity(), list));
        }
    };

    public void onEventMainThread(JpushReceiverEvent event){
        handler.sendEmptyMessage(0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
