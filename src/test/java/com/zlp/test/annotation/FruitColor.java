package com.zlp.test.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FruitColor {

	/**
     * 颜色枚举
     */
    public enum Color{ BULE,RED,GREEN};
    
    /**
     * 颜色属性
     */
    Color fruitColor() default Color.GREEN;
	
}
