package com.github.ingarabr.immutable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE })
public @interface Immutable {
    boolean privateFields() default true;
    boolean finalFields() default true;
    boolean setterMethod() default true;
}
