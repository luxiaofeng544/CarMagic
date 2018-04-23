package com.lemon;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lemon.annotation.FieldView;
import com.lemon.annotation.Layout;
import com.lemon.annotation.OnClick;
import com.lemon.bean.BeanFactory;
import com.lemon.carmonitor.api.ApiManager;
import com.lemon.carmonitor.model.bean.UserLoginBean;
import com.lemon.carmonitor.util.AppCacheManager;
import com.lemon.config.Config;
import com.lemon.model.BaseResult;
import com.lemon.util.ParamUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/1/21 15:12]
 * 修改人:    [xflu]
 * 修改时间:  [2016/1/21 15:12]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public abstract class LemonFragment extends Fragment {

    protected LayoutInflater inflater;
    protected View rootView;
    protected int layout;

    protected LemonGreenDaoManager daoManager;
    protected AppCacheManager cacheManager;
    protected ApiManager apiManager;
    protected Fragment mFragment;
    protected LemonMessage lemonMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragment = this;
        this.inflater = inflater;
        injectLayout();
        setLayout();
        rootView = inflater.inflate(layout, container, false);
        injectView();
        injectEvent();
        parentInit();
        initView();
        initData();
        init();
        return rootView;
    }

    private void parentInit() {
        EventBus.getDefault().register(this);
        cacheManager = BeanFactory.getInstance().getBean(AppCacheManager.class);
        apiManager = BeanFactory.getInstance().getBean(ApiManager.class);
        daoManager = BeanFactory.getInstance().getBean(LemonGreenDaoManager.class);
        lemonMessage = LemonContext.getBean(LemonMessage.class);
    }

    /**
     * 设置对应的layout
     */
    protected void setLayout(){};


    protected void initView() {
    }

    protected void initData() {
    }

    protected void init() {
    }


    private final void injectLayout() {
        //反射初始化布局
        Layout mLayout = getClass().getAnnotation(Layout.class);
        if (ParamUtils.isNull(mLayout)) {
            return;
        }

        layout = mLayout.id();
    }

    private final void injectView() {
        //反射初始化视图

        Field[] fields = getClass().getDeclaredFields();
        if(ParamUtils.isEmpty(fields)){
            return;
        }
        for (Field field : fields) {
            FieldView view = field.getAnnotation(FieldView.class);
            if(ParamUtils.isNull(view)){
                continue;
            }

            try {
                field.setAccessible(true);
                field.set(this, rootView.findViewById(view.id()));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private final void injectEvent() {
        //反射初始化事件
        Method[] methods = getClass().getDeclaredMethods();
        if(ParamUtils.isEmpty(methods)){
            return;
        }


        for(final Method method:methods){
            OnClick onClick = method.getAnnotation(OnClick.class);
            if(ParamUtils.isNull(onClick)){
                continue;
            }

            View view = rootView.findViewById(onClick.id());
            if(ParamUtils.isNull(view)){
                continue;
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        method.invoke(mFragment);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BaseResult event) {

    }

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            notificationMessage(msg);
        }
    };

    public void notificationMessage(Message msg) {

    }

    protected void toast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    protected boolean isUseYingyanService(){
        if(cacheManager.containBean(UserLoginBean.class)){
            return cacheManager.getBean(UserLoginBean.class).isYingYanService();
        }
        return Config.getBooleanValue("is_use_baidu_yingyan");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        EventBus.getDefault().unregister(this);
    }
}
