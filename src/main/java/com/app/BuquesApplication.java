package com.app;

import com.app.collections.Usuario.Enum.EIdentificacion;
import com.app.collections.Usuario.Enum.ERol;
import com.app.collections.Usuario.Usuario;
import com.app.collections.Usuario.pojo.Empresa;
import com.app.repository.UsuarioRepository;
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

			/* Crear Usuarios */
			Usuario userJose = Usuario.builder()
					.tipoIdentificacion(EIdentificacion.CC)
					.numeroIdentificacion("12345")
					.imagen("perfil-usuario.jpg")
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
					.numeroIdentificacion("123456")
					.imagen("perfil-usuario.jpg")
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

			/* Crear Empresa */
			Empresa empresaLeamitre = Empresa.builder()
					.nit("123456789-3")
					.nombre("Lemaitre Perfumeria")
					.pais("Colombia")
					.ciudad("Cartagena")
					.direccion("ParquiAmérica - Mamonal Gambote")
					.telefono("+57 310 2255889")
					.correo("leamitre@perfumeria.com")
					.build();

			Usuario userTheresa = Usuario.builder()
					.tipoIdentificacion(EIdentificacion.NIT)
					.numeroIdentificacion("123456789 - 1")
					.imagen("perfil-usuario.jpg")
					.nombres("Theresa Andrea")
					.apellidos("Torres Diaz")
					.telefono("+57 310 5566778")
					.correo("theresa@mail.com")
					.password("$2a$10$rj3PmRqB76o2VrobVRdCf.s2Q4S3HDnvVHeAmi8Uxdp.GWrLoqiMq")
					.rol(ERol.AGENTE_NAVIERO)
					.empresa(empresaLeamitre)
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.build();

			if (!usuarioRepository.existsByCorreo(userJose.getCorreo())) {
				usuarioRepository.save(userJose);
			}

			if (!usuarioRepository.existsByCorreo(userCarlos.getCorreo())) {
				usuarioRepository.save(userCarlos);
			}

			if (!usuarioRepository.existsByCorreo(userTheresa.getCorreo())) {
				usuarioRepository.save(userTheresa);
			}

		};
	}
}