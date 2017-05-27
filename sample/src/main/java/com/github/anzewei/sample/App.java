package com.github.anzewei.sample;

import android.app.Application;

import cn.appsdream.parallax.ParallaxHelper;

/**
 * Created by anzew on 2017-05-09.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(ParallaxHelper.getInstance());
    }
}
