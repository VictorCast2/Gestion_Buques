package com.App.Gestion_Buques.Services;

import com.App.Gestion_Buques.Entity.UsuarioEntity;
import com.App.Gestion_Buques.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import lombok.Data;

@Data
@Service
public class UsuarioServices {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Busca un usuario por su email o nombre de usuario.
     * @param identifier Puede ser el email o el nombre de usuario.
     * @return Un Optional que contiene el usuario si se encuentra.
     */
    public Optional<UsuarioEntity> findByEmailOrUsername(String identifier) {
        Optional<UsuarioEntity> user = usuarioRepository.findByEmail(identifier); // Busca por email.
        if (user.isEmpty()) {
            user = usuarioRepository.findByUsername(identifier); // Busca por nombre de usuario.
            if (user.isEmpty()) {
                return Optional.empty(); // No se encontró el usuario.
            }
        }
        return user;
    }

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
    public void modificarUser(UsuarioEntity usuario){
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
    public Optional<UsuarioEntity> EncontrarUsuarioPorID(Long Id) {
        return usuarioRepository.findById(Id);
    }

}