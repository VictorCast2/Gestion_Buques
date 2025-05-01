package com.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // agregar luego al application.properties
    private final String directorioFisico = "C:/gestion-buques/perfil-usuario/";
    private final String recusoUrl = "file:/" + directorioFisico;

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        // creamos el directorio 'file:/C:/gestion-buques/perfil-usuario/' si no existe, nos ahorramos crearlo manualmente
        try {
            Path path = Paths.get(directorioFisico); // no se pega con el file:/ para que no cree problemas al crear el directorio
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            // creamos las imágenes por defecto dentro del directorio 'C:/gestion-buques/perfil-usuario/'
            copiarImagen("static/css/assets/image/perfil-usuario.jpg", "perfil-usuario.jpg");
            copiarImagen("static/css/assets/image/user_default.jpg", "user_default.jpg");

        } catch (IOException e) {
            throw new RuntimeException("Error: no se ha podido crear el directorio de imágenes: " + e.getMessage(), e);
        }

        registry.addResourceHandler("/perfil-usuario/**") // recurso externo estático en donde busca las imágenes
                .addResourceLocations(recusoUrl); // localización del directorio, para que sepa donde buscar
    }

    /**
     * Método para copiar una imagen desde 'static/css/assets/image/**' a un archivo externo, en este caso a 'C:/gestion-buques/perfil-usuario/'
     * @param rutaOrigen aquí especificamos la ruta (path) donde se encuentra la imagen a copiar, incluyendo el nombre de la imagen
     * @param nombreImagen este será el nombre que tendrá la imagen en el archivo externo ('/gestion-buques/perfil-usuario/')
     */
    public void copiarImagen(String rutaOrigen, String nombreImagen) {

        try {
            Resource resource = resourceLoader.getResource("classpath:" + rutaOrigen); // usamos el prefijo 'classpath:' porque estamos buscando dentro del proyecto, específicamente en 'resources/*+'

            if (!resource.exists()) {
                throw new IllegalArgumentException("la ruta específicada no existe " + rutaOrigen);
            }

            Path rutaDestino = Paths.get(directorioFisico + nombreImagen);

            if (!Files.exists(rutaDestino)) {
                try (InputStream inputStream = resource.getInputStream(); // InputStream: Se encarga de leer datos
                     OutputStream outputStream = Files.newOutputStream(rutaDestino) // OutputStream: Se encarga de escribir datos (en bytes)
                ) {
                    FileCopyUtils.copy(inputStream, outputStream);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error: ha ocurrido un error al copiar la imagen: " + nombreImagen + " " +  e.getMessage(), e);
        }

    }

}
