package com.lemon.carmonitor.old.ui;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.lemon.LemonActivity;
import com.lemon.LemonDaoManager;
import com.lemon.bean.BeanFactory;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.adapter.MessageListAdapter;
import com.lemon.carmonitor.db.Alarm;
import com.lemon.carmonitor.manager.AlarmManager;
import com.lemon.util.ParamUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.ui]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2015/12/21 20:45]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2015/12/21 20:45]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class DeviceMessageActivity extends LemonActivity {
    private ListView listView;
    private AlarmManager alarmManager;
    private String devSn;

    @Override
    protected void setLayout() {
        setLayoutValue(R.layout.devicemessage);
    }

    @Override
    protected void initView() {
        if(!checkDevice()){
            finish();
            return;
        }
        listView = (ListView) findViewById(R.id.listView);
        handler.sendEmptyMessage(0);
        Button btn_clean = (Button) findViewById(R.id.btn_clean);
        btn_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmManager.readAll(devSn);
                handler.sendEmptyMessage(0);
            }
        });
    }

    @Override
    protected void initData() {
        if(ParamUtils.isEmpty(cacheManager.getCurrentDevSn())){
            return;
        }
        alarmManager = BeanFactory.getInstance().getBean(AlarmManager.class);
        devSn = getIntentExtraStr("devSn");
        if(ParamUtils.isEmpty(devSn)){
            devSn = cacheManager.getCurrentDevSn();
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            List<Alarm> list = new ArrayList<>();
            try {
                list = BeanFactory.getInstance().getBean(LemonDaoManager.class).query(Alarm.class,"devSn",devSn,"id",false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            listView.setAdapter(new MessageListAdapter(handler, mContext, list));
        }
    };
}
