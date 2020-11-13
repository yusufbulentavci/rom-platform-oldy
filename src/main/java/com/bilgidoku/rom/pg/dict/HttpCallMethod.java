package com.bilgidoku.rom.pg.dict;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpCallMethod {
	public String http() default "post";
	public String contenttype() default "text/html; charset=UTF-8";
	public String roles() default "";
	public long cache() default -1;
	public int cpu() default 1000;
	public String audit() default "_";
	public int file() default 0;
}
