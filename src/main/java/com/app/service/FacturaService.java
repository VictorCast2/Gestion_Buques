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
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Service
public class FacturaService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private FacturaRespository facturaRepository;

    @Autowired
    private AtraqueRepository atraqueRepository;

    /**
     * Método para obtener los procesos de un agentenaviero
     * @param agenteNaviero parámetro pra obtener los procesos ligados a este usuario
     * @return una lista de facturas con los procesos ligados al usuario
     */
    public List<Proceso> getProcesoByUsuario(Usuario agenteNaviero) {
        List<Factura> facturas = facturaRepository.findByAgenteNaviero(agenteNaviero);
        return facturas.stream()
                .flatMap(f -> f.getProcesos().stream())
                .collect(Collectors.toList());
    }

    /**
     * Método para validar que el usuario tenga una solicitud de atraque aprovada
     * @param agenteNaviero parámetro para extraer la solicitud a verificar
     * @return true si el estado es aprobado o false si el estado es PENDIENTE o RECHAZADO
     */
    public boolean validarSolicitudAtraque(Usuario agenteNaviero) {
        List<Atraque> atraques = atraqueRepository.findByAgenteNaviero(agenteNaviero);
        return atraques.stream()
                .anyMatch(s -> s.getEstadoSolicitud() == EResultado.APROBADO);
    }

    public AuthResponse registrarProceso(@Valid FacturaProcesoRequest facturaProcesoRequest, CustomUserDetails userDetails) {

        Usuario agenteNaviero = usuarioRepository.findByCorreo(userDetails.getCorreo())
                .orElseThrow(() -> new UsernameNotFoundException("usuario no encontrado en el proceso " + userDetails.getCorreo()));

        if (agenteNaviero.getRol() != ERol.AGENTE_NAVIERO) {
            throw new AccessDeniedException("No tienes permisos para crear un proceso");
        }

        Empresa empresa = agenteNaviero.getEmpresa();
        if (empresa == null) {
            throw new IllegalStateException("El usuario no está asociado a ninguna empresa.");
        }

        Set<Usuario> usuariosEmpresa = usuarioRepository.findAllByEmpresa_Nit(empresa.getNit());
        if (usuariosEmpresa.isEmpty()) {
            throw new IllegalStateException("No hay usuarios registrados en la empresa con NIT: " + empresa.getNit());
        }

        List<Proceso> procesos = facturaProcesoRequest.procesoRequests().stream()
                .map(this::crearProcesos)
                .toList();

        int total = procesos.stream().mapToInt(Proceso::getSubtotal).sum();

        Factura factura = Factura.builder()
                .fechaEmision(LocalDateTime.now())
                .fechaVencimiento(LocalDateTime.now().plusDays(30))
                .estado(EEstadoFactura.PENDIENTE)
                .procesos(procesos)
                .agenteNaviero(usuariosEmpresa)
                .total(total)
                .build();

        facturaRepository.save(factura);

        agenteNaviero.getFacturas().add(factura);
        usuarioRepository.save(agenteNaviero);

        return new AuthResponse("Servicio registrado exitosamente");
    }

    /**
     * Método para crear el objeto Proceso
     * @param procesoRequest parametro con los datos necesarios para crear el proceso
     * @return un proceso creado, usado para crear la factura en el método registrarProceso
     */
    public Proceso crearProcesos(ProcesoRequest procesoRequest) {
        int precioUnitario = 1000; // por ahora ese será el valor por defecto
        int subtotal = procesoRequest.cantidad() * precioUnitario;

        return Proceso.builder()
                .tipoOperacion(EOperacion.valueOf(procesoRequest.tipoOperacion()))
                .tipoCarga(ECarga.valueOf(procesoRequest.tipoCarga()))
                .tipoProducto(procesoRequest.tipoProducto())
                .cantidad(procesoRequest.cantidad())
                .descripcion(procesoRequest.descripcion())
                .precioUnitario(precioUnitario)
                .subtotal(subtotal)
                .build();
    }

}
