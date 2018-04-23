package com.lemon.carmonitor.old.ui;

import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lemon.LemonActivity;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.contant.ICmdConstant;
import com.lemon.carmonitor.contant.StatusCode;
import com.lemon.carmonitor.model.bean.protocol.CmdParamEntity;
import com.lemon.carmonitor.model.bean.protocol.CmdsEntity;
import com.lemon.carmonitor.model.param.IssueCmdParam;
import com.lemon.carmonitor.model.result.IssueCmdResult;
import com.lemon.util.ParamUtils;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.ui]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/1/11 15:22]
 * 修改人:    [xflu]
 * 修改时间:  [2016/1/11 15:22]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public abstract class CmdActivity extends LemonActivity implements ICmdConstant {

    protected final static int CONFIRM_TYPE = 1;
    protected final static int LIST_TYPE = 2;
    protected final static int INPUT_TYPE = 3;
    protected final static int CUSTOM_INPUT_TYPE = 4;
    protected final static int NOTIFICATION_MSG = 5;
    protected String type,title,content,hint;

    protected void sendCommand(IssueCmdParam param){
        param.setLoginToken(cacheManager.getCurrentToken());
        param.setDevSn(cacheManager.getCurrentDevSn());
        apiManager.issueCmd(param);
    }

    public void onEventMainThread(IssueCmdResult result){
        if(result.getRetCode().equals(StatusCode.SUCCESS.getCode())){
            toast("设置成功");
        }else {
            toast(result.getRetMsg());
        }
    }


    protected void showConfirmDialog(final CmdsEntity model, final String type, String title, String content) {
        new MaterialDialog.Builder(this)
                .title(title)
                .content(content)
                .positiveText("同意")
                .negativeText("不同意")
                .titleColorRes(R.color.black)
                .contentColorRes(R.color.black)
                .backgroundColorRes(R.color.white)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        Message message = handler.obtainMessage();
                        message.what = CONFIRM_TYPE;
                        CmdParamEntity paramEntity = new CmdParamEntity();
                        paramEntity.setInput(type);
                        paramEntity.setModel(model);
                        message.obj =paramEntity;
                        handler.sendMessage(message);
                    }
                }).show();
    }

    protected void showConfirmDialog(final String type, String title, String content) {
        showConfirmDialog(null, type, title, content);
    }


    protected void showListDialog(final CmdsEntity model, final String type, String title, String[] items) {
        new MaterialDialog.Builder(this)
                .title(title)
                .items(items)
                .negativeText("取消")
                .titleColorRes(R.color.black)
                .contentColorRes(R.color.black)
                .backgroundColorRes(R.color.white)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        Message message = handler.obtainMessage();
                        CmdParamEntity paramEntity = new CmdParamEntity();
                        paramEntity.setInput( "{'type':'" + type + "','which':'" + which + "','text':'" + text + "'}");
                        paramEntity.setModel(model);
                        message.obj =paramEntity;
                        message.what = LIST_TYPE;
                        handler.sendMessage(message);
                    }
                })
                .show();
    }

    protected void showListDialog(final String type, String title, String[] items) {
        showListDialog(null, type, title, items);
    }

    protected void showInputDialog(final CmdsEntity model, final String type, String title, String hint){
        new MaterialDialog.Builder(this)
                .title(title)
                .titleColor(getResources().getColor(R.color.black))
                .contentColorRes(R.color.black)
                .backgroundColorRes(R.color.white)
                .inputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PERSON_NAME |
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .negativeText("取消")
                .positiveText("确定")
                .input(hint, "", false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        Message msg = handler.obtainMessage();
                        msg.what = INPUT_TYPE;
                        CmdParamEntity paramEntity = new CmdParamEntity();
                        paramEntity.setInput("{'type':'" + type + "','text':'" + input + "'}");
                        paramEntity.setModel(model);
                        msg.obj = paramEntity;
                        handler.sendMessage(msg);
                    }
                }).show();
    }

    protected void showInputDialog(final String type, String title, String hint){
        showInputDialog(null, type, title, hint);
    }

    protected void showCustomInputDialog(final CmdsEntity model, final String type, String title, final String firHint, final String secHint){
        final View promptView = getLayoutInflater().inflate(R.layout.input_form, null);
        EditText input1 = (EditText) promptView.findViewById(R.id.edit_dialog_input1);
        EditText input2 = (EditText) promptView.findViewById(R.id.edit_dialog_input2);
        input1.setHint(firHint);
        input2.setHint(secHint);

        new MaterialDialog.Builder(this)
                .title(title)
                .titleColor(getResources().getColor(R.color.black))
                .contentColorRes(R.color.black)
                .backgroundColorRes(R.color.white)
                .inputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PERSON_NAME |
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .customView(promptView, true)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        Message message = handler.obtainMessage();
                        message.what = CUSTOM_INPUT_TYPE;
                        EditText input1 = (EditText) promptView.findViewById(R.id.edit_dialog_input1);
                        EditText input2 = (EditText) promptView.findViewById(R.id.edit_dialog_input2);

                        String strInput1 = input1.getText().toString();
                        String strInput2 = input2.getText().toString();
                        if (ParamUtils.isEmpty(strInput1) || ParamUtils.isEmpty(strInput2)) {
                            handler.sendEmptyMessage(NOTIFICATION_MSG);
                            return;
                        }

                        CmdParamEntity paramEntity = new CmdParamEntity();
                        paramEntity.setInput("{'type':'" + type + "','input1':'" + strInput1 + "','input2':'" + strInput2 + "'}");
                        paramEntity.setModel(model);
                        message.obj = paramEntity;
                        handler.sendMessage(message);
                    }
                })
                .negativeText("取消")
                .positiveText("确定")
                .show();
    }

    protected void showCustomInputDialog(final String type, String title, String firHint, String secHint){
        showCustomInputDialog(null,type,title,firHint,secHint);
    }
}
