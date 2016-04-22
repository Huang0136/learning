package com.huang.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * 标记方法注解
 * 
 * @author huanggh
 * @createDate 2016-04-22 16:27
 */
@Target(value = { ElementType.METHOD, ElementType.CONSTRUCTOR })
public @interface Content {

	/**
	 * 被标记方法的内容
	 */
	public String value() default "";
}
