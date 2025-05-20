package com.app.service;

import com.app.collections.Atraque.Atraque;
import com.app.collections.Atraque.Enum.*;
import com.app.collections.Atraque.pojo.*;
import com.app.collections.Muelle.Enum.EEstado;
import com.app.collections.Muelle.Muelle;
import com.app.collections.Usuario.Usuario;
import com.app.dto.request.*;
import com.app.dto.request.DimensionRequest;
import com.app.dto.response.AuthResponse;
import com.app.repository.*;
import com.app.utils.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AtraqueService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AtraqueRepository atraqueRepository;

    @Autowired
    private MuelleRepository muelleRepository;

    /**
     * Método para obtener a un usuario por su correo
     * @param correo parámetro para buscar al usuario en la base de datos y verificar si existe
     * @return al usuario de la base de datos o un UsernameNotFoundException si este no fue encontrado
     */
    public Usuario getUsuarioByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Error: el usuario no ha sido encontrado en el atraque " + correo));
    }

    /**
     * Método para obtener los atraques de la base de datos con estado 'APROBADO'
     * @return una lista de atraques con el estado 'APROBADO'
     */
    public List<Atraque> getAtraquesByEstadoAprobado() {
        return atraqueRepository.findByEstadoSolicitud(EResultado.APROBADO);
    }

    /**
     * Método para obtener los atraques de la base de datos con estado 'PENDIENTE'
     * @return una lista de atraques con el estado 'PENDIENTE'
     */
    public List<Atraque> getAtraquesByEstadoPendiente() {
        return atraqueRepository.findByEstadoSolicitud(EResultado.PENDIENTE);
    }

    /**
     * Método para buscar un atraque en la base de datos por su id
     * @param id del atraque a buscar
     * @return el atraque si existe o un NoSuchElementException si no existe
     */
    public Atraque getAtraqueById(String id) {
        return atraqueRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No se ha encontrado una solicitud con el id: " + id));
    }

    /**
     * Método para obtener los atraques de un agenteNaviero
     * @param agenteNaviero parámetro para obtener los atraques del usuario
     * @return una lista con los atraques del usuario
     */
    public List<Atraque> getAtraquesByUsuario(Usuario agenteNaviero) {
        return atraqueRepository.findByAgenteNaviero(agenteNaviero);
    }

    /**
     * Método para obtener un AtraqueRequest por su id
     * @param id parámetro para obtener un atraque específico de la base de datos
     * @return un objeto de tipo AtraqueRequest con los datos del atraque
     * @nota: no se usa directamente el objeto Atraque por motivos de seguridad y asi no exponer al modelo de datos
     */
    public AtraqueRequest getAtraqueRequestById(String id) {

        Atraque atraque = this.getAtraqueById(id);

        DimensionRequest dimensionRequest = new DimensionRequest(
                atraque.getBuque().getDimensiones().getPeso(),
                atraque.getBuque().getDimensiones().getLargo(),
                atraque.getBuque().getDimensiones().getAncho()
        );

        BuqueRequest buqueRequest = new BuqueRequest(atraque.getBuque().getMatricula(),
                atraque.getBuque().getNombre(),
                atraque.getBuque().getTipoBuque().toString(),
                dimensionRequest);

        return new AtraqueRequest(atraque.getPaisProcedencia(),
                atraque.getCiudadProcedencia(),
                atraque.getPuertoProcedencia(),
                atraque.getPaisDestino(),
                atraque.getCiudadDestino(),
                atraque.getPuertoDestino(),
                atraque.getFechaLlegada(),
                atraque.getFechaSalida(),
                buqueRequest);
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

        Usuario agenteNaviero = this.getUsuarioByCorreo(userDetails.getCorreo());

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

        // Actualizamos los datos de las dimensiones del buque
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

    /**
     * Método para validar una solicitud de atraque
     * @param validarSolicitudAtraque dto con los parámetros necesarios para validar una solicitud de atraque
     * @param userDetails parámetro para extraer al usuario de la sesión
     * @param estado true si fue aprobada o false si fue rechazada
     * @return un objeto de tipo AuthResponse con un mensaje según los siguientes criterios:
     * estado del muelle, capacidad de buques que soporta el muelle, peso del buque en comparación con el muelle
     * y un mensaje genérico donde se aprueba o rechaza la solicitud según su estado (tue o false)
     */
    public AuthResponse validarSolicitudAtraque(@Valid ValidarSolicitudAtraque validarSolicitudAtraque, CustomUserDetails userDetails, String atraqueId, boolean estado) {

        Usuario admin = this.getUsuarioByCorreo(userDetails.getCorreo());

        Atraque atraque = this.getAtraqueById(atraqueId);

        if (estado) {

            Muelle muelle = muelleRepository.findById(validarSolicitudAtraque.muelleId())
                    .orElseThrow(() -> new NoSuchElementException("Muelle no encontrado en la Solicitud de Atraque"));

            if (!muelle.getEstado().equals(EEstado.DISPONIBLE)) {
                return new AuthResponse("El muelle '" + muelle.getNombre() + "' no está disponible actualmente.");
            }

            int atraquesAprobados = atraqueRepository.countByMuelleIdAndEstadoSolicitud(muelle.getId(), EResultado.APROBADO);
            if (atraquesAprobados >= muelle.getCapacidadBuques()) {
                muelle.setEstado(EEstado.EN_USO);
                muelleRepository.save(muelle);
                return new AuthResponse("El muelle '" + muelle.getNombre() + "' ya está lleno y no puede recibir más buques.");
            }

            double pesoBuque = atraque.getBuque().getDimensiones().getPeso();
            if (pesoBuque > muelle.getCapacidad()) {
                return new AuthResponse("El buque '" + atraque.getBuque().getMatricula() + "' excede la capacidad de carga del muelle '" + muelle.getNombre() + "'.");
            }

            atraque.setEstadoSolicitud(EResultado.APROBADO);
            atraque.setFechaAprobacion(LocalDateTime.now());
            atraque.setDescripcionSolicitud(validarSolicitudAtraque.descripcionSolicitud());
            atraque.setMuelle(muelle);
        } else {
            atraque.setEstadoSolicitud(EResultado.RECHAZADO);
            atraque.setDescripcionSolicitud("La solicitud ha sido rechazada por razones administrativas. Para más información, comuníquese con el administrador.");
        }

        atraque.setAdmin(admin);
        atraqueRepository.save(atraque);
        return new AuthResponse("La solicitud de atraque fue " + (estado ? "aprobada" : "rechazada") + " correctamente");
    }
}
