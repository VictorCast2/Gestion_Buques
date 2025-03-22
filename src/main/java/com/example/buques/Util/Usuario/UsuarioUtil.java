package com.example.buques.Util.Usuario;

public class UsuarioUtil {

    /**
     * Valida que la telefono tenga exactamente 10 dígitos
     *
     * @param telefono
     */
    public int validarTelefono(int telefono) {
        String telefonoStr = String.valueOf(telefono);
        if (telefonoStr.length() != 10) {
            throw new IllegalArgumentException("El teléfono debe tener exactamente 10 dígitos");
        }
        if (!telefonoStr.matches("3\\d{9}")) {
            throw new IllegalArgumentException("El teléfono debe ser un número colombiano válido: 10 dígitos y comenzar con '3'.");
        }
        return telefono;
    }

}