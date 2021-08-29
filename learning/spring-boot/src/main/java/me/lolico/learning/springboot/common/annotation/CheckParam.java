package me.lolico.learning.springboot.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lolico
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckParam {
    /**
     * 需要检查的参数的index ,从0开始
     *
     * @return 需要检查的参数的index
     */
    int[] index() default {};

}
