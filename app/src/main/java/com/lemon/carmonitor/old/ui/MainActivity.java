package com.lemon.carmonitor.old.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lemon.LemonActivity;
import com.lemon.LemonLocation;
import com.lemon.LemonUpdate;
import com.lemon.bean.BeanFactory;
import com.lemon.carmonitor.R;
import com.lemon.carmonitor.event.ChangeViewEvent;
import com.lemon.carmonitor.fragment.DeviceListFragment;
import com.lemon.carmonitor.fragment.HomeFragment;
import com.lemon.carmonitor.fragment.MessageFragment;
import com.lemon.carmonitor.fragment.TrackingFragment;
import com.lemon.carmonitor.model.param.AppUpdateParam;
import com.lemon.carmonitor.model.param.AppUserLocReportParam;
import com.lemon.carmonitor.model.param.GetUserDevsParam;
import com.lemon.config.Config;
import com.lemon.util.AppUtils;
import com.lemon.util.ParamUtils;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.ui]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2015/12/21 20:34]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2015/12/21 20:34]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class MainActivity extends LemonActivity {
    private Button[] mTabs;
    private int index; // 用户点击Tab的index
    private int currentTabIndex; // 当前fragment的index
    private Fragment[] fragments;
    private LemonLocation lemonLocation;

    @Override
    protected void setLayout() {
        layout = R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mTabs = new Button[4];
        mTabs[0] = findControl(R.id.btn_home);
        mTabs[1] = findControl(R.id.btn_list);
        mTabs[2] = findControl(R.id.btn_tracking);
        mTabs[3] = findControl(R.id.btn_message);

        Fragment fg1 = new HomeFragment();
        Fragment fg2 = new DeviceListFragment();
        Fragment fg3 = new TrackingFragment();
        Fragment fg4 = new MessageFragment();

        fragments = new Fragment[]{fg1, fg2, fg3, fg4};

        // 添加显示第一个fragment
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fg1)
                .add(R.id.fragment_container, fg2)
                .add(R.id.fragment_container, fg3)
                .add(R.id.fragment_container, fg4).hide(fg2).hide(fg3).hide(fg4)
                .show(fg1).commit();


        mTabs[0].setSelected(true);
    }

    @Override
    protected void initData() {
        if(!Config.isUpdate()){
            return;
        }
        BeanFactory.getInstance().getBean(LemonUpdate.class).setContext(mContext);
        /*AppUpdateParam param = new AppUpdateParam();
        apiManager.update(param);*/
        lemonLocation = BeanFactory.getInstance().getBean(LemonLocation.class);
        handler.sendEmptyMessageDelayed(1,3000);

        if(Config.isCheckUpdate()){
            handler.sendEmptyMessageDelayed(100,1000*60*10);
        }
    }

    @Override
    public void notificationMessage(Message msg) {
        if(msg.what == 1){
            uploadLocation();
        }else if(msg.what == 100){
            checkUpdateVersion();
        }
    }

    private void checkUpdateVersion(){
        AppUpdateParam param = new AppUpdateParam();
        param.setShowDialog(false);
        param.setInvokeType("silence");
        apiManager.updateNew(param);
    }

    private boolean isUpload = false;

    private void uploadLocation() {
        if (ParamUtils.isNull(lemonLocation.currentLocation)) {
            handler.sendEmptyMessageDelayed(1,10000);
            return;
        }
        if(isUpload){
            return;
        }
        isUpload = true;
        AppUserLocReportParam param = new AppUserLocReportParam();
        param.setShowDialog(false);
        param.setLoginToken(cacheManager.getCurrentToken());
        param.setAddress(lemonLocation.locationInfo+ "   --   " + AppUtils.getVerName(mContext));
        param.setPhoneNum(cacheManager.getCurrentMobile());
        param.setLatitude(lemonLocation.currentLocation.getLatitude() + "");
        param.setLongitude(lemonLocation.currentLocation.getLongitude()+"");
        apiManager.appUserLocReport(param);
    }

    public void onBottomClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home:
                index = 0;
                break;
            case R.id.btn_list:
                index = 1;
                GetUserDevsParam param = new GetUserDevsParam();
                param.setLoginToken(cacheManager.getCurrentToken());
                apiManager.getUserDevs(param);
                break;
            case R.id.btn_tracking:
                index = 2;
                break;
            case R.id.btn_message:
                index = 3;
                break;
        }

        if (currentTabIndex != index) {
            FragmentTransaction trx = getFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }

    public void onEventMainThread(ChangeViewEvent event){

        index =event.getIndex();
        if (currentTabIndex != index) {
            FragmentTransaction trx = getFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentTabIndex = index;

    }


    private long exitTime;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
                finish();
            }
        }
        return false;
    }
}
