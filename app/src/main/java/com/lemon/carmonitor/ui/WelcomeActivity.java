package com.lemon.carmonitor.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.lemon.ApplicationEngine;
import com.lemon.carmonitor.R;

public class WelcomeActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mHandler.sendEmptyMessage(1);
    }

    public void initFramework(){
        ApplicationEngine.start(getApplicationContext());
        mHandler.sendEmptyMessageDelayed(2,2000);
    }

    public void startNextActivity(){
        startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
        finish();
    }

    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    initFramework();
                    break;
                case 2:
                    startNextActivity();
                    break;
            }
        }
    };
}
