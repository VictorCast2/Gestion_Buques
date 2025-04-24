package com.app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UsuarioService {

    @Value("${imagenes.upload.usuario}")
    private String uploadBaseDir;

    public String guardarImagenDeUsuario(MultipartFile archivo, String nombreCarpetaUsuario) throws IOException {
        // Ruta base + nombre carpeta (ej: Img/Usuario/nombreUsuario)
        Path carpetaUsuario = Paths.get(uploadBaseDir, nombreCarpetaUsuario);
        if (!Files.exists(carpetaUsuario)) {
            Files.createDirectories(carpetaUsuario);
        }

        String nombreArchivo = System.currentTimeMillis() + "_" + archivo.getOriginalFilename();
        Path rutaFinal = carpetaUsuario.resolve(nombreArchivo);

        Files.write(rutaFinal, archivo.getBytes());

        // Ruta relativa para guardar en la entidad
        return carpetaUsuario.getFileName() + "/" + nombreArchivo;
    }

    public byte[] obtenerImagen(String nombreUsuario, String nombreArchivo) throws IOException {
        Path ruta = Paths.get(uploadBaseDir, nombreUsuario, nombreArchivo);
        return Files.readAllBytes(ruta);
    }

}