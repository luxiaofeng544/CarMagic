package com.lemon.carmonitor.old.ui;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.lemon.LemonActivity;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.adapter.CommandCategoryAdapter;
import com.lemon.carmonitor.event.ChoiceCommandCategoryEvent;
import com.lemon.carmonitor.model.bean.DeviceInfo;
import com.lemon.carmonitor.model.bean.protocol.CategoriesEntity;
import com.lemon.carmonitor.model.bean.protocol.ModelsEntity;
import com.lemon.carmonitor.model.param.AppUpdateNewParam;
import com.lemon.util.ParamUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.ui]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/2/1 21:56]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/2/1 21:56]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class CommandCategoryActivity extends LemonActivity {
    private String type;
    private ListView listView;

    @Override
    protected void setLayout() {
        layout = R.layout.activity_category_command;
    }

    @Override
    protected void initView() {
        if(!checkDevice()){
            finish();
            return;
        }
        listView = findControl(R.id.lv_command);
    }

    @Override
    protected void initData() {
        if(ParamUtils.isEmpty(cacheManager.getCurrentDevSn())){
            return;
        }
        cacheManager.putBean("command_connection_type","gprs");
        type = getIntentExtraStr("type");
        type = "gprs";
        handler.sendEmptyMessageDelayed(1, 0);
    }

    public void alarmClick(View v) {
        startNextActivity(AlarmsetActivity.class, false);
    }

    public void updateClick(View v) {
        AppUpdateNewParam param = new AppUpdateNewParam();
        param.setInvokeType("manual");
        apiManager.updateNew(param);
    }

    @Override
    public void notificationMessage(Message msg) {
        String proType = ((DeviceInfo)cacheManager.getBean(cacheManager.getCurrentDevSn())).getDevProtocol();
        proType = ParamUtils.isEmpty(proType) ? "GT06" : proType;
        ModelsEntity modelsEntity = cacheManager.getBean(proType, ModelsEntity.class);
        if(ParamUtils.isNull(modelsEntity)){
            toast("不支持协议:"+proType);
            return;
        }
        List<CategoriesEntity> list = new ArrayList<>();
        if (type.equals("sms")) {
            list = modelsEntity.getSms().getCategories();
        } else if (type.equals("gprs")) {
            list = modelsEntity.getGprs().getCategories();
        }
        listView.setAdapter(new CommandCategoryAdapter(handler, mContext, list));
    }

    public void onEventMainThread(ChoiceCommandCategoryEvent event) {
        String uuid = UUID.randomUUID().toString();
        cacheManager.putBean(uuid,event.getModel());
        Intent intent = new Intent(mContext, CommandListActivity.class);
        intent.putExtra("category",uuid );
        startNextActivity(intent, false);
    }
}
