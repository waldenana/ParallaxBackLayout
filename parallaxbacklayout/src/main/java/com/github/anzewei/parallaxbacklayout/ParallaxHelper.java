package com.github.anzewei.parallaxbacklayout;

import android.app.Activity;
import android.app.Application;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.github.anzewei.parallaxbacklayout.widget.ParallaxBackLayout;

/**
 * Created by anzew on 2017-05-09.
 */
public class ParallaxHelper implements Application.ActivityLifecycleCallbacks {

    private static ParallaxHelper sParallaxHelper;
    private LinkedStack<Activity, TraceInfo> mLinkedStack = new LinkedStack<>();

    /**
     * Gets instance.
     *
     * @return the instance
     */
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

        ParallaxBack parallaxBack = checkAnnotation(activity.getClass());
        if (mLinkedStack.size() > 0 && parallaxBack != null) {
            ParallaxBackLayout layout = enableParallaxBack(activity);
            layout.setEdgeFlag(parallaxBack.edge().getValue());
            layout.setLayoutType(parallaxBack.layout().getValue());
        }
    }

    private ParallaxBack checkAnnotation(Class<? extends Activity> c) {
        Class mc = c;
        ParallaxBack parallaxBack;
        while (Activity.class.isAssignableFrom(mc)) {
            parallaxBack = (ParallaxBack) mc.getAnnotation(ParallaxBack.class);
            if (parallaxBack != null)
                return parallaxBack;
            mc = mc.getSuperclass();
        }
        return null;
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

    /**
     * Disable parallax back.
     *
     * @param activity the activity
     */
    public static void disableParallaxBack(Activity activity) {
        ParallaxBackLayout layout = getParallaxBackLayout(activity);
        if (layout != null)
            layout.setEnableGesture(false);
    }

    /**
     * Enable parallax back.
     *
     * @param activity the activity
     */
    public static ParallaxBackLayout enableParallaxBack(Activity activity) {
        ParallaxBackLayout layout = getParallaxBackLayout(activity, true);
        layout.setEnableGesture(true);
        return layout;
    }

    /**
     * Gets parallax back layout.
     *
     * @param activity the activity
     * @return the parallax back layout
     */
    public static ParallaxBackLayout getParallaxBackLayout(Activity activity) {
        return getParallaxBackLayout(activity, false);
    }

    /**
     * Gets parallax back layout.
     *
     * @param activity the activity
     * @param create   the create
     * @return the parallax back layout
     */
    public static ParallaxBackLayout getParallaxBackLayout(Activity activity, boolean create) {
        View view = ((ViewGroup) activity.getWindow().getDecorView()).getChildAt(0);
        if (view instanceof ParallaxBackLayout)
            return (ParallaxBackLayout) view;
        view = activity.findViewById(R.id.pllayout);
        if (view instanceof ParallaxBackLayout)
            return (ParallaxBackLayout) view;
        if (create) {
            ParallaxBackLayout backLayout = new ParallaxBackLayout(activity);
            backLayout.setId(R.id.pllayout);
            backLayout.attachToActivity(activity);
            backLayout.setBackgroundView(new GoBackView(activity));
            return backLayout;
        }
        return null;
    }

    /**
     * The type Trace info.
     */
    public static class TraceInfo {
        private Activity mCurrent;
    }

    private static class GoBackView implements ParallaxBackLayout.IBackgroundView {

        private Activity mActivity;
        private Activity mActivityBack;

        private GoBackView(Activity activity) {
            mActivity = activity;
        }


        @Override
        public void draw(Canvas canvas) {
            if (mActivityBack != null) {
                mActivityBack.getWindow().getDecorView().requestLayout();
                mActivityBack.getWindow().getDecorView().draw(canvas);
            }
        }

        @Override
        public boolean canGoBack() {
            return (mActivityBack = sParallaxHelper.mLinkedStack.before(mActivity)) != null;
        }
    }
}
