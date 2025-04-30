package com.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // agregar luego al application.properties
    private final String directorioFisico = "C:/gestion-buques/perfil-usuario/";
    private final String recusoUrl = "file:/" + directorioFisico;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        // creamos el directorio 'file:/C:/gestion-buques/perfil-usuario/' si no existe, nos ahorramos crearlo manualmente
        try {
            Path path = Paths.get(directorioFisico); // no se pega con el file:/ para que no cree problemas al crear el directorio
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error: no se ha podido crear el directorio de imágenes: ", e);
        }

        registry.addResourceHandler("/perfil-usuario/**") // recurso externo estático en donde busca las imagenes
                .addResourceLocations(recusoUrl); // localización del directorio, para que sepa donde buscar
    }
}
