package com.dou361.jjdxmupdate;

import android.app.Application;

/**
 * @author Administrator
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UpdateConfig.init(this);

    }
}
