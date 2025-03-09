package com.App.Gestion_Buques.Usuario.Services;

import lombok.*;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.App.Gestion_Buques.Usuario.Entity.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.App.Gestion_Buques.Usuario.Repository.UsuarioRepository;

@Data
@Service
@AllArgsConstructor
public class UsuarioServices {

    @Autowired
    private UsuarioRepository usuarioRepository; // Repositorio de la entidad UsuarioEntity.

    /**
     * Guarda un nuevo usuario en la base de datos.
     * @param usuario El usuario a guardar.
     * @return El usuario guardado.
     */
    public UsuarioEntity crearUsuario(UsuarioEntity usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * Elimina un usuario de la base de datos
     */
    public void modificarUsuario(UsuarioEntity usuario){
        if(usuarioRepository.existsById(usuario.getId())) {
            usuarioRepository.save(usuario);
        }
    }

    /**
     * Elimina un usuario por su ID.
     * @param Id El ID del usuario a eliminar.
     */
    public void eliminarUsuario(Long Id) {
        usuarioRepository.deleteById(Id);
    }

    /**
     * Encuentra un usuario por su ID.
     * @param Id El ID del usuario.
     * @return Un Optional que contiene el usuario si se encuentra.
     */
    public Optional<UsuarioEntity> encontrarUsuarioPorID(Long Id) {
        return usuarioRepository.findById(Id);
    }

    /**
     * Encuentra todos los usuarios en la base de datos.
     * @return Una lista con todos los usuarios.
     */
    public List<UsuarioEntity> encontrarTodosUsuario() {
        return usuarioRepository.findAll();
    }

}