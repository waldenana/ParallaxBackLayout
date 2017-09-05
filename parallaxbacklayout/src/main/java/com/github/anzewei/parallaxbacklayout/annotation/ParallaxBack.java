package com.github.anzewei.parallaxbacklayout.annotation;

import com.github.anzewei.parallaxbacklayout.utils.ViewDragHelper;
import com.github.anzewei.parallaxbacklayout.widget.ParallaxBackLayout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by anzew on 2017-05-09.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParallaxBack {

    /**
     * slide edge
     */
    public enum Edge {

        LEFT(ViewDragHelper.EDGE_LEFT), RIGHT(ViewDragHelper.EDGE_RIGHT), TOP(ViewDragHelper.EDGE_TOP), BOTTOM(ViewDragHelper.EDGE_BOTTOM);
        private final int value;

        private Edge(int value) {
            this.value = value;
        }

        public
        @ParallaxBackLayout.Edge
        int getValue() {
            return value;
        }
    }

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

    /**
     * Edge edge.
     *
     * @return the edge
     */
    Edge edge() default Edge.LEFT;

    /**
     * The  slide Transform.
     *
     * @return the layout type ,default parallax
     */
    @Deprecated
    Layout layout() default Layout.PARALLAX;

    int transform() default ParallaxBackLayout.LAYOUT_PARALLAX;
    /**
     * The slide distance
     *
     * @return default edge
     */
    EdgeMode edgeMode() default EdgeMode.EDGE;

}
