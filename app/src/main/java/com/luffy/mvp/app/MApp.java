package com.luffy.mvp.app;

import android.app.Application;

/**
 * Created by jiangminglu on 16/7/6.
 */
public class MApp extends Application {

    private static  MApp app = null;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
    public static MApp getInstance(){
        return app;
    }
}
