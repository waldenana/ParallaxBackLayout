package com.github.anzewei.parallaxbacklayout;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.github.anzewei.parallaxbacklayout.annotation.ParallaxBack;
import com.github.anzewei.parallaxbacklayout.transform.TransformFactory;
import com.github.anzewei.parallaxbacklayout.utils.LinkedStack;
import com.github.anzewei.parallaxbacklayout.widget.BackgroundViewFactory;
import com.github.anzewei.parallaxbacklayout.widget.ParallaxBackLayout;

/**
 * Created by anzew on 2017-05-09.
 */
public class ParallaxHelper implements Application.ActivityLifecycleCallbacks {

    private static ParallaxHelper sParallaxHelper;
    private LinkedStack<Activity, TraceInfo> mLinkedStack = new LinkedStack<>();
    private BackgroundViewFactory mBackgroundViewFactory;
    private TransformFactory mTransformFactory;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ParallaxHelper getInstance() {
        if (sParallaxHelper == null) {
            sParallaxHelper = new ParallaxHelper();
            sParallaxHelper.config(new Config());
        }
        return sParallaxHelper;
    }

    /**
     * Config.
     *
     * @param config the config
     */
    public void config(Config config) {
        config.confirm();
        mBackgroundViewFactory = config.mBackgroundViewFactory;
        mTransformFactory = config.mTransformFactory;
    }

    private ParallaxHelper() {

    }

    @Override
    public void onActivityCreated(final Activity activity, Bundle savedInstanceState) {
        final TraceInfo traceInfo = new TraceInfo();
        mLinkedStack.put(activity, traceInfo);
        traceInfo.mCurrent = activity;

        ParallaxBack parallaxBack = checkAnnotation(activity.getClass());
        if (mLinkedStack.size() > 0 && parallaxBack != null) {
            ParallaxBackLayout layout = enableParallaxBack(activity);
            layout.setEdgeFlag(parallaxBack.edge().getValue());
            layout.setEdgeMode(parallaxBack.edgeMode().getValue());
            //old version used layout enum
            int trans = parallaxBack.layout() != ParallaxBack.Layout.PARALLAX ? parallaxBack.layout().getValue() : parallaxBack.transform();
            layout.setTransform(sParallaxHelper.mTransformFactory.createTransform(trans));
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
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        mLinkedStack.remove(activity);
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
            backLayout.setBackgroundView(sParallaxHelper.mBackgroundViewFactory.createView(activity));
            return backLayout;
        }
        return null;
    }

    /**
     * Get the activity under param
     *
     * @param activity activity
     * @return the activity  under param
     */
    public Activity underActivity(Activity activity) {
        return mLinkedStack.before(activity);
    }

    /**
     * The type Config.
     */
    public static class Config {
        private BackgroundViewFactory mBackgroundViewFactory;
        private TransformFactory mTransformFactory;

        public Config() {

        }

        public Config transform(TransformFactory factory) {
            mTransformFactory = factory;
            return this;
        }

        public Config background(BackgroundViewFactory factory) {
            mBackgroundViewFactory = factory;
            return this;
        }
        private void confirm(){
            if (mBackgroundViewFactory == null)
                mBackgroundViewFactory = new BackgroundViewFactory.DEFAULT();
            if (mTransformFactory == null)
                mTransformFactory = new TransformFactory.DEFAULT();
        }

    }


    /**
     * The type Trace info.
     */
    public static class TraceInfo {
        private Activity mCurrent;
    }


}
