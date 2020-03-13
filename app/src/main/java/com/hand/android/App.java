package com.hand.android;

import android.app.Application;

import com.hand.android.common.network.retrofitlib.RetrofitNetRequest;
import com.hand.android.constants.Constant;

/**
 * Created by admin on 2016/10/27.
 */
public class App extends Application {
    private static App INSTANCE = null;


    public static App getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitNetRequest.getInstance().init(Constant.serverIp);
    }

}
