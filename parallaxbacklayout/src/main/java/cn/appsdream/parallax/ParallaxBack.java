package cn.appsdream.parallax;

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
    public enum ViewType {BITMAP, DECORVIEW}

    ViewType viewType() default ViewType.DECORVIEW;


}
