package com.example.buques.service.Empresa;

import com.example.buques.docs.Empresa.Empresa;
import com.example.buques.dto.request.EmpresaRequest;
import com.example.buques.dto.response.EmpresaResponse;
import com.example.buques.repository.EmpresaRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Data
@Service
public class EmpresaService {

    @Autowired
    private final EmpresaRepository empresaRepository;

    public EmpresaResponse createEmpresa(EmpresaRequest request) {
        // Verificar si el NIT ya existe
        boolean exists = empresaRepository.findAll()
                .stream()
                .anyMatch(emp -> emp.getNit().equals(request.nit()));
        if (exists) {
            throw new IllegalArgumentException("Ya existe una empresa con el NIT: " + request.nit());
        }

        // Parsear cantidad_buques y manejar error
        int cantidadBuques;
        try {
            cantidadBuques = Integer.parseInt(request.cantidad_buques());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La cantidad de buques debe ser un número válido");
        }

        // Construir la empresa y guardar
        Empresa empresa = Empresa.builder()
                .nit(request.nit())
                .nombre(request.nombre())
                .pais(request.pais())
                .ciudad(request.ciudad())
                .direccion(request.direccion())
                .telefono(request.telefono())
                .correo(request.correo())
                .cantidad_buques(cantidadBuques)
                .build();
        empresaRepository.save(empresa);
        return toResponse(empresa);
    }

    public List<EmpresaResponse> getAllEmpresas() {
        return empresaRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public EmpresaResponse getEmpresaById(String id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
        return toResponse(empresa);
    }

    public EmpresaResponse updateEmpresa(String id, EmpresaRequest request) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        // Verificar si el NIT ya existe
        boolean exists = empresaRepository.findAll()
                .stream()
                .anyMatch(emp -> emp.getNit().equals(request.nit()));
        if (exists) {
            throw new IllegalArgumentException("Ya existe una empresa con el NIT: " + request.nit());
        }

        empresa.setNit(request.nit());
        empresa.setNombre(request.nombre());
        empresa.setPais(request.pais());
        empresa.setCiudad(request.ciudad());
        empresa.setDireccion(request.direccion());
        empresa.setTelefono(request.telefono());
        empresa.setCorreo(request.correo());
        empresa.setCantidad_buques(Integer.parseInt(request.cantidad_buques()));
        empresaRepository.save(empresa);
        return toResponse(empresa);
    }

    public void deleteEmpresa(String id) {
        empresaRepository.deleteById(id);
    }

    private EmpresaResponse toResponse(Empresa empresa) {
        return new EmpresaResponse(
                empresa.getNit(),
                empresa.getNombre(),
                empresa.getPais(),
                empresa.getCiudad(),
                empresa.getDireccion(),
                empresa.getTelefono(),
                empresa.getCorreo(),
                empresa.getCantidad_buques()
        );
    }

}