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

    public EmpresaResponse createEmpresa(EmpresaRequest request, String jwt) {
        Empresa empresa = Empresa.builder()
                .nit(request.nit())
                .nombre(request.nombre())
                .pais(request.pais())
                .ciudad(request.ciudad())
                .direccion(request.direccion())
                .telefono(request.telefono())
                .cantidad_buques(Integer.parseInt(request.cantidad_buques()))
                .build();
        empresaRepository.save(empresa);
        return toResponse(empresa, jwt);
    }

    public List<EmpresaResponse> getAllEmpresas() {
        return empresaRepository.findAll().stream()
                .map(empresa -> toResponse(empresa, null))
                .toList();
    }

    public EmpresaResponse getEmpresaById(String id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
        return toResponse(empresa, null);
    }

    public EmpresaResponse updateEmpresa(String id, EmpresaRequest request, String jwt) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
        empresa.setNit(request.nit());
        empresa.setNombre(request.nombre());
        empresa.setPais(request.pais());
        empresa.setCiudad(request.ciudad());
        empresa.setDireccion(request.direccion());
        empresa.setTelefono(request.telefono());
        empresa.setCantidad_buques(Integer.parseInt(request.cantidad_buques()));
        empresaRepository.save(empresa);
        return toResponse(empresa, jwt);
    }

    public void deleteEmpresa(String id) {
        empresaRepository.deleteById(id);
    }

    private EmpresaResponse toResponse(Empresa empresa, String jwt) {
        return new EmpresaResponse(
                empresa.getNit(),
                empresa.getNombre(),
                empresa.getPais(),
                empresa.getCiudad(),
                empresa.getDireccion(),
                empresa.getTelefono(),
                empresa.getCorreo(),
                jwt,
                empresa.getCantidad_buques()
        );
    }

}