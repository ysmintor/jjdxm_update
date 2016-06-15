package com.dou361.jjdxm_update;

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
