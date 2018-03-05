package com.github.anzewei.parallaxbacklayout.params;

/**
 * ParallaxBackLayout
 *
 * @author An Zewei (anzewei88[at]gmail[dot]com)
 * @since ${VERSION}
 */

import com.github.anzewei.parallaxbacklayout.widget.ParallaxBackLayout;

/**
 * Slide mode.
 */
public enum EdgeMode {
    FULLSCREEN(ParallaxBackLayout.EDGE_MODE_FULL),
    EDGE(ParallaxBackLayout.EDGE_MODE_DEFAULT);
    private final int value;

    private EdgeMode(int value) {
        this.value = value;
    }

    public @ParallaxBackLayout.EdgeMode
    int getValue() {
        return value;
    }

}
