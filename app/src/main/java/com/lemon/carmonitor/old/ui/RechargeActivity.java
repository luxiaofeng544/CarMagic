package com.lemon.carmonitor.old.ui;

import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lemon.LemonActivity;
import com.lemon.bean.BeanFactory;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.contant.StatusCode;
import com.lemon.carmonitor.model.bean.SmsPackageModel;
import com.lemon.carmonitor.model.param.GetAppUserAccountParam;
import com.lemon.carmonitor.model.param.GetSmsPackagesParam;
import com.lemon.carmonitor.model.result.GetAppUserAccountResult;
import com.lemon.carmonitor.model.result.GetSmsPackagesResult;
import com.lemon.pay.PayEngine;
import com.lemon.util.ParamUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.ui]
 * 类描述:    [充值]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2015/12/21 21:04]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2015/12/21 21:04]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class RechargeActivity extends LemonActivity {

    public PayEngine payEngine;
    private TextView tvPhone, tvPackage;
    private SmsPackageModel currentPackage;
    private List<SmsPackageModel> smsPackages;
    private String[] items;
    private LinearLayout ll_sms;
    private TextView tv_sms;

    @Override
    protected void setLayout() {
        setLayoutValue(R.layout.recharge);
    }

    @Override
    protected void initView() {
        tvPhone = findControl(R.id.tvPhone);
        tvPackage = findControl(R.id.tvPackage);
        ll_sms = findControl(R.id.ll_sms);
        tv_sms = findControl(R.id.tv_sms);
    }

    @Override
    protected void initData() {
        payEngine = BeanFactory.getInstance().getBean(PayEngine.class);
        loadSmsPackage();
        loadSmsInfo();
        tvPhone.setText(cacheManager.getCurrentMobile());
    }

    public void payClick(View v) {
        payEngine.pay(currentPackage);
    }

    public void rechargeRecordClick(View v) {
        startNextActivity(RechargeRecordActivity.class, false);
    }

    private void loadSmsPackage() {
        GetSmsPackagesParam param = new GetSmsPackagesParam();
        param.setLoginToken(cacheManager.getCurrentToken());
        apiManager.getSmsPackages(param);
    }

    private void loadSmsInfo() {
        GetAppUserAccountParam param = new GetAppUserAccountParam();
        param.setLoginToken(cacheManager.getCurrentToken());
        apiManager.getAppUserAccount(param);
    }

    public void onEventMainThread(GetSmsPackagesResult result) {
        if (result.getRetCode().equals(StatusCode.SUCCESS.getCode())) {
            smsPackages = result.getRetData().getSmsPackages();
            if (ParamUtils.isEmpty(smsPackages)) {
                toast("加载套餐失败,请稍后尝试");
                return;
            }
            List<String> list = new ArrayList<>();
            String message = "";
            for (SmsPackageModel model : smsPackages) {
                message = model.getPackageName() + " 金额:" + model.getMoney() + " 短信条数:" + model.getSmsAmount();
                list.add(message);
            }
            items = (String[]) list.toArray(new String[smsPackages.size()]);
            handler.sendEmptyMessage(0);
        } else {
            toast(result.getRetMsg());
        }
    }

    public void onEventMainThread(GetAppUserAccountResult result) {
        if (result.getRetCode().equals(StatusCode.SUCCESS.getCode())) {
            ll_sms.setVisibility(View.VISIBLE);
            tv_sms.setText(result.getRetData().getSmsBalance()+"");
        } else {
            toast(result.getRetMsg());
        }
    }

    public void packageClick(View v) {
        if (ParamUtils.isEmpty(smsPackages)) {
            loadSmsPackage();
            toast("正在加载套餐,请稍等");
            return;
        }

        String title = "选择短信套餐";
        showPackageDialog(title, items);
    }

    @Override
    public void notificationMessage(Message msg) {
        switch (msg.what) {
            case 0:
                //first load ,set default
                currentPackage = smsPackages.get(0);
                break;
            case 1:
                break;
        }
        tvPackage.setText(currentPackage.getPackageName());
    }

    protected void showPackageDialog(String title, String[] items) {
        new MaterialDialog.Builder(this)
                .title(title)
                .items(items)
                .titleColorRes(R.color.black)
                .contentColorRes(R.color.black)
                .backgroundColorRes(R.color.white)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        currentPackage = smsPackages.get(which);
                        handler.sendEmptyMessage(1);
                    }
                })
                .show();
    }
}
