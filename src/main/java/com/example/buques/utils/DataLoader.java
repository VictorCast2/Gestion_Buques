package com.example.buques.utils;

import com.example.buques.docs.Usuario.Enum.EIdentificacion;
import com.example.buques.docs.Usuario.Enum.ERol;
import com.example.buques.docs.Usuario.Usuario;
import com.example.buques.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader {

    @Bean
    public CommandLineRunner init(UsuarioRepository usuarioRepository) {
        return args -> {

            /* Crear Usuarios */
            Usuario userJose = Usuario.builder()
                    .tipoIdentificacion(EIdentificacion.CC)
                    .numeroIdentificacion(12345)
                    .nombres("Jose Andres")
                    .apellidos("Torres Diaz")
                    .telefono("+57 310 2233445")
                    .correo("jose@mail.com")
                    .password("$2a$10$rj3PmRqB76o2VrobVRdCf.s2Q4S3HDnvVHeAmi8Uxdp.GWrLoqiMq")
                    .rol(ERol.ADMIN)
                    .empresa(null)
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .build();

            Usuario userCarlos = Usuario.builder()
                    .tipoIdentificacion(EIdentificacion.TI)
                    .numeroIdentificacion(123456)
                    .nombres("Carlos Andres")
                    .apellidos("Torres Diaz")
                    .telefono("+57 310 5566778")
                    .correo("carlos@mail.com")
                    .password("$2a$10$rj3PmRqB76o2VrobVRdCf.s2Q4S3HDnvVHeAmi8Uxdp.GWrLoqiMq")
                    .rol(ERol.INSPECTOR)
                    .empresa(null)
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .build();

            usuarioRepository.saveAll(List.of(userJose, userCarlos));
        };
    }
}
