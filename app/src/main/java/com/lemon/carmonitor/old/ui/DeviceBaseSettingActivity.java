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
public class DeviceBaseSettingActivity extends CmdActivity {
    @Override
    protected void setLayout() {
        layout = R.layout.activity_device_base_setting;
    }

    public void factoryResetClick(View v) {
        title = "恢复出厂设置";
        type = RESTART;
        hint="登陆密码";
        showInputDialog(type, title, hint);
    }

    public void editPasswdClick(View v) {
        title = "修改设备密码";
        type = EDIT_PASSWD;
        String firHint="旧密码";
        String secHint="新密码";
        showCustomInputDialog(type, title, firHint,secHint);
    }

    public void restartClick(View v) {
        title = "重新启动";
        type = RESTART;
        showConfirmDialog(type, title, CONFIRM_MESSAGE);
    }

    public void setLangClick(View v) {
        title = "语言设置";
        type = SET_LANG;
        String[] items = new String[]{"中文","英文"};
        showListDialog(type, title, items);
    }

    public void setTimeZoneClick(View v) {
        title = "时区设置";
        type = SET_TIMEZONE;
        String[] items = new String[25];
        for(int i=-12;i<13;i++){
            items[i+12] = i+"";
        }
        showListDialog(type,title,items);
    }

    @Override
    public void notificationMessage(Message msg) {
        if(msg.what == NOTIFICATION_MSG){
            toast("输入有误,请检查");
            return;
        }

        IssueCmdParam param = new IssueCmdParam();
        if(msg.what == CONFIRM_TYPE) {
            if (msg.obj.equals(RESTART)) {
                param.setCmd(RESTART);
            }
        }else if(msg.what == LIST_TYPE){
            String value = msg.obj.toString();
            String text = JSONUtils.getString(value, "text", "");
            String type = JSONUtils.getString(value, "type", "");
            param.setCmd(type);
            if(type.equals(SET_LANG)){
                value = text.equals("中文")?"2":"1";
                param.setParam1(value);
            }else if(type.equals(SET_TIMEZONE)){
                value = (Integer.valueOf(text)+8) +"";
                param.setParam1(value);
            }

            toast(text);
        }else if(msg.what == INPUT_TYPE){
            String value = msg.obj.toString();
            String text = JSONUtils.getString(value, "text", "");
            String type = JSONUtils.getString(value, "type", "");
            param.setCmd(type);
            param.setParam1(text);
            toast(text);
        }else if(msg.what == CUSTOM_INPUT_TYPE){
            String value = msg.obj.toString();
            String input1 = JSONUtils.getString(value, "input1", "");
            String input2 = JSONUtils.getString(value, "input2", "");
            String type = JSONUtils.getString(value, "type", "");
            param.setCmd(type);
            param.setParam1(input1);
            param.setParam2(input2);
            toast(value);
        }

        if(!ParamUtils.isEmpty(param.getCmd())){
            sendCommand(param);
        }
    }

}
