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
public class DevicePhoneSettingActivity extends CmdActivity {
    @Override
    protected void setLayout() {
        layout = R.layout.activity_device_phone_setting;
    }

    public void setAdminClick(View v) {
        title = "设置主控号码";
        type = SET_ADMIN;
        hint="手机号码";
        showInputDialog(type,title,hint);
    }

    public void clearAdminClick(View v) {
        title = "删除主控号码";
        type = CLEAR_ADMIN;
        showConfirmDialog(type,title,CONFIRM_MESSAGE);
    }

    public void setSosNumClick(View v) {
        title = "设置亲情号码";
        type = SET_SOSNUM;
        hint="手机号码";
        showCustomInputDialog(type, title, hint, hint);
    }

    public void clearSosNumClick(View v) {
        title = "删除情亲号码";
        type = CLEAR_SOSNUM;
        showConfirmDialog(type,title,CONFIRM_MESSAGE);
    }

    @Override
    public void notificationMessage(Message msg) {
        if(msg.what == NOTIFICATION_MSG){
            toast("输入有误,请检查");
            return;
        }

        IssueCmdParam param = new IssueCmdParam();
        if(msg.what == CONFIRM_TYPE) {
            if(msg.obj.equals(CLEAR_ADMIN)){
                param.setCmd(CLEAR_ADMIN);
                toast("删除主控号码 确定");
            }else if(msg.obj.equals(CLEAR_SOSNUM)){
                param.setCmd(CLEAR_SOSNUM);
                toast("删除情亲号码 确定");
            }
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
