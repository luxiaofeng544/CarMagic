package com.lemon.carmonitor.old.ui;

import android.os.Message;
import android.view.View;

import com.lemon.carmonitor.R;
import com.lemon.carmonitor.model.param.IssueCmdParam;
import com.lemon.util.JSONUtils;
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
public class DeviceCmdSettingActivity extends CmdActivity {
    @Override
    protected void setLayout() {
        layout = R.layout.activity_device_cmd_setting;
    }

    public void setRunIntervalClick(View v) {
        title = "上传频率";
        type = SET_RUNINTERVAL;
        hint = "时间(0-3600秒)";
        showInputDialog(type, title, hint);
    }

    public void setSosAlarmTypeClick(View v) {
        title = "SOS报警";
        type = SET_SOSALARMTYPE;
        String[] items = new String[]{"关闭", "发短信", "打电话", "先发短信+再打电话"};
        showListDialog(type, title, items);
    }

    public void monitorClick(View v) {
        title = "监听及通话";
        type = MONITOR;
        hint = "手机号码";
        showInputDialog(type, title, hint);
    }

    public void setMoveAlarmClick(View v) {
        title = "开启移位报警";
        type = SET_MOVEALARM;
        hint = "移动距离(米)";
        showInputDialog(type, title, hint);
    }

    public void closeMoveAlarmClick(View v) {
        title = "关闭位移报警";
        type = CLOSE_MOVEALARM;
        showConfirmDialog(type, title, CONFIRM_MESSAGE);
    }

    public void setOverSpeedAlarmClick(View v) {
        title = "开启超速报警";
        type = SET_OVERSPEEDALARM;
        hint = "速度阀值(公里/小时)";
        showInputDialog(type, title, hint);
    }

    public void closeOverSpeedAlarmClick(View v) {
        title = "关闭超速报警";
        type = CLOSE_OVERSPEEDALARM;
        showConfirmDialog(type, title, CONFIRM_MESSAGE);
    }

    @Override
    public void notificationMessage(Message msg) {
        IssueCmdParam param = new IssueCmdParam();
        if (msg.what == CONFIRM_TYPE) {
            if (msg.obj.equals(CLOSE_MOVEALARM)) {
                param.setCmd(CLOSE_MOVEALARM);
                toast("关闭位移报警 确定");
            } else if (msg.obj.equals(CLOSE_OVERSPEEDALARM)) {
                param.setCmd(CLOSE_OVERSPEEDALARM);
                toast("关闭超速报警 确定");
            }
        } else if (msg.what == LIST_TYPE) {
            String value = msg.obj.toString();
            String text = JSONUtils.getString(value, "text", "");
            String type = JSONUtils.getString(value, "type", "");
            param.setCmd(type);
            if(type.equals(SET_SOSALARMTYPE)){
                if (text.equals("发短信")) {
                    value = "1";
                }
                if (text.equals("打电话")) {
                    value = "2";
                }
                if (text.equals("先发短信+再打电话")) {
                    value = "";
                }
                if (text.equals("")) {
                    value = "";
                }

                param.setParam1(value);
            }

            toast(text);
        } else if (msg.what == INPUT_TYPE) {
            String value = msg.obj.toString();
            String text = JSONUtils.getString(value, "text", "");
            String type = JSONUtils.getString(value, "type", "");
            param.setCmd(type);
            param.setParam1(text);
            toast(text);
        }

        if(!ParamUtils.isEmpty(param.getCmd())){
            sendCommand(param);
        }
    }

}
