package com.chabok.chabokpush.testchabokservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class SecondService extends Service {
    //Service Containing Local Binder
    private LocalBinder mBinder=new LocalBinder();
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    class LocalBinder extends Binder {

        public LocalBinder() {
        }
    }
}
