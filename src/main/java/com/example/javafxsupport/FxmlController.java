package com.example.newbox.Annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Component
@Retention(RetentionPolicy.RUNTIME)
public @interface FxmlController {
    String value() default "";
}

