package com.github.anzewei.parallaxbacklayout.annotation;

import com.github.anzewei.parallaxbacklayout.params.Edge;
import com.github.anzewei.parallaxbacklayout.params.EdgeMode;
import com.github.anzewei.parallaxbacklayout.params.Layout;
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
