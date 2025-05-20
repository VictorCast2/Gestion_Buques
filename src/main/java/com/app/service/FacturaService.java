package com.app.service;

import com.app.collections.Atraque.Atraque;
import com.app.collections.Atraque.Enum.EResultado;
import com.app.collections.Factura.Enum.*;
import com.app.collections.Factura.Factura;
import com.app.collections.Factura.pojo.Proceso;
import com.app.collections.Usuario.Enum.ERol;
import com.app.collections.Usuario.Usuario;
import com.app.collections.Usuario.pojo.Empresa;
import com.app.dto.request.*;
import com.app.dto.response.AuthResponse;
import com.app.repository.*;
import com.app.utils.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class FacturaService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private FacturaRespository facturaRepository;

    @Autowired
    private AtraqueRepository atraqueRepository;

    /**
     * Método para obtener las facturas de un usuario
     * @param agenteNaviero parámetro pra obtener las facturas ligadas al usuario
     * @return una lista de facturas ligadas al usuario
     */
    public List<Factura> getFacturasByUsuario(Usuario agenteNaviero) {
        return facturaRepository.findByAgenteNaviero(agenteNaviero);
    }

    /**
     * Método para listar los procesos con estado 'PENDIENTE'
     * @return una lista de procesos con el estado 'PENDIENTE'
     */
    public List<ProcesoDto> getProcesosPendientes() {
        List<Factura> facturas = facturaRepository.findByProcesosEstadoProceso(EEstadoProceso.PENDIENTE);

        List<ProcesoDto> procesosPendientes = new ArrayList<>();

        for (Factura factura : facturas) {
            for (Proceso proceso : factura.getProcesos()) {
                if (proceso.getEstadoProceso() == EEstadoProceso.PENDIENTE) {
                    String agenteNaviero = factura.getAgenteNaviero()
                            .stream()
                            .findFirst()
                            .map(usuario -> usuario.getNombres() + " " + usuario.getApellidos())
                            .orElse("Desconocido");

                    String facturaId = factura.getId();
                    Atraque atraque = factura.getAtraque();
                    String matricula = (atraque != null && atraque.getBuque() != null) ? atraque.getBuque().getMatricula() : "N/A";
                    String nombreBuque = (atraque != null && atraque.getBuque() != null) ? atraque.getBuque().getNombre() : "N/A";

                    procesosPendientes.add(new ProcesoDto(
                            facturaId,
                            agenteNaviero,
                            matricula,
                            proceso.getTipoOperacion().getDescripcion(),
                            proceso.getTipoCarga().getDescripcion(),
                            proceso.getTipoProducto(),
                            proceso.getCantidad(),
                            proceso.getDescripcion(),
                            nombreBuque
                    ));
                }
            }
        }
        return procesosPendientes;
    }

    /**
     * Método para validar que el usuario tenga una solicitud de atraque aprobada
     * @param agenteNaviero parámetro para extraer la solicitud a verificar
     * @return true si el estado es aprobado o false si el estado es PENDIENTE o RECHAZADO
     */
    public boolean validarSolicitudAtraque(Usuario agenteNaviero) {
        List<Atraque> atraques = atraqueRepository.findByAgenteNaviero(agenteNaviero);
        return atraques.stream()
                .anyMatch(s -> s.getEstadoSolicitud() == EResultado.APROBADO);
    }

    /**
     * Método para registrar un Proceso
     * @param facturaProcesoRequest parámetro con los datos necesarios para solicitar un proceso
     * @param userDetails parámetro para obtener al usuario de la sesión
     * @return un objeto de tipo AuthResponse con un mensaje de satisfacción si el proceso se completo exitosamente
     */
    public AuthResponse registrarProceso(@Valid FacturaProcesoRequest facturaProcesoRequest, CustomUserDetails userDetails) {

        Usuario agenteNaviero = usuarioRepository.findByCorreo(userDetails.getCorreo())
                .orElseThrow(() -> new UsernameNotFoundException("usuario no encontrado en el proceso " + userDetails.getCorreo()));

        if (agenteNaviero.getRol() != ERol.AGENTE_NAVIERO) {
            throw new AccessDeniedException("No tienes permisos para crear un proceso");
        }

        Atraque ultimoAtraque = atraqueRepository.findTopByAgenteNavieroAndEstadoSolicitudOrderByFechaAprobacionDesc(agenteNaviero, EResultado.APROBADO)
                .orElseThrow(() -> new RuntimeException("No se encontró ningún atraque aprobado para este usuario"));

        Empresa empresa = agenteNaviero.getEmpresa();
        if (empresa == null) {
            throw new IllegalStateException("El usuario no está asociado a ninguna empresa.");
        }

        Set<Usuario> usuariosEmpresa = usuarioRepository.findAllByEmpresa_Nit(empresa.getNit());
        if (usuariosEmpresa.isEmpty()) {
            throw new IllegalStateException("No hay usuarios registrados en la empresa con NIT: " + empresa.getNit());
        }

        List<Proceso> procesos = facturaProcesoRequest.procesoRequests().stream()
                .map(this::crearProcesos) // este método se llama mas abajo
                .toList();

        float iva = procesos.stream().mapToInt(Proceso::getSubtotal).sum() * 0.19f;

        double total = procesos.stream().mapToInt(Proceso::getSubtotal).sum() + iva;

        Factura factura = Factura.builder()
                .fechaEmision(LocalDateTime.now())
                .fechaVencimiento(LocalDateTime.now().plusDays(90))
                .estado(EEstadoFactura.PENDIENTE)
                .procesos(procesos)
                .agenteNaviero(usuariosEmpresa)
                .atraque(ultimoAtraque)
                .total(total)
                .build();

        facturaRepository.save(factura);

        agenteNaviero.getFacturas().add(factura);
        usuarioRepository.save(agenteNaviero);

        return new AuthResponse("Servicio registrado exitosamente");
    }

    /**
     * Método para crear el objeto Proceso
     * @param procesoRequest parámetro con los datos necesarios para crear el proceso
     * @return un proceso creado, usado para crear la factura en el método registrarProceso
     */
    public Proceso crearProcesos(@Valid ProcesoRequest procesoRequest) {
        int precioUnitario = 0; // por ahora ese será el valor por defecto

        if (procesoRequest.tipoCarga().equals(ECarga.RO.toString())) {
            precioUnitario = 10000;
        } else if (procesoRequest.tipoCarga().equals(ECarga.CG.toString())) {
            precioUnitario = 20000;
        } else if (procesoRequest.tipoCarga().equals(ECarga.CN.toString())) {
            precioUnitario = 30000;
        } else if (procesoRequest.tipoCarga().equals(ECarga.GL.toString())) {
            precioUnitario = 40000;
        } else if (procesoRequest.tipoCarga().equals(ECarga.GS.toString())) {
            precioUnitario = 50000;
        } else if (procesoRequest.tipoCarga().equals(ECarga.LQ.toString())) {
            precioUnitario = 60000;
        } else {
           throw new IllegalStateException("Error: El tipo de proceso no existe");
        }

        int subtotal = procesoRequest.cantidad() * precioUnitario;

        return Proceso.builder()
                .tipoOperacion(EOperacion.valueOf(procesoRequest.tipoOperacion()))
                .tipoCarga(ECarga.valueOf(procesoRequest.tipoCarga()))
                .tipoProducto(procesoRequest.tipoProducto())
                .cantidad(procesoRequest.cantidad())
                .descripcion(procesoRequest.descripcion())
                .precioUnitario(precioUnitario)
                .subtotal(subtotal)
                .estadoProceso(EEstadoProceso.PENDIENTE)
                .build();
    }

    /**
     * Método para actualizar un proceso
     * @param facturaProcesoRequest parámetro con los datos necesarios para actualizar el proceso
     * @param id del proceso a actualizar
     * @return un objeto de tipo AuthResponse con un mensaje de satisfacción
     */
    public AuthResponse updateProceso(@Valid FacturaProcesoRequest facturaProcesoRequest, String id) {

        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("La factura con id: '" + id + "' no existe"));

        if (factura.getEstado() != EEstadoFactura.PENDIENTE) {
            throw new IllegalStateException("Error: no se puede editar una factura con estado diferente a 'PENDIENTE'");
        }

        factura.getProcesos().clear();

        List<Proceso> nuevosProcesos = facturaProcesoRequest.procesoRequests().stream()
                .map(this::crearProcesos).toList();

        float iva = nuevosProcesos.stream().mapToInt(Proceso::getSubtotal).sum() * 0.19f;

        double total = nuevosProcesos.stream().mapToInt(Proceso::getSubtotal).sum() + iva;

        factura.setProcesos(nuevosProcesos);
        factura.setTotal(total);

        facturaRepository.save(factura);

        return new AuthResponse("el proceso se ha actualizado exitosamente");
    }

    /**
     * Método para eliminar un proceso (factura) por su id
     * @param id parámetro para obtener la factura a eliminar
     * @return un objeto de tipo AuthResponse con un mensaje de satisfacción o un error dependiendo del estado del proceso
     */
    public AuthResponse deleteFactura(String id) {

        Factura factura = facturaRepository.findById(id)
                        .orElseThrow(() -> new NoSuchElementException("Factura no encontrada"));

        Proceso proceso = factura.getProcesos().getFirst();

        if (proceso.getEstadoProceso() == EEstadoProceso.PENDIENTE) {
            facturaRepository.deleteById(id);
            return new AuthResponse("El proceso ha sido eliminado exitosamente");
        }

        return new AuthResponse("Error: No se puede eliminar un proceso que este en un estado diferente a 'PENDIENTE'");
    }

    /**
     * Método para validar los Procesos
     * @param userDetails parámetro para extraer al usuario de la sesión
     * @param facturaId parámetro con la lista de procesos a validar
     * @param descripcionServicio parámetro con un mensaje seteado por el usuario en sesión
     * @param estado true si fue aprobada o false si fue rechazada
     * @return un objeto de tipo AuthResponse con un mensaje de satisfacción si la solicitud fue aprobada o
     * un mensaje de insatisfacción en caso contrario
     */
    public AuthResponse validarProceso(CustomUserDetails userDetails, String facturaId, String descripcionServicio, boolean estado) {

        Usuario admin = usuarioRepository.findByCorreo(userDetails.getCorreo())
                .orElseThrow(() -> new UsernameNotFoundException("Error: el usuario no ha sido encontrado en validar proceso"));

        Factura factura = facturaRepository.findById(facturaId)
                .orElseThrow(() -> new NoSuchElementException("El proceso no existe"));

        List<Proceso> procesos = factura.getProcesos();

        if (procesos.isEmpty()) {
            throw new NoSuchElementException("No existen procesos para validar");
        }

        for (Proceso proceso : procesos) {
            if (proceso.getEstadoProceso() == EEstadoProceso.PENDIENTE) {
                if (estado) {
                    factura.setDescripcionServicio(descripcionServicio);
                    proceso.setEstadoProceso(EEstadoProceso.APROBADO);
                } else {
                    factura.setDescripcionServicio("Servicio rechazado tras revisión. Para más información, comuníquese con el administrador.");
                    proceso.setEstadoProceso(EEstadoProceso.RECHAZADO);
                }
            }
        }

        factura.setAdmin(admin);
        facturaRepository.save(factura);
        return new AuthResponse("El procesos fueron " + (estado ? "aprobados" : "rechazados") + " correctamente");
    }
}
