package com.github.anzewei.parallaxbacklayout;

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
        public int getValue() {
            return value;
        }
    }

    /**
     * The enum Layout.
     */
    public enum Layout {
        PARALLAX(ParallaxBackLayout.LAYOUT_PARALLAX), FULL(ParallaxBackLayout.LAYOUT_COVER);
        private final int value;

        private Layout(int value) {
            this.value = value;
        }
        public int getValue() {
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
     * Back layout type.
     *
     * @return the layout type ,default parallax
     */
    Layout layout() default Layout.PARALLAX;

}
