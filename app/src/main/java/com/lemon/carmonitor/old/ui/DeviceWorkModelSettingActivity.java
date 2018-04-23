package com.lemon.carmonitor.old.ui;

import android.os.Message;
import android.view.View;

import com.lemon.carmonitor.R;
import com.lemon.carmonitor.model.param.IssueCmdParam;
import com.lemon.util.ParamUtils;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.ui]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2015/12/21 20:39]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2015/12/21 20:39]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class DeviceWorkModelSettingActivity extends CmdActivity {
    @Override
    protected void setLayout() {
        layout = R.layout.activity_work_moel_setting;
    }

    public void setMonitorClick(View v) {
        title = "设置监听模式";
        type = SET_MONITOR;
        showConfirmDialog(type,title,CONFIRM_MESSAGE);
    }

    public void setLbsClick(View v) {
        title = "设置定位模式";
        type = SET_LB;
        showConfirmDialog(type,title,CONFIRM_MESSAGE);
    }

    @Override
    public void notificationMessage(Message msg) {
        IssueCmdParam param = new IssueCmdParam();
        if(msg.obj.equals(SET_LB)){
            param.setCmd(SET_LB);
        }else if(msg.obj.equals(SET_MONITOR)){
            param.setCmd(SET_MOVEALARM);
        }
        if(!ParamUtils.isEmpty(param.getCmd())){
            sendCommand(param);
        }
    }

}
