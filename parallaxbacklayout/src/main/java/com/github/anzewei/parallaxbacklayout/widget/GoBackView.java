package com.github.anzewei.parallaxbacklayout.widget;

import android.app.Activity;
import android.graphics.Canvas;

import com.github.anzewei.parallaxbacklayout.ParallaxHelper;

/**
 * ParallaxBackLayout
 *
 * @author An Zewei (anzewei88[at]gmail[dot]com)
 * @since ${VERSION}
 */

public class GoBackView implements ParallaxBackLayout.IBackgroundView {

    private Activity mActivity;
    private Activity mActivityBack;

    public GoBackView(Activity activity) {
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
        return (mActivityBack = ParallaxHelper.getInstance().underActivity(mActivity)) != null;
    }
}