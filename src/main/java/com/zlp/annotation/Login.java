package com.zlp.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * @author zhoulongpeng
 * @date   2016-02-17
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Login {
	String value() default "token";
	boolean saveSession() default false;
	int expire() default 604800;
}
