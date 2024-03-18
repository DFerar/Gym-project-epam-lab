package com.gym;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class GymCRMApp {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {

        }
    }
}
