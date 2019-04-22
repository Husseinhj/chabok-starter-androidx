package com.chabok.chabokpush.testchabokservice;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.adpdigital.push.AdpPushClient;

public class MainApplication extends Application {
    private AdpPushClient chabok = null;

    private SecondService.LocalBinder mBinder;
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mBinder = (SecondService.LocalBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        initPushClient();

        //starting second service
        Intent launch = new Intent(this,SecondService.class);
        startService(launch);

        bindService(launch, mConnection, BIND_AUTO_CREATE);
    }

    private synchronized void initPushClient() {
        if (chabok == null) {
            chabok = AdpPushClient.init(
                    getApplicationContext(),
                    MainActivity.class,
                    "adp-nms-push/845225163503",
                    "e2100f0d7e071c7450f04e530bda746da2fc493b",
                    "adp",
                    "test"
            );
            chabok.setDevelopment(true);
            chabok.register("HUSSEIN_TEST_SERIVCE");
        }
    }

    public synchronized AdpPushClient getPushClient() throws IllegalStateException {
        if (chabok == null) {
            throw new IllegalStateException("Adp Push Client not initialized");
        }
        return chabok;
    }

    @Override
    public void onTerminate() {
        if (chabok != null)
            chabok.dismiss();

        super.onTerminate();
    }
}
