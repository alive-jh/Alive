package com.wechat.jfinal.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface EmptyParaValidate {

	String[] params();

	String errorRedirect() default "";

}
