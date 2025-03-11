package com.App.Gestion_Buques.AgenteNav.Services;

import com.App.Gestion_Buques.AgenteNav.Entity.AgenteNavEntity;
import lombok.*;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.App.Gestion_Buques.AgenteNav.Repository.AgenteNavRepository;

@Data
@Service
@AllArgsConstructor
public class UsuarioServices {

    @Autowired
    private AgenteNavRepository AgenteNavRepository; // Repositorio de la entidad AgenteNavEntity.

    /**
     * Guarda un nuevo usuario en la base de datos.
     * @param usuario El usuario a guardar.
     * @return El usuario guardado.
     */
    public AgenteNavEntity crearUsuario(AgenteNavEntity usuario) {
        return AgenteNavRepository.save(usuario);
    }

    /**
     * Elimina un usuario de la base de datos
     */
    public void modificarUsuario(AgenteNavEntity usuario){
        if(AgenteNavRepository.existsById(usuario.getId())) {
            AgenteNavRepository.save(usuario);
        }
    }

    /**
     * Elimina un usuario por su ID.
     * @param Id El ID del usuario a eliminar.
     */
    public void eliminarUsuario(Long Id) {
        AgenteNavRepository.deleteById(Id);
    }

    /**
     * Encuentra un usuario por su ID.
     * @param Id El ID del usuario.
     * @return Un Optional que contiene el usuario si se encuentra.
     */
    public Optional<AgenteNavEntity> encontrarUsuarioPorID(Long Id) {
        return AgenteNavRepository.findById(Id);
    }

    /**
     * Encuentra todos los usuarios en la base de datos.
     * @return Una lista con todos los usuarios.
     */
    public List<AgenteNavEntity> encontrarTodosUsuario() {
        return AgenteNavRepository.findAll();
    }

}