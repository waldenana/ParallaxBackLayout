package com.github.anzewei.parallaxbacklayout.params;

/**
 * ParallaxBackLayout
 *
 * @author An Zewei (anzewei88[at]gmail[dot]com)
 * @since ${VERSION}
 */


import com.github.anzewei.parallaxbacklayout.widget.ParallaxBackLayout;

/**
 * The enum Layout.
 */
public enum Layout {
    PARALLAX(ParallaxBackLayout.LAYOUT_PARALLAX), COVER(ParallaxBackLayout.LAYOUT_COVER), SLIDE(ParallaxBackLayout.LAYOUT_SLIDE);
    private final int value;

    private Layout(int value) {
        this.value = value;
    }

    public
    @ParallaxBackLayout.LayoutType
    int getValue() {
        return value;
    }

}