package com.lemon.carmonitor.old.ui;

import android.widget.ListView;

import com.lemon.LemonActivity;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.adapter.RechargeRecordAdapter;
import com.lemon.carmonitor.contant.StatusCode;
import com.lemon.carmonitor.model.bean.ChargeList;
import com.lemon.carmonitor.model.param.GetBuyPackageRecordParam;
import com.lemon.carmonitor.model.result.GetBuyPackageRecordResult;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.ui]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2015/12/21 21:05]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2015/12/21 21:05]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class RechargeRecordActivity extends LemonActivity {
    private ListView listView;

    @Override
    protected void setLayout() {
        setLayoutValue(R.layout.recharge_record);
    }

    @Override
    protected void initView() {
        listView = findControl(R.id.listView);
    }

    @Override
    protected void initData() {
        GetBuyPackageRecordParam param = new GetBuyPackageRecordParam();
        param.setLoginToken(cacheManager.getCurrentToken());
        param.setLimit(1000 + "");
        apiManager.getBuyPackageRecord(param);
    }

    public void onEventMainThread(GetBuyPackageRecordResult event) {
        if (event.getRetCode().equals(StatusCode.SUCCESS.getCode())) {
            ChargeList list = event.getRetData();
            listView.setAdapter(new RechargeRecordAdapter(mContext, list.getChargeList()));
        }
    }


}
