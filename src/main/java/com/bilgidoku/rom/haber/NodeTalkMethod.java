package com.bilgidoku.rom.haber;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NodeTalkMethod {
	public String cmd();
	public byte retrypattern() default 0;
	public byte alarmlevel() default 0;
	public boolean err() default false;
}
