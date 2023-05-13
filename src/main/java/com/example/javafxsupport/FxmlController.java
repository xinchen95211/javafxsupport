package com.example.javafxsupport;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Component
@Lazy
@Retention(RetentionPolicy.RUNTIME)
public @interface FxmlController {
    String value() default "";
}

