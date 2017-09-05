package com.github.anzewei.parallaxbacklayout.widget;

import android.app.Activity;

/**
 * ParallaxBackLayout
 *
 * @author An Zewei (anzewei88[at]gmail[dot]com)
 * @since ${VERSION}
 */


public interface BackgroundViewFactory {
    ParallaxBackLayout.IBackgroundView createView(Activity activity);
    public static class DEFAULT implements BackgroundViewFactory{

        @Override
        public ParallaxBackLayout.IBackgroundView createView(Activity activity) {
            return new GoBackView(activity);
        }
    }
}
