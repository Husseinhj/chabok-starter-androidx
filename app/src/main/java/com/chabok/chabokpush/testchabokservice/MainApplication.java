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

        //RegisterUser
        String userId = chabok.getUserId();
        if ( userId != null){
            chabok.register(userId);
        } else {
            chabok.registerAsGuest();
        }

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
                    "chabok-starter/839879285435",
                    "70df4ae2e1fd03518ce3e3b21ee7ca7943577749",
                    "chabok-starter",
                    "chabok-starter"
            );
            chabok.setDevelopment(true);
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
