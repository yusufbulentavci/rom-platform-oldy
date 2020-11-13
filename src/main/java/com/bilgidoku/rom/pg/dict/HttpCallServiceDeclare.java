package com.bilgidoku.rom.pg.dict;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpCallServiceDeclare {
	public String uri();
	public String name();
	public String paket();
	public boolean custom() default false;
	public boolean norom() default false;
	public Net net() default Net.INTRA;
	public String roles() default "";
	public long cache() default -1;
	public int cpu() default 100;
	public boolean direct() default false;
}
