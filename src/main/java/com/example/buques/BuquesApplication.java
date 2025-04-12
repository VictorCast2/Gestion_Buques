package com.example.buques;

import com.example.buques.docs.Usuario.Enum.*;
import com.example.buques.docs.Usuario.Usuario;
import com.example.buques.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.List;

@SpringBootApplication
public class BuquesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuquesApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(UsuarioRepository usuarioRepository) {
		return args -> {

			/* Crear Usuarios Jose */
			Usuario userJose = Usuario.builder()
					.tipoIdentificacion(EIdentificacion.CC)
					.numeroIdentificacion(12345)
					.nombres("Jose Andres")
					.apellidos("Torres Diaz")
					.telefono("+57 310 2233445")
					.correo("jose@email.com")
					.password("$2a$10$rj3PmRqB76o2VrobVRdCf.s2Q4S3HDnvVHeAmi8Uxdp.GWrLoqiMq")
					.rol(ERol.ADMIN)
					.empresa(null)
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.build();

			/* Crear Usuarios Carlos */
			Usuario userCarlos = Usuario.builder()
					.tipoIdentificacion(EIdentificacion.TI)
					.numeroIdentificacion(123456)
					.nombres("Carlos Andres")
					.apellidos("Torres Diaz")
					.telefono("+57 310 5566778")
					.correo("carlos@email.com")
					.password("$2a$10$Ek.Cou/rsSJMQk/NQR96jeCE9ImW.pzSaf.OXMptcNDzUOPFQz7EC")
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
