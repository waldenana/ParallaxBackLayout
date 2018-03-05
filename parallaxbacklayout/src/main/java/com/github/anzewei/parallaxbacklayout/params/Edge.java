package com.github.anzewei.parallaxbacklayout.params;

import com.github.anzewei.parallaxbacklayout.utils.ViewDragHelper;
import com.github.anzewei.parallaxbacklayout.widget.ParallaxBackLayout;

/**
 * slide edge
 */
public enum Edge {

    LEFT(ViewDragHelper.EDGE_LEFT), RIGHT(ViewDragHelper.EDGE_RIGHT), TOP(ViewDragHelper.EDGE_TOP), BOTTOM(ViewDragHelper.EDGE_BOTTOM);
    private final int value;

    private Edge(int value) {
        this.value = value;
    }

    public @ParallaxBackLayout.Edge    int getValue() {
        return value;
    }
}