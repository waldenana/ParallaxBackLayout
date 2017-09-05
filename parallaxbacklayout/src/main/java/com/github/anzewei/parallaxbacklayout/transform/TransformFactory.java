package com.github.anzewei.parallaxbacklayout.transform;

import com.github.anzewei.parallaxbacklayout.widget.ParallaxBackLayout;

/**
 * ParallaxBackLayout
 *
 * @author An Zewei (anzewei88[at]gmail[dot]com)
 * @since ${VERSION}
 */

public interface TransformFactory {
    ITransform createTransform(int type);

    public static class DEFAULT implements TransformFactory {

        @Override
        public ITransform createTransform(int type) {
            if (type == ParallaxBackLayout.LAYOUT_PARALLAX)
                return new ParallaxTransform();
            if (type == ParallaxBackLayout.LAYOUT_COVER)
                return new CoverTransform();
            if (type == ParallaxBackLayout.LAYOUT_SLIDE)
                return new SlideTransform();
            return null;
        }
    }
}
