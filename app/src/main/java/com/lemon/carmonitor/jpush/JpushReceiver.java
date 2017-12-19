package com.lemon.carmonitor.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.lemon.LemonDaoManager;
import com.lemon.bean.BeanFactory;
import com.lemon.carmonitor.contant.IAlarmConstant;
import com.lemon.carmonitor.db.Alarm;
import com.lemon.carmonitor.db.Voice;
import com.lemon.carmonitor.event.JpushReceiverEvent;
import com.lemon.util.JSONUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

public class JpushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//            processMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
            processMessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            String devSn = JSONUtils.getString(extras, "devSn", "");
            //打开自定义的Activity
            /*Intent i = new Intent(context, DeviceMessageActivity.class);
            i.putExtra("devSn", devSn);
            i.putExtras(bundle);
            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);*/

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            }else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it =  json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " +json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    private void processMessage(Context context, Bundle bundle) {
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Alarm alarm = new Alarm();
        alarm.setDevName(JSONUtils.getString(extras, "devName", ""));
        alarm.setDevSn(JSONUtils.getString(extras, "devSn", ""));
        alarm.setAlais(JSONUtils.getString(extras, "alias", ""));
        alarm.setAlarmTime(JSONUtils.getString(extras, "alarmTime", ""));
        alarm.setAlarmTypeName(JSONUtils.getString(extras, "alarmTypeName", ""));
        alarm.setAlarmType(JSONUtils.getString(extras, "alarmType", ""));
        alarm.setStatue("-1");
        BeanFactory.getInstance().getBean(LemonDaoManager.class).create(Alarm.class, alarm);
        if(alarm.getId() != 0 && alarm.getAlarmType().equals(IAlarmConstant.voiceType)){
            Voice voice = new Voice();
            voice.setAlarmId(alarm.getId());
            voice.setDevName(alarm.getDevName());
            voice.setDevSn(alarm.getDevSn());
            voice.setStatue("-1");
            voice.setVoiceTime(alarm.getAlarmTime());
            voice.setVoiceUrl(JSONUtils.getString(extras, "voiceUrl", ""));
            //BeanFactory.getInstance().getBean(LemonDaoManager.class).create(Voice.class, voice);
        }
        EventBus.getDefault().post(new JpushReceiverEvent());
    }

}
