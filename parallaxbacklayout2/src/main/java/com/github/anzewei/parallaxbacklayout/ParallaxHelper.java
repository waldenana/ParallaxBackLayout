package com.github.anzewei.parallaxbacklayout;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by anzew on 2017-05-09.
 */

public class ParallaxHelper implements Application.ActivityLifecycleCallbacks {

    private static ParallaxHelper sParallaxHelper;
    private LinkedStack<Activity, TraceInfo> mLinkedStack = new LinkedStack<>();

    public static ParallaxHelper getInstance() {
        if (sParallaxHelper == null)
            sParallaxHelper = new ParallaxHelper();
        return sParallaxHelper;
    }

    private ParallaxHelper() {

    }

    @Override
    public void onActivityCreated(final Activity activity, Bundle savedInstanceState) {
        Log.d(ParallaxHelper.class.getSimpleName(), activity + "onActivityCreated");
        final TraceInfo traceInfo = new TraceInfo();
        mLinkedStack.put(activity, traceInfo);
        traceInfo.mCurrent = activity;
        if (mLinkedStack.size() > 0 && activity.getClass().getAnnotation(ParallaxBack.class) != null) {
            traceInfo.mBackLayout = new ParallaxBackLayout(activity);
            traceInfo.mBackLayout.setId(R.id.pllayout);
            traceInfo.mBackLayout.attachToActivity(activity);
            traceInfo.mBackLayout.setBackgroundView(new GoBackView(activity));
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {
//        activity.getWindow().getDecorView().buildDrawingCache();
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.d(ParallaxHelper.class.getSimpleName(), activity + "onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        mLinkedStack.remove(activity);
        Log.d(ParallaxHelper.class.getSimpleName(), activity + "onActivityDestroyed");
    }

    public ParallaxBackLayout getParallaxBackLayout(Activity activity){
       View view = ((ViewGroup)activity.getWindow().getDecorView()).getChildAt(0);
        if (view instanceof ParallaxBackLayout)
            return (ParallaxBackLayout) view;
        view = activity.findViewById(R.id.pllayout);
        if (view instanceof ParallaxBackLayout)
            return (ParallaxBackLayout) view;
        return null;
    }

    public static class TraceInfo {
        private Activity mCurrent;
        private ParallaxBackLayout mBackLayout;
    }

    private static class GoBackView implements ParallaxBackLayout.IBackgroundView {

        private Activity mActivity;
        private Activity mActivityBack;

        private GoBackView(Activity activity) {
            mActivity = activity;
        }


        @Override
        public void draw(Canvas canvas) {
            if (mActivityBack != null)
                mActivityBack.getWindow().getDecorView().draw(canvas);
        }

        @Override
        public boolean canGoBack() {
            return (mActivityBack = sParallaxHelper.mLinkedStack.before(mActivity)) != null;
        }
    }
}
