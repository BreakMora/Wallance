package com.wallance.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
   
    public void configurePathMatch(PathMatchConfigurer configure){

        // Añade el prefijo "/api/v1" a todo controlador con la notacion @RestController
        configure.addPathPrefix("/api/v1", c -> c.isAnnotationPresent(RestController.class));
    }
}
