package com.proyecto;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class imagesConfigurer implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // TODO Auto-generated method stub
        WebMvcConfigurer.super.addResourceHandlers(registry);
        String myExternalFilePath = "file:///C:/Users/mterrone/Documents/Recursos/";
        registry.addResourceHandler("/Recursos/**").addResourceLocations(myExternalFilePath);
        // registry.addResourceHandler("/Recursos/**").addResourceLocations("file:" + "C:\\Users\\mterrone\\Documents\\Recursos");    
        // * Asteriscos para que busque archivos en carpeta 
        // * Recursos y en subcarpetas de dicha carpeta
    }
    
}
