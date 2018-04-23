package com.lemon.pay;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.lemon.LemonActivityManager;
import com.lemon.annotation.Autowired;
import com.lemon.annotation.Component;
import com.lemon.carmonitor.api.ApiManager;
import com.lemon.carmonitor.contant.StatusCode;
import com.lemon.carmonitor.model.bean.SmsPackageModel;
import com.lemon.carmonitor.model.param.BuySmsPackageParam;
import com.lemon.carmonitor.model.result.BuySmsPackageResult;
import com.lemon.carmonitor.util.AppCacheManager;
import com.lemon.event.ToastEvent;
import com.lemon.util.LogUtils;

import org.greenrobot.eventbus.EventBus;


/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.pay]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/2/3 23:33]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/2/3 23:33]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Component
public class PayEngine {

    @Autowired
    public Context mContext;
    @Autowired
    public LemonActivityManager lemonActivityManager;
    @Autowired
    public AppCacheManager appCacheManager;
    @Autowired
    public ApiManager apiManager;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;

    public PayEngine(){
        EventBus.getDefault().register(this);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    LogUtils.e("支付宝-----------------------------------------\n"+(String) msg.obj);
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        EventBus.getDefault().post(new ToastEvent("支付成功"));
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            EventBus.getDefault().post(new ToastEvent("支付结果确认中"));
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            EventBus.getDefault().post(new ToastEvent("支付失败"));
                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    EventBus.getDefault().post(new ToastEvent("检查结果为：" + msg.obj));
                    break;
                }
                default:
                    break;
            }
        }
        ;
    };

    public void onEventMainThread(BuySmsPackageResult event){
        if(event.getRetCode().equals(StatusCode.SUCCESS.getCode())){
            final String payInfo = event.getRetData().getPayInfo();
            Runnable payRunnable = new Runnable() {

                @Override
                public void run() {
                    // 构造PayTask 对象
                    PayTask alipay = new PayTask(lemonActivityManager.getCurrentActivity());
                    // 调用支付接口，获取支付结果
                    String result = alipay.pay(payInfo, true);

                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
            };

            // 必须异步调用
            Thread payThread = new Thread(payRunnable);
            payThread.start();
        }else {
            EventBus.getDefault().post(new ToastEvent(event.getRetMsg()));
        }
    }

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay(SmsPackageModel currentPackage) {
        BuySmsPackageParam param = new BuySmsPackageParam();
        param.setLoginToken(appCacheManager.getCurrentToken());
        param.setPackageId(currentPackage.getId()+"");
        LogUtils.e("支付宝购买套餐----------------------------------------\n"+currentPackage.toString());
        apiManager.buySmsPackage(param);
    }

}
