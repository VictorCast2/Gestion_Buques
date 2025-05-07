package com.app.service;

import com.app.collections.Atraque.Atraque;
import com.app.collections.Atraque.Enum.*;
import com.app.collections.Atraque.pojo.*;
import com.app.collections.Usuario.Usuario;
import com.app.dto.request.*;
import com.app.dto.request.DimensionRequest;
import com.app.dto.response.AuthResponse;
import com.app.repository.*;
import com.app.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Data
@Service
public class AtraqueService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AtraqueRepository atraqueRepository;

    public List<Atraque> getAtraquesByUsuario(Usuario agenteNaviero) {
        return atraqueRepository.findByAgenteNaviero(agenteNaviero);
    }

    public Atraque getAtraqueById(String id) {
        return atraqueRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No se ha encontrado una solicitud con el id: " + id));
    }

    /**
     * Método para crear una solicitud de atraque
     * @param atraqueRequest parámetro con los datos de la solicitud
     * @param userDetails parámetro para extraer el usuario de la sesión
     * @return un objeto de tipo authResponse que contiene un mensaje de satisfacción
     * @nota: el atraque tendrá por defecto el estado de la solicitud como Pendiente,
     * lo referente al muelle y al admin que valida la solicitud, se maneja en el método 'nombre método'
     */
    public AuthResponse crearSolicitudAtraque(@Valid AtraqueRequest atraqueRequest, CustomUserDetails userDetails) {

        Usuario agenteNaviero = usuarioRepository.findByCorreo(userDetails.getCorreo())
                .orElseThrow(() -> new UsernameNotFoundException("usuario no encontrado en la solicitud de atraque " + userDetails.getCorreo()));

        Buque buque = asignarBuque(atraqueRequest.buque()); // este método se llama abajo, aquí se crea el buque

        // creamos el objeto 'atraque' con los datos del dto y valores por defecto
        Atraque atraque = Atraque.builder()
                .paisProcedencia(atraqueRequest.paisProcedencia())
                .ciudadProcedencia(atraqueRequest.ciudadProcedencia())
                .puertoProcedencia(atraqueRequest.puertoProcedencia())
                .paisDestino(atraqueRequest.paisDestino())
                .ciudadDestino(atraqueRequest.ciudadDestino())
                .puertoDestino(atraqueRequest.puertoDestino())
                .fechaLlegada(atraqueRequest.fechaLlegada())
                .fechaSalida(atraqueRequest.fechaSalida())
                .estadoSolicitud(EResultado.PENDIENTE)
                .buque(buque)
                .agenteNaviero(agenteNaviero)
                .build();

        atraqueRepository.save(atraque);
        return new AuthResponse("Solicitud creada exitosamente");
    }

    /**
     * Método para crear un buque
     * @param buqueRequest parámetro con los datos necesarios para crear el buque
     * @return un objeto de tipo Buque
     * @nota: Este método se usa en crearSolicitudAtraque, para crear el buque de la solicitud,
     * se opto por crear una clase aparte para no sobrecargar la anterior
     */
    public static Buque asignarBuque(@Valid BuqueRequest buqueRequest) {

        Dimension dimension = asignarDimension(buqueRequest.dimensiones()); // este método se llama abajo, aquí se establecen las dimensiones del buque

        return Buque.builder()
                .matricula(buqueRequest.matricula())
                .nombre(buqueRequest.nombre())
                .tipoBuque(ETipoBuque.valueOf(buqueRequest.tipoBuque()))
                .dimensiones(dimension)
                .build();
    }

    /**
     * Método para crear las dimensiones del buque
     * @param dimensionRequest parámetro con los datos necesarios para establecer las dimensiones del buque
     * @return un objeto de tipo Buque
     * @nota: Este método se usa en asignarBuque, como complemento para definir las dimensiones del buque
     */
    public static Dimension asignarDimension(@Valid DimensionRequest dimensionRequest) {
        return Dimension.builder()
                .peso(dimensionRequest.peso())
                .largo(dimensionRequest.largo())
                .ancho(dimensionRequest.ancho())
                .build();
    }

    public AuthResponse updateSolicitudAtraque(@Valid AtraqueRequest atraqueRequest, String id) {

        Atraque atraqueActualizado = this.getAtraqueById(id);

        // Actualizamos los datos de las dimensiones de buque
        Dimension dimensionActualizada = atraqueActualizado.getBuque().getDimensiones();
        dimensionActualizada.setPeso(atraqueRequest.buque().dimensiones().peso());
        dimensionActualizada.setLargo(atraqueRequest.buque().dimensiones().largo());
        dimensionActualizada.setAncho(atraqueRequest.buque().dimensiones().ancho());
        
        // Actualizamos los datos del buque
        Buque buqueActualizado = atraqueActualizado.getBuque();
        buqueActualizado.setMatricula(atraqueRequest.buque().matricula());
        buqueActualizado.setNombre(atraqueRequest.buque().nombre());
        buqueActualizado.setDimensiones(dimensionActualizada);

        // Actualizamos los datos de la Solicitud de Atraque
        atraqueActualizado.setPaisProcedencia(atraqueRequest.paisProcedencia());
        atraqueActualizado.setCiudadDestino(atraqueRequest.ciudadDestino());
        atraqueActualizado.setPaisProcedencia(atraqueRequest.puertoProcedencia());
        atraqueActualizado.setPaisDestino(atraqueRequest.paisDestino());
        atraqueActualizado.setCiudadDestino(atraqueRequest.ciudadDestino());
        atraqueActualizado.setPuertoDestino(atraqueRequest.puertoDestino());
        atraqueActualizado.setFechaLlegada(atraqueRequest.fechaLlegada());
        atraqueActualizado.setFechaSalida(atraqueRequest.fechaSalida());
        atraqueActualizado.setBuque(buqueActualizado);
        
        atraqueRepository.save(atraqueActualizado);

        return new AuthResponse("La solicitud ha sido actualizada con exitosamente");
    }

    public AuthResponse deleteSolicitudAtraque(String id) {
        atraqueRepository.deleteById(id);
        return new AuthResponse("La solicitud ha sido eliminada exitosamente");
    }
}
