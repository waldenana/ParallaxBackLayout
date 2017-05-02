package com.github.anzewei.parallaxbacklayout;

import android.app.Activity;
import android.view.View;

/**
 * Created by zewei on 2015-11-26.
 */
public class ParallaxBackActivityHelper {

    public Activity getActivity() {
        return mActivity;
    }

    private Activity mActivity;

    private ParallaxBackLayout mParallaxBackLayout;

    public ParallaxBackActivityHelper(Activity activity) {
        mActivity = activity;
        mParallaxBackLayout = new ParallaxBackLayout(mActivity);
    }

    public void onPostCreate() {
        mParallaxBackLayout.attachToActivity(this);
    }

    public View findViewById(int id) {
        if (mParallaxBackLayout != null) {
            return mParallaxBackLayout.findViewById(id);
        }
        return null;
    }

    public void onStartActivity() {
        mParallaxBackLayout.onStartActivity();
    }

    public void scrollToFinishActivity() {
        getBackLayout().scrollToFinishActivity();
    }

    public void setBackEnable(boolean enable) {
        getBackLayout().setEnableGesture(enable);
    }

    public ParallaxBackLayout getBackLayout() {
        return mParallaxBackLayout;
    }
}
