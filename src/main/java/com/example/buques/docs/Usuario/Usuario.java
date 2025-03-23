package com.example.buques.docs.Usuario;

import com.example.buques.docs.Empresa.Empresa;
import com.example.buques.docs.Usuario.Enum.EnumRol;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String id;
    private Long cedula;
    private String nombres;
    private String apellidos;
    private int telefono;

    @Email(message = "El correo debe tener un formato válido") // Email verifica que el correo tenga un formato válido
    @NotBlank(message = "El correo no puede estar vacío")
    private String correo;
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

    @NotNull(message = "El rol no puede estar vacío")
    private EnumRol rol;

    @DBRef
    private Empresa empresa;

}