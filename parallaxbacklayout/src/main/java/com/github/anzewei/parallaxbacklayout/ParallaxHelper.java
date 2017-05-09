package com.github.anzewei.parallaxbacklayout;

import android.app.Activity;
import android.app.Application;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;

import java.util.Stack;

/**
 * Created by anzew on 2017-05-09.
 */

public class ParallaxHelper implements Application.ActivityLifecycleCallbacks {

    private static ParallaxHelper sParallaxHelper;
    private Stack<TraceInfo> mActivities = new Stack<>();

    public static ParallaxHelper getInstance() {
        if (sParallaxHelper == null)
            sParallaxHelper = new ParallaxHelper();
        return sParallaxHelper;
    }

    private ParallaxHelper() {

    }

    @Override
    public void onActivityCreated(final Activity activity, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            final TraceInfo traceInfo = new TraceInfo();
            traceInfo.mCurrent = activity;
            if (mActivities.size() > 0 && activity.getClass().getAnnotation(ParallaxBack.class)!= null) {
                traceInfo.mPre = mActivities.peek().mCurrent;
                traceInfo.mBackLayout = new ParallaxBackLayout(activity);
                traceInfo.mBackLayout.attachToActivity(activity);
                traceInfo.mBackLayout.setBackgroundView(new ParallaxBackLayout.IBackgroundView() {

                    @Override
                    public void draw(Canvas canvas) {
                        traceInfo.mPre.getWindow().getDecorView().draw(canvas);
//                        canvas.drawBitmap(traceInfo.mPre.getWindow().getDecorView().getDrawingCache(), 0, 0, mPaint);
                    }
                });
            }
            mActivities.push(traceInfo);
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

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        mActivities.pop();
    }

    public static class TraceInfo {
        private Activity mCurrent;
        private Activity mPre;
        private ParallaxBackLayout mBackLayout;
    }
}
