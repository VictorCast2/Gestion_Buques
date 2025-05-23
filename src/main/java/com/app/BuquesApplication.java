package com.app;

import com.app.collections.Atraque.Atraque;
import com.app.collections.Atraque.Enum.EResultado;
import com.app.collections.Atraque.Enum.ETipoBuque;
import com.app.collections.Atraque.pojo.Buque;
import com.app.collections.Atraque.pojo.Dimension;
import com.app.collections.Muelle.Enum.EEstado;
import com.app.collections.Muelle.Muelle;
import com.app.collections.Usuario.Enum.EEstadoEmpresa;
import com.app.collections.Usuario.Enum.EIdentificacion;
import com.app.collections.Usuario.Enum.ERol;
import com.app.collections.Usuario.Usuario;
import com.app.collections.Usuario.pojo.Empresa;
import com.app.repository.AtraqueRepository;
import com.app.repository.MuelleRepository;
import com.app.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;
import java.time.LocalDateTime;


@SpringBootApplication
@EnableCaching
public class BuquesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuquesApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(UsuarioRepository usuarioRepository, AtraqueRepository atraqueRepository, MuelleRepository muelleRepository) {
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
					.estadoEmpresa(EEstadoEmpresa.APROBADA)
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

			/* Crear Muelle */
			Muelle muelle = Muelle.builder()
					.nombre("Puerto Bahia")
					.capacidadBuques(10)
					.capacidad(800)
					.estado(EEstado.DISPONIBLE)
					.build();

			/* Crear solicitud de atraque */
			Dimension dimension = Dimension.builder()
					.peso(50)
					.largo(50)
					.ancho(50)
					.build();

			Buque buque = Buque.builder()
					.matricula("TE-AAH-32569")
					.nombre("Buque Portacontenedores")
					.tipoBuque(ETipoBuque.PC)
					.dimensiones(dimension)
					.build();

			Atraque atraque = Atraque.builder()
					.paisProcedencia("Argentina")
					.ciudadProcedencia("Bello")
					.puertoProcedencia("Puerto Argentina")
					.paisDestino("Colombia")
					.ciudadDestino("Cartagena")
					.puertoDestino("Puerto Cartagena")
					.fechaLlegada(LocalDate.now())
					.fechaSalida(LocalDate.now().plusDays(30))
					.fechaAprobacion(LocalDateTime.now())
					.estadoSolicitud(EResultado.APROBADO)
					.buque(buque)
					.muelle(muelle)
					.agenteNaviero(userTheresa)
					.build();

			if (!usuarioRepository.existsByCorreo(userJose.getCorreo())) {
				usuarioRepository.save(userJose);
			}

			if (!usuarioRepository.existsByCorreo(userCarlos.getCorreo())) {
				usuarioRepository.save(userCarlos);
			}

			if (!usuarioRepository.existsByCorreo(userTheresa.getCorreo())) {
				usuarioRepository.save(userTheresa);
				muelleRepository.save(muelle);
				atraqueRepository.save(atraque);
			}

		};
	}
}