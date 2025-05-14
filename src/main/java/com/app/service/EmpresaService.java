package com.app.service;

import com.app.collections.Usuario.Enum.ERol;
import com.app.collections.Usuario.Usuario;
import com.app.collections.Usuario.pojo.Empresa;
import com.app.dto.request.EmpresaRequest;
import com.app.dto.response.AuthResponse;
import com.app.repository.UsuarioRepository;
import com.app.utils.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;

@Service
public class EmpresaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Método para asignar (crear) una empresa al usuario (este debe tener el rol Invitado)
     * @param empresaRequest parámetro con los datos necesarios para crear la empresa y además contiene la imagen que se le cargara al usuario
     * @param userDetails parámetro para extraer al usuario de la session
     * @return un objeto de tipo authResponse que contiene un mensaje de satisfacción
     */
    public AuthResponse asignarEmpresa(@Valid EmpresaRequest empresaRequest, CustomUserDetails userDetails)  {

        Usuario usuarioEmpresa = usuarioRepository.findByCorreo(userDetails.getCorreo())
                .orElseThrow(() -> new UsernameNotFoundException("el correo " + userDetails.getCorreo() + " no existe."));

        Empresa empresa = Empresa.builder()
                .nit(empresaRequest.nit())
                .nombre(empresaRequest.nombre())
                .pais(empresaRequest.pais())
                .ciudad(empresaRequest.ciudad())
                .direccion(empresaRequest.direccion())
                .telefono(empresaRequest.telefono())
                .correo(empresaRequest.correo())
                .build();

        MultipartFile multipartFile = empresaRequest.imagen();

        String imagen = this.asignarImagen(multipartFile);

        // validamos que la imagen no sea nula
        if (imagen != null) {
            usuarioEmpresa.setImagen(imagen); // esta es la nueva imagen, la que cargo en el input file
        } else {
            usuarioEmpresa.setImagen(empresaRequest.imagenOriginal()); // esta es la imagen que tiene por defecto, en caso le de al botón reiniciar
        }

        usuarioEmpresa.setEmpresa(empresa);
        usuarioEmpresa.setRol(ERol.AGENTE_NAVIERO);

        usuarioRepository.save(usuarioEmpresa);

        return new AuthResponse("Se ha vinculado a la empresa '" + empresa.getNombre() + "' exitosamente");
    }

    /**
     * Método para asignarle una foto al usuario
     * @param imagen interfaz que recibe la imagen que el usuario cargue en el formulario
     * @return el nombre de la foto con un formato en milisegundos más su extensión (.jpg, .png, étc) o un error si el usuario no cuenta con una foto de perfil
     * @nota: La interfaz MultipartFile, es usada para representa un archivo
     * cargado mediante un formulario multipart/form-data,
     * (típico en formularios HTML con <input type="file">).
     */
    public String asignarImagen(MultipartFile imagen) {

        String ruta = "C://gestion-buques//perfil-usuario";

        try {
            if (!imagen.isEmpty()) {
                int index = imagen.getOriginalFilename().indexOf("."); // hola.jpg
                String extension = "." + imagen.getOriginalFilename().substring(index + 1);
                String nombreFoto = System.currentTimeMillis() + extension;

                byte[] bytesImage = imagen.getBytes();
                Path rutaCompleta = Paths.get(ruta + "//" + nombreFoto);
                Files.write(rutaCompleta, bytesImage);

                return nombreFoto;
            } else {
                //throw new RuntimeException("Error: la imagen no puede estar vaciá");
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException("Error: ha ocurrido un error al guardar la imagen " + e.getMessage(), e);
        }
    }

}