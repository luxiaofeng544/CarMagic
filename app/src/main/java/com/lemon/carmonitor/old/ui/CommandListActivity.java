package com.lemon.carmonitor.old.ui;

import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.lemon.carmonitor.R;
import com.lemon.carmonitor.adapter.CommandListAdapter;
import com.lemon.carmonitor.contant.StatusCode;
import com.lemon.carmonitor.event.ClickCommandEvent;
import com.lemon.carmonitor.model.bean.CmdLog;
import com.lemon.carmonitor.model.bean.DeviceInfo;
import com.lemon.carmonitor.model.bean.protocol.CategoriesEntity;
import com.lemon.carmonitor.model.bean.protocol.CmdParamEntity;
import com.lemon.carmonitor.model.bean.protocol.CmdsEntity;
import com.lemon.carmonitor.model.bean.protocol.SuppliersEntity;
import com.lemon.carmonitor.model.param.GetDevLastCmdLogParam;
import com.lemon.carmonitor.model.param.IssueCmdParam;
import com.lemon.carmonitor.model.result.GetDevLastCmdLogResult;
import com.lemon.event.ToastEvent;
import com.lemon.util.JSONUtils;
import com.lemon.util.ParamUtils;
import com.lemon.util.PhoneUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
public class CommandListActivity extends CmdActivity {
    private ListView listView;
    private String category;
    CategoriesEntity categoriesEntity;
    private CommandListAdapter adapter;
    private List<CmdsEntity> cmds;
    private Map<String,CmdLog> cacheLogs = new HashMap<>();
    private TextView tvNotifyMessage;

    @Override
    protected void setLayout() {
        layout = R.layout.activity_list_command;
    }

    @Override
    protected void initView() {
        listView = findControl(R.id.lv_command);
        tvNotifyMessage = findControl(R.id.tvNotifyMessage);
    }

    @Override
    protected void initData() {
        category = getIntentExtraStr("category");
        categoriesEntity = (CategoriesEntity) cacheManager.getBean(category);
        cacheManager.removeBean(category);
        handler.sendEmptyMessageDelayed(100, 0);
        workModelMap.put("workModel1", (long) 0);
        workModelMap.put("workModel2", (long) 0);
        workModelMap.put("workModel3", (long) 0);
    }

    protected void initValue(){
        if(ParamUtils.isEmpty(cmds)){
            handler.sendEmptyMessageDelayed(101, 1000);
            return;
        }

        Map<String,CmdsEntity> cmdsEntityMap = new HashMap<>();
        for (CmdsEntity cmd:cmds){
            if(cmdsEntityMap.containsKey(cmd.getKey())){
                continue;
            }
            cmdsEntityMap.put(cmd.getKey(),cmd);
            loadCmdValue(cmd.getKey());
        }
        handler.sendEmptyMessageDelayed(102, 1000);
    }

    private void loadCmdValue(String key){
        GetDevLastCmdLogParam param = new GetDevLastCmdLogParam();
        param.setLoginToken(cacheManager.getCurrentToken());
        param.setDevSn(cacheManager.getCurrentDevSn());
        param.setCmd(key);
        param.setShowDialog(false);
        apiManager.getDevLastCmdLog(param);
    }

    public void onEventMainThread(GetDevLastCmdLogResult result){
        if(result.getRetCode().equals(StatusCode.SUCCESS.getCode())) {
            CmdLog cmdLog = result.getRetData();
            if(ParamUtils.isNull(cmdLog)){
                return;
            }
            cacheLogs.put(cmdLog.getCmd(),cmdLog);
            handleCmdValue(cmdLog);
        }else {
            EventBus.getDefault().post(new ToastEvent(result.getRetMsg()));
        }
    }

    private void handleCmdValue(CmdLog cmdLog){

        String key = cmdLog.getCmd();
        TextView tvValue = null;
        Map<String,TextView> valueMap = adapter.getValueMapControllers();
        if(key.equals("setOverSpeedAlarm")){//超速报警
            tvValue = valueMap.get(key);
            if(!ParamUtils.isNull(tvValue)){
                tvValue.setText(cmdLog.getParam1()+"km/h");
            }
        }else if(key.equals("setVibrationAlarm")){//震动报警
            tvValue = valueMap.get(key);
            if(!ParamUtils.isNull(tvValue)){
                if(cmdLog.getParam1().equals("0")){
                    tvValue.setText("关");
                }else{
                    tvValue.setText(cmdLog.getParam1()+"级");
                }
            }
        }else if(key.equals("setTamper")){//防拆报警
            tvValue = valueMap.get(key);
            if(!ParamUtils.isNull(tvValue)){
                String value = "";
                if(cmdLog.getParam1().equals("0")){
                    value = "关闭";
                }else if(cmdLog.getParam1().equals("1")){
                    value = "开启";
                }
                if(!ParamUtils.isEmpty(value) ){
                    tvValue.setText(value);
                }
            }
        }else if(key.equals("setSoundAlarm")){//声控报警
            tvValue = valueMap.get(key);
            if(!ParamUtils.isNull(tvValue)){
                if(cmdLog.getParam1().equals("00")){
                    tvValue.setText("关闭");
                }else if(cmdLog.getParam1().equals("01")){
                    tvValue.setText("报警");
                }else if(cmdLog.getParam1().equals("02")){
                    tvValue.setText("报警+录音");
                }else{
                    tvValue.setText(cmdLog.getParam1());
                }
            }
        }else if(key.equals("workModel")){//工作模式
            key += "-param1:"+cmdLog.getParam1();
            tvValue = valueMap.get(key);
            if(!ParamUtils.isNull(tvValue)){
                clearWorkModel(valueMap,cmdLog.getCmd());
                tvValue.setText("当前");
            }
        }else if(key.equals("workModel1") || key.equals("workModel2") ||key.equals("workModel3")){//工作模式
            tvValue = valueMap.get(key);
            if(!ParamUtils.isNull(tvValue)){
                resetWorkModel(valueMap,cmdLog);
            }
        }else if(key.equals("timingPowerOn")){//深度休眠:定时开机
            tvValue = valueMap.get(key);
            if(!ParamUtils.isNull(tvValue)){
                tvValue.setText(cmdLog.getParam1()+"小时");
            }
        }else if(key.equals("powerOff")){//深度休眠:关机时间
            tvValue = valueMap.get(key);
            if(!ParamUtils.isNull(tvValue)){
                tvValue.setText(cmdLog.getParam1()+"分钟");
            }
        }else if(key.equals("setAdmin")){//设置中心号码

        }else if(key.equals("clearAdmin")){//删除中心号码

        }else if(key.equals("setRunInterval")){//轨迹精准度
            key += "-param1:"+cmdLog.getParam1();
            tvValue = valueMap.get(key);
            if(!ParamUtils.isNull(tvValue)){
                clearSetRunInterval(valueMap,cmdLog.getCmd());
                tvValue.setText("当前");
            }
        }else if(key.equals("setMonitor")){//监听设置
            tvValue = valueMap.get(key);
            if(!ParamUtils.isNull(tvValue)){
                String value = "";
                if(cmdLog.getParam1().equals("0")){
                    value = "关闭监听";
                }else if(cmdLog.getParam1().equals("1")){
                    value = "开启监听";
                }
                if(!ParamUtils.isEmpty(value) ){
                    tvValue.setText(value);
                }
            }
        }else if(key.equals("setLbs")){//监听设置
            tvValue = valueMap.get(key);
            if(!ParamUtils.isNull(tvValue)){
                String value = "";
                if(cmdLog.getParam1().equals("0")){
                    value = "关闭";
                }else if(cmdLog.getParam1().equals("1")){
                    value = "开启";
                }
                if(!ParamUtils.isEmpty(value) ){
                    tvValue.setText(value);
                }
            }
        }
    }

    private void handleModel(){
        Map<String,TextView> valueMap = adapter.getValueMapControllers();
        if(cacheLogs.containsKey("workModel") && (cacheLogs.containsKey("timingPowerOn")||cacheLogs.containsKey("powerOff"))){
            long timeModel = 0;
            if(cacheLogs.containsKey("timingPowerOn") && cacheLogs.containsKey("powerOff")){
                timeModel = cacheLogs.get("timingPowerOn").getCreateTime()>cacheLogs.get("powerOff").getCreateTime()?cacheLogs.get("timingPowerOn").getCreateTime():cacheLogs.get("powerOff").getCreateTime();
            }else if(cacheLogs.containsKey("timingPowerOn")  && !cacheLogs.containsKey("powerOff")){
                timeModel = cacheLogs.get("timingPowerOn").getCreateTime();
            }else if(!cacheLogs.containsKey("timingPowerOn")  && cacheLogs.containsKey("powerOff")){
                timeModel = cacheLogs.get("powerOff").getCreateTime();
            }

            long workModel =cacheLogs.get("workModel").getCreateTime();
            if(workModel>= timeModel){
                if(!ParamUtils.isNull(valueMap.get("timingPowerOn"))){
                    valueMap.get("timingPowerOn").setText("");
                }
                if(!ParamUtils.isNull(valueMap.get("powerOff"))){
                    valueMap.get("powerOff").setText("");
                }
            }else {
                clearWorkModel(valueMap,"workModel");
            }
        }
    }

    private Map<String,Long> workModelMap = new HashMap<>();

    private void resetWorkModel(Map<String,TextView> valueMap, CmdLog cmdLog){
        workModelMap.put(cmdLog.getCmd(),cmdLog.getCreateTime());
        valueMap.get("workModel1").setText("");
        valueMap.get("workModel2").setText("");
        valueMap.get("workModel3").setText("");
        if(!ParamUtils.isNull(valueMap.get(compareLastWorkModel()))){
            valueMap.get(compareLastWorkModel()).setText("当前");
        }
    }

    private String compareLastWorkModel(){
        long workModel1 = workModelMap.get("workModel1");
        long workModel2 = workModelMap.get("workModel2");
        long workModel3 = workModelMap.get("workModel3");
        if((workModel1>workModel2 && workModel1>workModel3)){
            return "workModel1";
        }

        if((workModel2>workModel1 && workModel2>workModel3)){
            return "workModel2";
        }

        if((workModel3>workModel2 && workModel3>workModel1)){
            return "workModel1";
        }

        return "";
    }

    private void clearWorkModel(Map<String,TextView> valueMap, String key){
        String value = "";
        TextView tvValue;
        value = key+"-param1:0";
        tvValue = valueMap.get(value);
        if(!ParamUtils.isNull(tvValue)){
            tvValue.setText("");
        }
        value = key+"-param1:1";
        tvValue = valueMap.get(value);
        if(!ParamUtils.isNull(tvValue)){
            tvValue.setText("");
        }
        value = key+"-param1:2";
        tvValue = valueMap.get(value);
        if(!ParamUtils.isNull(tvValue)){
            tvValue.setText("");
        }

    }

    private void clearSetRunInterval(Map<String,TextView> valueMap, String key){
        String value = "";
        TextView tvValue;
        value = key+"-param1:10";
        tvValue = valueMap.get(value);
        if(!ParamUtils.isNull(tvValue)){
            tvValue.setText("");
        }
        value = key+"-param1:20";
        tvValue = valueMap.get(value);
        if(!ParamUtils.isNull(tvValue)){
            tvValue.setText("");
        }
        value = key+"-param1:60";
        tvValue = valueMap.get(value);
        if(!ParamUtils.isNull(tvValue)){
            tvValue.setText("");
        }
    }

    private void setNotifyMessage(String msg){
        tvNotifyMessage.setVisibility(View.GONE);
        tvNotifyMessage.setText(msg);
        EventBus.getDefault().post(new ToastEvent(msg));
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.sendEmptyMessageDelayed(101, 0);
    }

    @Override
    public void notificationMessage(Message msg) {
        if (msg.what == 100) {
            if (ParamUtils.isNull(categoriesEntity) || ParamUtils.isEmpty(categoriesEntity.getCmds())) {
                toast("该分类无支持命令");
                return;
            }
            cmds = filterCmds(categoriesEntity.getCmds());
            if(ParamUtils.isEmpty(cmds)){
                setNotifyMessage("此设备不支持"+categoriesEntity.getName());
                return;
            }

            DeviceInfo deviceInfo = (DeviceInfo) cacheManager.getBean(cacheManager.getCurrentDevSn());
            /*if(categoriesEntity.getType().equals("voice_set") && !(deviceInfo.getDevModel().equalsIgnoreCase("gt06-v")||deviceInfo.getDevModel().equalsIgnoreCase("gt06-n"))){
                cmds = new ArrayList<>();
                setNotifyMessage("此设备不支持"+categoriesEntity.getName());
            }*/
            adapter = new CommandListAdapter(handler, mContext, cmds);
            listView.setAdapter(adapter);
            return;
        }else if(msg.what == 101){
            initValue();
            return;
        }else if(msg.what == 102 ){
            handleModel();
            return;
        }

        if (msg.what == NOTIFICATION_MSG) {
            toast("输入有误,请检查");
            return;
        }

        IssueCmdParam param = new IssueCmdParam();
        String template = "";
        if (msg.what == CONFIRM_TYPE) {
            CmdParamEntity paramEntity = (CmdParamEntity) msg.obj;
            CmdsEntity cmdsEntity = paramEntity.getModel();
            String value = paramEntity.getInput();
            String defaultValue = cmdsEntity.getValue();
            if(!ParamUtils.isEmpty(defaultValue)){
                String defaultParam1 = JSONUtils.getString(defaultValue,"param1","");
                if(!ParamUtils.isEmpty(defaultParam1)){
                    param.setParam1(defaultParam1);
                }
                String defaultParam2 = JSONUtils.getString(defaultValue,"param2","");
                if(!ParamUtils.isEmpty(defaultParam2)){
                    param.setParam2(defaultParam2);
                }
                String defaultParam3 = JSONUtils.getString(defaultValue,"param3","");
                if(!ParamUtils.isEmpty(defaultParam3)){
                    param.setParam3(defaultParam3);
                }
            }
            param.setCmd(value);
            template = cmdsEntity.getTemplate();
        } else if (msg.what == LIST_TYPE) {
            CmdParamEntity paramEntity = (CmdParamEntity) msg.obj;
            CmdsEntity cmdsEntity = paramEntity.getModel();
            String value = paramEntity.getInput();
            String text = JSONUtils.getString(value, "text", "");
            String type = JSONUtils.getString(value, "type", "");
            param.setCmd(type);
            int index = cmdsEntity.getFields().get(0).getValues().indexOf(text);
            String param1 = cmdsEntity.getFields().get(0).getKeys().get(index);
            param.setParam1(param1);
            String defaultValue = cmdsEntity.getValue();
            if(!ParamUtils.isEmpty(defaultValue)){
                String defaultParam1 = JSONUtils.getString(defaultValue,"param1","");
                if(!ParamUtils.isEmpty(defaultParam1)){
                    param.setParam1(defaultParam1);
                }
                String defaultParam2 = JSONUtils.getString(defaultValue,"param2","");
                if(!ParamUtils.isEmpty(defaultParam2)){
                    param.setParam2(defaultParam2);
                }
                String defaultParam3 = JSONUtils.getString(defaultValue,"param3","");
                if(!ParamUtils.isEmpty(defaultParam3)){
                    param.setParam3(defaultParam3);
                }
            }

            template = cmdsEntity.getTemplate().replace("${param1}",param1);
        } else if (msg.what == INPUT_TYPE) {
            CmdParamEntity paramEntity = (CmdParamEntity) msg.obj;
            CmdsEntity cmdsEntity = paramEntity.getModel();
            String value = paramEntity.getInput();
            String text = JSONUtils.getString(value, "text", "");
            String type = JSONUtils.getString(value, "type", "");
            param.setCmd(type);
            param.setParam1(text);
            String defaultValue = cmdsEntity.getValue();
            if(!ParamUtils.isEmpty(defaultValue)){
                String defaultParam1 = JSONUtils.getString(defaultValue,"param1","");
                if(!ParamUtils.isEmpty(defaultParam1)){
                    param.setParam1(defaultParam1);
                }
                String defaultParam2 = JSONUtils.getString(defaultValue,"param2","");
                if(!ParamUtils.isEmpty(defaultParam2)){
                    param.setParam2(defaultParam2);
                }
            }

            template = cmdsEntity.getTemplate().replace("${param1}",text);
        }else if(msg.what == CUSTOM_INPUT_TYPE){
            CmdParamEntity paramEntity = (CmdParamEntity) msg.obj;
            CmdsEntity cmdsEntity = paramEntity.getModel();
            String value = paramEntity.getInput();
            String input1 = JSONUtils.getString(value, "input1", "");
            String input2 = JSONUtils.getString(value, "input2", "");
            String type = JSONUtils.getString(value, "type", "");
            param.setCmd(type);


            template = cmdsEntity.getTemplate();
            if(cmdsEntity.getFields().get(0).getKey().equals("param1")){
                param.setParam1(input1);
                template = template.replace("${param1}",input1);
            }else {
                param.setParam2(input1);
                template = template.replace("${param2}",input1);
            }

            if(cmdsEntity.getFields().get(1).getKey().equals("param2")){
                param.setParam2(input2);
                template = template.replace("${param2}",input2);
            }else {
                param.setParam1(input2);
                template = template.replace("${param1}",input2);
            }
        }

        if (!ParamUtils.isEmpty(param.getCmd())){
            if(cacheManager.getBean("command_connection_type").equals("sms")){
                PhoneUtil.sendMessage(mContext,((DeviceInfo)cacheManager.getBean(cacheManager.getCurrentDevSn())).getDevPhoneNum(),template);
            }else {
                param.setLoginToken(cacheManager.getCurrentToken());
                param.setDevSn(cacheManager.getCurrentDevSn());
                sendCommand(param);
                handler.sendEmptyMessageDelayed(101, 2000);
            }
        }

    }

    public List<CmdsEntity> filterCmds(List<CmdsEntity> cmdEntities){
        List<CmdsEntity> cmds = new ArrayList<CmdsEntity>();
        if(ParamUtils.isEmpty(cmdEntities)) {
            return new ArrayList<CmdsEntity>();
        }

        String devSn = cacheManager.getCurrentDevSn();
        if(!cacheManager.containBean(devSn)){
            return cmds;
        }
        DeviceInfo deviceInfo = (DeviceInfo) cacheManager.getBean(devSn);

        if(!cacheManager.containBean(SuppliersEntity.class.getSimpleName()+":"+deviceInfo.getDevProtocol())){
            return cmds;
        }
        SuppliersEntity suppliersEntity = (SuppliersEntity) cacheManager.getBean(SuppliersEntity.class.getSimpleName()+":"+deviceInfo.getDevProtocol());
        if(ParamUtils.isNull(suppliersEntity)){
            return cmds;
        }

        boolean isWcard = suppliersEntity.getIotcard_type().contains(deviceInfo.getDevModel());//物联网卡
        for (CmdsEntity cmd:cmdEntities){
            if(ParamUtils.isEmpty(cmd.getShow())){
                cmds.add(cmd);
            }else{//W 物联网卡  H 用户卡
                if(cmd.getShow().equals("W") && isWcard){
                    cmds.add(cmd);
                }else if(cmd.getShow().equals("H") && !isWcard){
                    cmds.add(cmd);
                }
            }
        }
        return cmds;
    }

    public void onEventMainThread(ClickCommandEvent event) {
        CmdsEntity model = event.getModel();
        if (!model.getInput().equals("true") || ParamUtils.isEmpty(model.getFields())) {
            //comfirm
            showConfirmDialog(model);
        } else if (model.getFields().size() == 1 && model.getFields().get(0).getType().equals("spinner")) {
            //spinner
            showSpinnerDialog(model);
        } else if (model.getFields().size() == 1) {
            // input
            showInputDialog(model);
        } else if (model.getFields().size() == 2) {
            // input 2
            showCustomInputDialog(model);
        } else {
            EventBus.getDefault().post(new ToastEvent("暂时不支持此命令"));
        }
    }

    public void showConfirmDialog(CmdsEntity model) {
        title = model.getName();
        type = model.getKey();
        showConfirmDialog(model, type, title, CONFIRM_MESSAGE);
    }

    public void showSpinnerDialog(CmdsEntity model) {
        title = model.getName();
        type = model.getKey();
        String[] items = model.getFields().get(0).getValues().toArray(new String[model.getFields().get(0).getValues().size()]);
        showListDialog(model, type, title, items);
    }

    public void showInputDialog(CmdsEntity model) {
        title = model.getName();
        type = model.getKey();
        hint = model.getFields().get(0).getHint();
        showInputDialog(model, type, title, hint);
    }

    public void showCustomInputDialog(CmdsEntity model) {
        title = model.getName();
        type = model.getKey();
        String firHint = model.getFields().get(0).getHint();
        String secHint = model.getFields().get(1).getHint();
        showCustomInputDialog(model,type, title, firHint, secHint);
    }
}
