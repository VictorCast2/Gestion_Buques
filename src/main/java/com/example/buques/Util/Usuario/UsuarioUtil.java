package com.example.buques.Util.Usuario;

public class UsuarioUtil {

    public void validarTelefono(int telefono) {
        String telefonoStr = String.valueOf(telefono);
        if (telefonoStr.length() != 10) {
            throw new IllegalArgumentException("El teléfono debe tener exactamente 10 dígitos");
        }
    }

}