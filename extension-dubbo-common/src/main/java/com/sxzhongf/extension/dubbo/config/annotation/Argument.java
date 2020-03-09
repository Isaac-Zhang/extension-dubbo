package com.sxzhongf.extension.dubbo.config.annotation;

import java.lang.annotation.*;

/**
 * @since 2.6.5
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/9
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
@Inherited
public @interface Argument {
    //argument: index -1 represents not set
    int index() default -1;

    //argument type
    String type() default "";

    //callback interface
    boolean callback() default false;
}
